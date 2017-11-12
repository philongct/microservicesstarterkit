package l.nguyen.security.support.jwt;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static l.nguyen.security.support.jwt.JwtTokenUtil.JWT_COOKIE_NAME;

public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private JwtTokenUtil tokenUtil;

    public JwtAuthenticationSuccessHandler(JwtTokenUtil tokenUtil) {
        this.tokenUtil = tokenUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest servletReq, HttpServletResponse servletRes, Authentication authentication) throws IOException, ServletException {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String jwtToken = tokenUtil.generateToken(user);
        Cookie cookie = new Cookie(JWT_COOKIE_NAME, jwtToken);
        cookie.setDomain(getDomain(servletReq.getRequestURL().toString()));
        // Add jwt token to cookie for browser to save it automatically
        // Web App then take value from cookie to assign to header for every request
        // This is to avoid CSRF
        servletRes.addCookie(cookie);
        servletRes.addHeader(JwtTokenUtil.HEADER_STRING, JwtTokenUtil.TOKEN_PREFIX + " " + jwtToken);
    }

    private String getDomain(String url) throws ServletException {
        try {
            return new URI(url).getHost();
        } catch (URISyntaxException e) {
            throw new ServletException("Invalid URL");
        }
    }
}
