package com.demo.repository;

import com.demo.entity.SellerGame;
import com.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerGameRepository extends JpaRepository<SellerGame, Integer> {
    List<SellerGame> findBySeller(User seller);
}
