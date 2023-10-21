package com.ervinaldo.springboot.backend.apirest.springsecurity;
public class TokenInfo {
    private String jwtToken;

    public TokenInfo(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
