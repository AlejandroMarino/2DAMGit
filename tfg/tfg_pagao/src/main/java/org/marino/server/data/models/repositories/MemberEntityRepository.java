package org.marino.server.data.models.repositories;

import org.marino.server.data.models.entities.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberEntityRepository extends JpaRepository<MemberEntity, Integer> {

    @Query("FROM MemberEntity m WHERE m.group.id = :groupId")
    List<MemberEntity> getAllOfGroup(int groupId);
}
