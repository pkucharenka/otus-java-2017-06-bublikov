package ru.otus.bvd.servlet;

import org.springframework.context.ApplicationContext;
import ru.otus.bvd.base.DBService;
import ru.otus.bvd.cache.CacheEngine;
import ru.otus.bvd.cache.CacheEngineAdmin;
import ru.otus.bvd.example.DBActivity;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by tully.
 */
public class AdminServlet extends HttpServlet {

    private static final String DEFAULT_USER_NAME = "UNKNOWN";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final String REFRESH_VARIABLE_NAME = "refreshPeriod";
    private static final int PERIOD_MS = 1000;


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = createCacheVariablesMap();
        //let's get login from session
        String login = (String) request.getSession().getAttribute(LoginServlet.LOGIN_PARAMETER_NAME);
        pageVariables.put("login", login != null ? login : DEFAULT_USER_NAME);
        pageVariables.put(REFRESH_VARIABLE_NAME, String.valueOf(PERIOD_MS));
        
        if (!pageVariables.get("login").equals("admin")) {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);      
            return;
        }
        
        response.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private CacheEngineAdmin cacheEngineAdmin;
    private DBService dbService;
    private Map<String, Object> createCacheVariablesMap() {
        Map<String, Object> cacheVariables = new HashMap<>();

        cacheVariables.put("maxElements", cacheEngineAdmin.maxElements());
        cacheVariables.put("idleTime", cacheEngineAdmin.idleTimeMs());
        cacheVariables.put("isEternal", cacheEngineAdmin.isEternal() ? "Yes" : "No");
        cacheVariables.put("lifeTime", cacheEngineAdmin.lifeTimeMs());
        
        CacheEngine cacheEngine = (CacheEngine) cacheEngineAdmin;
        cacheVariables.put("hitCount", cacheEngine.getHitCount());
        cacheVariables.put("missCount", cacheEngine.getMissCount());
        cacheVariables.put("size", cacheEngine.getSize());
                
        return cacheVariables;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext springContext = (ApplicationContext) config.getServletContext().getAttribute("springContext");
        cacheEngineAdmin = (CacheEngineAdmin) springContext.getBean("cacheEngine");
        dbService = (DBService) springContext.getBean("dbService");

        Thread dbActivity = new Thread( new DBActivity(dbService));
        dbActivity.start();

    }

}
