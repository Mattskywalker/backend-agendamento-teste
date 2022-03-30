package com.comeia.backend.data;


import org.springframework.security.core.userdetails.User;

public class UserDTO {

    private User userDetails;
    private String token;
    private com.comeia.backend.model.User userData;

    public UserDTO(User userDetails, String token, com.comeia.backend.model.User user) {
        userDetails.eraseCredentials();
        this.userDetails = userDetails;
        user.setAgendamentoList(null);
        user.setSenha(null);
        this.userData = user;
        this.token = token;
    }

    public User getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(User userDetails) {
        this.userDetails = userDetails;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public com.comeia.backend.model.User getUserData() {
        return userData;
    }

    public void setUserData(com.comeia.backend.model.User userData) {
        this.userData = userData;
    }
}
