package com.example.servidorspring.data;

import com.example.servidorspring.data.models.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ShopEntityRepository extends JpaRepository<ShopEntity, Integer> {


    @Override
    @Transactional
    @Modifying
    void deleteById(Integer integer);
}
