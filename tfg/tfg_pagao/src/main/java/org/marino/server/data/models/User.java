package org.marino.server.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String email;
    private String password;
    private boolean verified;
    private String verificationCode;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
