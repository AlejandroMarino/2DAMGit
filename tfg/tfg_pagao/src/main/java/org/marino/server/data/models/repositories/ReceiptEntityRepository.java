package org.marino.server.data.models.repositories;

import org.marino.server.data.models.entities.ReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptEntityRepository extends JpaRepository<ReceiptEntity, Integer> {

    @Query("FROM ReceiptEntity r WHERE r.group.id = :groupId")
    List<ReceiptEntity> getAllOfGroup(int groupId);
}
