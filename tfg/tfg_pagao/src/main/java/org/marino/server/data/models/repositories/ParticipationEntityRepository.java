package org.marino.server.data.models.repositories;

import org.marino.server.data.models.entities.ParticipationEntity;
import org.marino.server.data.models.entities.ParticipationEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationEntityRepository extends JpaRepository<ParticipationEntity, ParticipationEntityId> {

    @Query("FROM ParticipationEntity p WHERE p.receipt.id = :receiptId")
    List<ParticipationEntity> getAllOfReceipt(int receiptId);
}
