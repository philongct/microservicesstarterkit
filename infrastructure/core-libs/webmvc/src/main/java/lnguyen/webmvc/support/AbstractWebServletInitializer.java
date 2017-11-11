package lnguyen.webmvc.support;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Servlet container (>=3.0) call to this class automatically (with the support from Spring) if
 * implementation found in classpath
 */
public abstract class AbstractWebServletInitializer extends SpringBootServletInitializer {

    public abstract Class<?>[] getConfigClasses();

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder appBuilder) {
        return appBuilder.sources(getConfigClasses());
    }
}
