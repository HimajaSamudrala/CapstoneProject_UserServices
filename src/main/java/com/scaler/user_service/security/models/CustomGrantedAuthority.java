package com.scaler.user_service.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.scaler.user_service.models.Role;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {

    //private Role role;

    private String authority;

    public CustomGrantedAuthority() {}


    public CustomGrantedAuthority(Role role)
    {
        //this.role = role;
        this.authority = role.getName();
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
