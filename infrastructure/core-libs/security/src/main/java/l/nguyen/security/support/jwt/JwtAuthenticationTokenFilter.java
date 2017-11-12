package l.nguyen.security.support.jwt;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    private JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationTokenFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // Web app must assign token to header to avoid CSRF
        final String requestHeader = request.getHeader(JwtTokenUtil.HEADER_STRING);

        SecurityContextHolder.getContext().setAuthentication(null);

        JwtCredentials jwtCredentials = null;
        if (requestHeader != null && requestHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            String authToken = requestHeader.replaceFirst(JwtTokenUtil.TOKEN_PREFIX, "");
            try {
                jwtCredentials = jwtTokenUtil.validateToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("Error occured during getting username from token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
            }
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }

        // TODO: validate jwt credentials with persisted data and cache it
        if (jwtCredentials != null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtCredentials.getPrincipal(), null, jwtCredentials.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}