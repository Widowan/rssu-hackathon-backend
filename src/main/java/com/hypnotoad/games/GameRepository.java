package com.hypnotoad.games;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    boolean existsById(int id);
    Game findByName(String name);
}
