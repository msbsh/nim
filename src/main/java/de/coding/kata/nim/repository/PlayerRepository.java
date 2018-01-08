package de.coding.kata.nim.repository;

import de.coding.kata.nim.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, String> {
}
