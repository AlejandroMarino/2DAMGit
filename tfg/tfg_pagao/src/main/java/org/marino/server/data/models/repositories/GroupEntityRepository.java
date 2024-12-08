package org.marino.server.data.models.repositories;

import org.marino.server.data.models.entities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupEntityRepository extends JpaRepository<GroupEntity, Integer> {

    @Query("SELECT g FROM GroupEntity g JOIN MemberEntity m ON g.id = m.group.id WHERE m.user.id = :userId")
    List<GroupEntity> getAllOfUser(int userId);
}
