package org.marino.server.spring.controller;

import jakarta.mail.MessagingException;
import lombok.extern.log4j.Log4j2;
import org.marino.server.data.models.User;
import org.marino.server.domain.exceptions.BadRequestException;
import org.marino.server.domain.services.ServicesEmail;
import org.marino.server.spring.security.ServicesJwt;
import org.marino.server.domain.services.ServicesUser;
import org.marino.server.domain.services.Utils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/login")
public class RestLoginController {

    private final ServicesUser sU;
    private final ServicesJwt sJ;
    private final ServicesEmail sE;
    private final Utils utils;


    public RestLoginController(ServicesUser sU, ServicesJwt sJ, ServicesEmail sE, Utils utils) {
        this.sU = sU;
        this.sJ = sJ;
        this.sE = sE;
        this.utils = utils;
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestParam String email, @RequestParam String password) {
        User user = new User(email, password);
        String bytes = utils.randomBytes();
        user.setVerificationCode(bytes);
        sU.register(user);
        try {
            sE.sendActivationEmail(user.getEmail(), bytes);
        } catch (Exception e) {
            throw new RuntimeException("Error while sending verification email");
        }
    }

    @PostMapping
    public ResponseEntity<User> login(@RequestParam String email, @RequestParam String password) {
        if (email == null || password == null) {
            throw new BadRequestException("Invalid username or password");
        } else {
            User user = sU.login(new User(email, password));
            String token = sJ.generateToken(user.getEmail());
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(user);
        }
    }


    @GetMapping("/activation")
    @ResponseStatus(HttpStatus.OK)
    public void validate(@RequestParam String code) {
        sU.verify(code);
    }
}
