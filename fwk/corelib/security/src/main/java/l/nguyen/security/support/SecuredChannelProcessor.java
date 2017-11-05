package l.nguyen.security.support;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
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