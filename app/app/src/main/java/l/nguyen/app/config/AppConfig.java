package l.nguyen.app.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
    value = "l.nguyen.app",
    // batch is modular and should be configured separately
    excludeFilters = @Filter(type = FilterType.REGEX, pattern = ".+[Bb]atch.+")
)
public class AppConfig {
}
