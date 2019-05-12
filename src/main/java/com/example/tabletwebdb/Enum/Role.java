package com.example.tabletwebdb.Enum;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority
{
    ADMIN,
    MANAGER,
    SUPER;

    @Override
    public String getAuthority() {
        return name();
    }
}
