package ru.otus.bvd.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.otus.bvd.example.MainExecutorDBServiceWithJetty;

public class ShutdownServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("login", "");

        response.setContentType("text/html;charset=utf-8");

        String errMsg = null;
        try {
            MainExecutorDBServiceWithJetty.dbService.shutdown();        
        } catch (Exception e) {
            errMsg = e.getMessage();
        }
        if (errMsg != null) {
            response.getWriter().println("Error:\n" + errMsg);                
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }    
        
        errMsg = null;
        try {
            MainExecutorDBServiceWithJetty.webServer.stop();
        } catch (Exception e) {
            errMsg = e.getMessage();
        }        
        if (errMsg != null) {
            response.getWriter().println("Error:\n" + errMsg);                
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else {
            response.getWriter().println("Server shutdown");
            response.setStatus( HttpServletResponse.SC_OK );
        }
    }

}
