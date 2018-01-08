package de.coding.kata.nim.service;

import de.coding.kata.nim.entity.Player;
import de.coding.kata.nim.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    public PlayerService(@Autowired PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player getOrCreatePlayerForName(final String playerName) {
        return playerRepository.findById(playerName).orElseGet(() -> playerRepository.save(new Player(playerName)));
    }

}
