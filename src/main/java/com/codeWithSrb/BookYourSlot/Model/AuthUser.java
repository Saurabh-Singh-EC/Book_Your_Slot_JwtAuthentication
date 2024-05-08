package com.codeWithSrb.BookYourSlot.Model;

import java.util.Objects;

public class AuthUser {

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthUser)) return false;
        AuthUser authUser = (AuthUser) o;
        return Objects.equals(userName, authUser.userName) && Objects.equals(password, authUser.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password);
    }
}
