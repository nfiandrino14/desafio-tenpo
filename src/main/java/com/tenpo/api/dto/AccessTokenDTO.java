package com.tenpo.api.dto;

import java.io.Serializable;

public class AccessTokenDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    private String JWT;

    public AccessTokenDTO() {
    }

    public AccessTokenDTO(String JWT) {
        this.JWT = JWT;
    }

    public String getJWT() {
        return JWT;
    }

    public void setJWT(String JWT) {
        this.JWT = JWT;
    }

    @Override
    public String toString() {
        return "AccessTokenDTO{" + "JWT=" + JWT + '}';
    }

}
