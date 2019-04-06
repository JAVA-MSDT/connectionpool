package com.javamsdt.connectionpool.entity;

import java.util.Objects;

/**
 * @Author Ahmed Samy (serenitydiver@hotmail.com)
 */
public class User {

    private Integer id;
    private String name;
    private String sureName;
    private String email;
    private String login;
    private String password;
    private Role role;

    public User() {
    }

    public User(Integer id, String name, String sureName, String email, String login, String password, Role role) {
        this.id = id;
        this.name = name;
        this.sureName = sureName;
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSureName() {
        return sureName;
    }

    public void setSureName(String sureName) {
        this.sureName = sureName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(sureName, user.sureName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name != null) ? name.hashCode() : 0);
        result = prime * result + ((sureName != null) ? sureName.hashCode() : 0);
        result = prime * result + ((email != null) ? email.hashCode() : 0);
        result = prime * result + ((login != null) ? login.hashCode() : 0);
        result = prime * result + ((password != null) ? password.hashCode() : 0);
        result = prime * result + ((role != null) ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sureName='" + sureName + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
