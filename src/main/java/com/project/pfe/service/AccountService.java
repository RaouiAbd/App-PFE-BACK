package com.project.pfe.service;


import com.project.pfe.models.AppRole;
import com.project.pfe.models.AppUser;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public interface AccountService {
    public AppUser saveUser(AppUser user);

    public AppRole saveRole(AppRole role);

    public void addRoleToUser(String username, String roleName);

    public void removeRoleToUser(String username, String roleName);

    public AppUser findUserByUsername(String username);

    public void sendEmail(String email, String username, String password)
            throws MessagingException, IOException;

    public String randomString();
}
