package com.demo.repository;

import com.demo.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    @Query("SELECT g FROM Game g WHERE LOWER(REPLACE(g.name, ' ', '')) = LOWER(REPLACE(:name, ' ', ''))")
    Game findByNameNormalized(@Param("name") String name);
}
