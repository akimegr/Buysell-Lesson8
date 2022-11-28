package com.shop.engine.models;

import javax.security.auth.Subject;
import java.security.Principal;

public class PrincipalOpen implements Principal {

    private String name;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}
