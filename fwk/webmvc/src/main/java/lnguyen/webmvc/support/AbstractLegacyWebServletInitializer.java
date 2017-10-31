package lnguyen.webmvc.support;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * For legacy servlet container (<3.0), just extends this class and set <listener> in web.xml
 */
public abstract class AbstractLegacyWebServletInitializer implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(AbstractLegacyWebServletInitializer.class);

    public abstract AbstractWebServletInitializer getWebServletInitializer();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            getWebServletInitializer().onStartup(sce.getServletContext());
        } catch (ServletException e) {
            logger.error("Web Servlet initialization failed", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ((ConfigurableApplicationContext) WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext())).close();
    }
}
