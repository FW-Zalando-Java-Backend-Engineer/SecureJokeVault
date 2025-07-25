package org.example.secure_joke_vault.alias;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecurityUser extends User {
    public SecurityUser(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities) {
                     super(  username,
                             password,
                             authorities
                     );
    }

    public SecurityUser(
            String username,
            String password,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
                     super(  username,
                             password,
                             enabled,
                             accountNonExpired,
                             credentialsNonExpired,
                             accountNonLocked,
                             authorities
                     );
    }
}
