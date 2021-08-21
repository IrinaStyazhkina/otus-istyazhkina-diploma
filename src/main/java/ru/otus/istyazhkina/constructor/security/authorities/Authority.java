package ru.otus.istyazhkina.constructor.security.authorities;

import org.springframework.security.core.GrantedAuthority;

public enum Authority implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_DEVELOPER;

    @Override
    public String getAuthority() {
        return name();
    }
}
