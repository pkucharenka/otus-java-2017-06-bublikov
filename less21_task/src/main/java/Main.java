import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

/**
 * Created by vadim on 11.06.17.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException, InstantiationException, IllegalAccessException {
        System.gc();

        long l = 0;
        TypeMeter tmLong = new TypeMeter(l);
        System.out.println("long: " + tmLong.getSize() );

        byte b = 0;
        TypeMeter tmByte = new TypeMeter(b);
        System.out.println("byte: " + tmByte.getSize() );

        boolean bool = true;
        TypeMeter tmBool = new TypeMeter(bool);
        System.out.println("boolean: " + tmBool.getSize() );

        char c = 'a';
        TypeMeter tmChar = new TypeMeter(c);
        System.out.println("char: " + tmChar.getSize() );

        short sh = 0;
        TypeMeter tmShort = new TypeMeter(sh);
        System.out.println("short: " + tmShort.getSize() );

        int i = 0;
        TypeMeter tmInt = new TypeMeter(i);
        System.out.println("int: " + tmInt.getSize() );

        float f = 0;
        TypeMeter tmFloat = new TypeMeter(f);
        System.out.println("float: " + tmFloat.getSize() );


        double d = 0;
        TypeMeter tmDouble = new TypeMeter(d);
        System.out.println("double: " + tmDouble.getSize() );

        String s1 = "11111111112222222222333333333344444444445555555555";
        TypeMeter tmString = new TypeMeter(s1);
        System.out.println("String : " + tmString.getSize() + " - " +s1 );

        TypeMeter tmObject = new TypeMeter( new Object());
        System.out.println("Object : " + tmObject.getSize());

        TypeMeter tmHttpServer = new TypeMeter(
                new HttpServer() {
                    @Override
                    public void bind(InetSocketAddress addr, int backlog) throws IOException {}
                    @Override
                    public void start() {}
                    @Override
                    public void setExecutor(Executor executor) {}
                    @Override
                    public Executor getExecutor() {return null;}
                    @Override
                    public void stop(int delay) {}
                    @Override
                    public HttpContext createContext(String path, HttpHandler handler) {return null;}
                    @Override
                    public HttpContext createContext(String path) {return null;}
                    @Override
                    public void removeContext(String path) throws IllegalArgumentException {}
                    @Override
                    public void removeContext(HttpContext context) {}
                    @Override
                    public InetSocketAddress getAddress() {return null;} }
        );
        System.out.println("HttpServer: " + tmHttpServer.getSize() );

    }


}
