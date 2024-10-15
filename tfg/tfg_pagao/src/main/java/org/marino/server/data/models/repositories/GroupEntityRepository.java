package org.marino.server.data.models.repositories;

import org.marino.server.data.models.entities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupEntityRepository extends JpaRepository<GroupEntity, Integer> {


}
