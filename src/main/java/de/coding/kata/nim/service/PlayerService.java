package de.coding.kata.nim.service;

import de.coding.kata.nim.entity.Player;
import de.coding.kata.nim.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class to manage nim players
 */
@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    public PlayerService(@Autowired PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Creates or retrieves a player by name.
     * @param playerName The unique player name
     * @return The player found or created for the provided name
     */
    public Player getOrCreatePlayerForName(final String playerName) {
        return playerRepository.findById(playerName).orElseGet(() -> playerRepository.save(new Player(playerName)));
    }

}
