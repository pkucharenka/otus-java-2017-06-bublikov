package ru.otus.bvd.ms.channel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import ru.otus.bvd.ms.app.Msg;
import ru.otus.bvd.ms.app.MsgClient;
import ru.otus.bvd.ms.core.Address;
import ru.otus.bvd.ms.core.MessageSystemContext;

/**
 * Created by tully.
 */
public class SocketMsgClient implements MsgClient {
    private static final Logger logger = Logger.getLogger(SocketMsgClient.class.getName());
    private static final int WORKERS_COUNT = 2;

    private final BlockingQueue<Msg> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Msg> input = new LinkedBlockingQueue<>();

    public final ExecutorService executor;
    private final Socket socket;
    private final List<Runnable> shutdownRegistrations;

    private Address address;   
    
    public SocketMsgClient(Socket socket, MessageSystemContext messageSystemContext) {
        this.socket = socket;
        this.shutdownRegistrations = new CopyOnWriteArrayList<>();
        this.executor = Executors.newFixedThreadPool(WORKERS_COUNT);
    }
    
    @Override
    public void send(Msg msg) {
        output.add(msg);
    }

    @Override
    public Msg pool() {
        return input.poll();
    }

    @Override
    public Msg take() throws InterruptedException {
        return input.take();
    }

    @Override
    public void close() {
        shutdownRegistrations.forEach(Runnable::run);
        shutdownRegistrations.clear();

        executor.shutdown();
    }

    public void init() {
        executor.execute(this::sendMessage);
        executor.execute(this::receiveMessage);
    }

    public void addShutdownRegistration(Runnable runnable) {
        this.shutdownRegistrations.add(runnable);
    }

    private void receiveMessage() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while (!Thread.currentThread().isInterrupted() && (inputLine = in.readLine()) != null) {
            	logger.config("Message received: " + inputLine);
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) { //empty line is the end of the message
                    String json = stringBuilder.toString();
                    Msg msg = getMsgFromJSON(json);
                    input.add(msg);
                    stringBuilder = new StringBuilder();
                }
            }
        } catch (IOException | ParseException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            close();
        }
        
    }

    private void sendMessage() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (!Thread.currentThread().isInterrupted() && socket.isConnected()) {
                Msg msg = output.poll(1, TimeUnit.SECONDS); //blocks
                if (msg == null)
                	continue;
                String json = new Gson().toJson(msg);
                out.println(json);
                out.println(); //end of message
            }
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private static Msg getMsgFromJSON(String json) throws ParseException, ClassNotFoundException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        String className = (String) jsonObject.get(Msg.CLASS_NAME_VARIABLE);
        Class<?> msgClass = Class.forName(className);
        return (Msg) new Gson().fromJson(json, msgClass);
    }

    @Override
    public void setAddress(Address address) {
    	this.address = address;
    }
    
	@Override
	public Address getAddress() {
		return address;
	}
}
