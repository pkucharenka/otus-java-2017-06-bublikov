package ru.otus.bvd.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by vadim on 16.09.17.
 */
public class ServletContextListenerApp implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext springContext = new ClassPathXmlApplicationContext("SpringBeans.xml");
        servletContextEvent.getServletContext().setAttribute("springContext", springContext);
   }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
