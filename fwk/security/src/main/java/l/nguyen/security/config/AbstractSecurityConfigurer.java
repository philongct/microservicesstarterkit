package l.nguyen.security.config;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

import l.nguyen.security.support.user.FakeUserDetailsService;
import l.nguyen.security.support.SecuredChannelProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ChannelSecurityConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAspectJAutoProxy
@PropertySource("classpath:fwk/security.properties")
public abstract class AbstractSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    protected Environment env;

    @Value("${ssl.enable:false}")
    private boolean sslEnabled;

    @Value("${ssl.patterns:}")
    private String[] sslPatterns;

    @Value("${resources.secured:}")
    private String securedResources;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        configureSsl(http);
        configureAuthEntry(http);
        configureAuthRequests(http);
    }

    protected void configureSsl(HttpSecurity http) throws Exception {
        if (sslEnabled) {
            ChannelSecurityConfigurer<?>.RequiresChannelUrl requiresChannelUrl;
            if (sslPatterns != null && sslPatterns.length > 0) {
                requiresChannelUrl = http.requiresChannel().antMatchers(sslPatterns);
            } else {
                requiresChannelUrl = http.requiresChannel().anyRequest();
            }

            requiresChannelUrl.requiresSecure().channelProcessors(Collections.singletonList(new SecuredChannelProcessor()));
        }
    }

    protected void configureAuthEntry(HttpSecurity http) throws Exception {
        http
            .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint());
    }

    protected void configureAuthRequests(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf().disable()
            .authorizeRequests()
                .antMatchers(securedResources)
                .fullyAuthenticated()
            .anyRequest()
                .permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    protected AuthenticationEntryPoint authEntryPoint() {
        // Simply reject unauthorized requests
        return (request, response, exception) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        return new FakeUserDetailsService();
    }
}