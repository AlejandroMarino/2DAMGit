package org.marino.server.domain.services;

import lombok.extern.log4j.Log4j2;
import org.marino.server.data.models.User;
import org.marino.server.data.models.entities.UserEntity;
import org.marino.server.data.models.mappers.UserMapper;
import org.marino.server.data.models.repositories.UserEntityRepository;
import org.marino.server.domain.exceptions.BadRequestException;
import org.marino.server.domain.exceptions.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class ServicesUser {

    private final UserEntityRepository userR;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public ServicesUser(UserEntityRepository userR, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userR = userR;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User get(int id) {
        if (id < 1) {
            return null;
        }
        return userR.findById(id).map(userMapper::toUser)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    public void register(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        try {
            userMapper.toUser(userR.save(userMapper.toUserEntity(user)));
        } catch (Exception e) {
            throw new BadRequestException("User already registered");
        }
    }

    @Transactional
    public void verify(String code) {
        int updatedRows = userR.verifyUser(code);
        if (updatedRows <= 0) {
            throw new NotFoundException("user not found or already verified");
        }
    }

    public User login(User u) {
        UserEntity userE = userR.findByEmail(u.getEmail().toLowerCase());
        if (userE != null) {
            if (!passwordEncoder.matches(u.getPassword(), userE.getPassword())) {
                throw new BadRequestException("Bad combination of email and password");
            } else {
                if (userE.isVerified()) {
                    return userMapper.toUser(userE);
                } else {
                    throw new BadRequestException("User is not verified, please verify it, by clicking in the button of the email received");
                }
            }
        } else {
            throw new BadRequestException("User not found");
        }
    }

    public Boolean isEmailValid(String email) {
        UserEntity user = userR.findByEmail(email.toLowerCase());
        return user != null && user.isVerified();
    }

}
