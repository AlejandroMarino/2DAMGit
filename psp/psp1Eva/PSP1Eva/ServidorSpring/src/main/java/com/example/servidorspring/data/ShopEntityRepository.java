package com.example.servidorspring.data;

import com.example.servidorspring.data.models.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopEntityRepository extends JpaRepository<ShopEntity, Integer> {


}
