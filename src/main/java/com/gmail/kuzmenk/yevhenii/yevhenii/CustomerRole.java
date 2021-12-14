package com.gmail.kuzmenk.yevhenii.yevhenii;

public enum CustomerRole {
    ADMIN, USER;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}


