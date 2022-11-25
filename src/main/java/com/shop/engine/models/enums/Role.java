package com.shop.engine.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN, ROLE_FRANCHISE, ROLE_ONLY_TEST;

    @Override
    public String getAuthority() {
        return name();
    }
}
