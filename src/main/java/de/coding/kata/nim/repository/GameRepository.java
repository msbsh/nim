package de.coding.kata.nim.repository;

import de.coding.kata.nim.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, String> {
}
