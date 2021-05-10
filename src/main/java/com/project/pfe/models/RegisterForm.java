package com.project.pfe.models;

import lombok.Data;


@Data
public class RegisterForm {
    private String username;
    private String email;
    private String password;
    private String function;
    private String mobile;
    private Boolean isAdmin;
    private Boolean isResp;
}

