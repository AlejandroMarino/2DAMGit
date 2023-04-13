package com.example.servidorspring.data;

import com.example.servidorspring.data.models.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameEntityRepository extends JpaRepository<GameEntity,Integer> {

    @Query("FROM GameEntity g WHERE g.shop.id = :shopId")
    List<GameEntity> getAllOfShop(int shopId);
}
