package org.marino.server.data.models.mappers;

import org.marino.server.data.models.User;
import org.marino.server.data.models.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserEntity user) {
        return new User(user.getId(), user.getEmail(), user.getPassword(), user.isVerified(), user.getVerificationCode());
    }

    public UserEntity toUserEntity(User user) {
        return new UserEntity(user.getId(), user.getEmail(), user.getPassword(), user.isVerified(), user.getVerificationCode());
    }
}
