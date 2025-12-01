package com.ecpi.jwt.response;

import java.util.Set;

public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private boolean active;
    private Set<String> roles;

    public AuthResponse(String accessToken, String refreshToken, boolean active, Set<String> roles) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.active = active;
        this.roles = roles;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
