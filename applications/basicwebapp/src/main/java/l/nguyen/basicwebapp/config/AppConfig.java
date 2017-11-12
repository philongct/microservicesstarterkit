package l.nguyen.basicwebapp.config;

import l.nguyen.security.config.basicweb.BasicJwtWebSecurityConfigurer;
import lnguyen.webmvc.config.DefaultWebMvcConfigurer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(
    value = "l.nguyen.app",
    // batch is modular and should be configured separately
    excludeFilters = @Filter(type = FilterType.REGEX, pattern = ".+[Bb]atch.+")
)
@Import({BasicJwtWebSecurityConfigurer.class, DefaultWebMvcConfigurer.class})
public class AppConfig {
}
