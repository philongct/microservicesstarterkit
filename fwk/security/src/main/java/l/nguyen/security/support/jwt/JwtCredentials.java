package l.nguyen.security.support.jwt;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtCredentials {

    private String principal;

    private Collection<GrantedAuthority> authorities;

    public JwtCredentials(String principal, Collection<GrantedAuthority> authorities) {
        this.principal = principal;
        this.authorities = authorities;
    }

    public String getPrincipal() {
        return principal;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
