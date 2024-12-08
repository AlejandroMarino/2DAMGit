package org.marino.server.data.models.repositories;

import org.marino.server.data.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {


    @Modifying
    @Query("UPDATE UserEntity u SET u.verified = true WHERE u.verificationCode = :verificationCode")
    int verifyUser(String verificationCode);

    UserEntity findByEmail(String email);
}
