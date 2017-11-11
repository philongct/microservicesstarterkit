package l.nguyen.security.support.user;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class FakeUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.startsWith("user")) {
            return new FakeUser(username);
        }
        throw new UsernameNotFoundException("user " + username + " not found");
    }

    private class FakeUser implements UserDetails {

        private String username;

        private Collection<GrantedAuthority> authorities = new ArrayList<>();

        public FakeUser(String username) {
            this.username = username;
            String[] parts = username.split("_");
            for (String part : parts) {
                authorities.add(new SimpleGrantedAuthority(part));
            }
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return username;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
