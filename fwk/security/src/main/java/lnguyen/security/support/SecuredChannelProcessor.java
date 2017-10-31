package lnguyen.security.support;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ChannelSecurityConfigurer;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.channel.SecureChannelProcessor;

public class SecuredChannelProcessor extends SecureChannelProcessor {
    @Override
    public void decide(FilterInvocation filterInvocation, Collection<ConfigAttribute> configAttributes) throws IOException, ServletException {
        for (ConfigAttribute configAttr : configAttributes) {
            if (supports(configAttr) && shouldRedirect(filterInvocation.getHttpRequest())) {
                getEntryPoint().commence(filterInvocation.getRequest(), filterInvocation.getResponse());
            }
        }
    }

    protected boolean shouldRedirect(HttpServletRequest request) {
        return !(request.isSecure() || "https".equals(request.getHeader("x-forwarded-proto")));
    }
}