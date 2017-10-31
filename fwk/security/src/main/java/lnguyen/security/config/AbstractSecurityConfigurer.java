package lnguyen.security.config;

import java.util.Collections;

import lnguyen.security.support.SecuredChannelProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ChannelSecurityConfigurer;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
@EnableAspectJAutoProxy
public abstract class AbstractSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    protected Environment env;

    @Value("${ssl.enable:true}")
    private boolean sslEnabled;

    @Value("${ssl.patterns:}")
    private String[] sslPatterns;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        configureSsl(http);
        configureAuthEntry(http);
        configureAuthRequests(http);
    }

    protected void configureSsl(HttpSecurity http) throws Exception {
        if (sslEnabled) {
            ChannelSecurityConfigurer.RequiresChannelUrl requiresChannelUrl;
            if (sslPatterns != null && sslPatterns.length > 0) {
                requiresChannelUrl = http.requiresChannel().antMatchers(sslPatterns);
            } else {
                requiresChannelUrl = http.requiresChannel().anyRequest();
            }

            requiresChannelUrl.requiresSecure().channelProcessors(Collections.singletonList(new SecuredChannelProcessor()));
        }
    }

    protected void configureAuthEntry(HttpSecurity http) {
        http
            .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint())
        .and()
            .addFilterBefore(new )
        return new BasicAuthenticationEntryPoint();
    }

    protected Authentication
}