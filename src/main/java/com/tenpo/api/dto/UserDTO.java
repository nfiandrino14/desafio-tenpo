package com.tenpo.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

    @NotNull(message = "username may not be null")
    @NotEmpty(message = "username may not be empty")
    @Size(min = 4, message = "invalid username size")
    private String username;

    @NotNull(message = "password may not be null")
    @NotEmpty(message = "password may not be empty")
    @Size(min = 4, message = "invalid paswword size")
    private String password;

    @Email(message = "Invalid e-mail")
    @NotNull(message = "Email may not be null")
    @NotEmpty(message = "Email may not be empty")
    private String mail;

    public UserDTO() {
    }

    public UserDTO(String username, String password, String mail) {
        this.username = username;
        this.password = password;
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "username=" + username + ", password=" + password + ", mail=" + mail + '}';
    }

}
