package org.marino.server.data.models.repositories;

import org.marino.server.data.models.entities.MemberEntity;
import org.marino.server.data.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberEntityRepository extends JpaRepository<MemberEntity, Integer> {

    @Query("FROM MemberEntity m WHERE m.group.id = :groupId")
    List<MemberEntity> getAllOfGroup(int groupId);

    @Query("SELECT COALESCE(SUM(p.pays), 0.0) - COALESCE(SUM(p.debts), 0.0) " +
            "FROM ParticipationEntity p " +
            "WHERE p.member.id = :memberId")
    double getBalanceOfMember(int memberId);

    @Query("SELECT COUNT(m) > 0 FROM MemberEntity m WHERE m.group.id = :groupId AND m.user.email = :userEmail")
    Boolean userAlreadyInGroup(String userEmail, int groupId);

    @Modifying
    @Query("UPDATE MemberEntity m SET m.user = :user WHERE m.id = :memberId")
    int setUser(int memberId, UserEntity user);
}
