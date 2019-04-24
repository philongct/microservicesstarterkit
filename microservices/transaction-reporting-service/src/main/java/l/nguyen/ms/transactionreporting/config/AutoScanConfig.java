package l.nguyen.ms.transactionreporting.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(value = "l.nguyen", excludeFilters = @Filter(type = FilterType.REGEX, pattern = ".*[Bb]atch.*"))
public class AutoScanConfig {
}
