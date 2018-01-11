package de.coding.kata.nim.service;

import de.coding.kata.nim.Application;
import de.coding.kata.nim.entity.Player;
import de.coding.kata.nim.repository.GameRepository;
import de.coding.kata.nim.repository.PlayerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class PlayerServiceTest {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    private Player player;

    @Before
    public void setup() {
        gameRepository.deleteAllInBatch();

        this.player = new Player("testPlayer");
    }

    @Test
    public void testCreatePlayer() {
        final String playerName = "myPlayer";

        assertThat(playerService.getOrCreatePlayerForName(playerName).isValid()).isTrue();
        assertThat(playerService.getOrCreatePlayerForName(playerName).getName()).isEqualTo(playerName);
    }

    @Test
    public void testGetPlayerForName() {
        playerRepository.save(player);

        assertThat(playerService.getOrCreatePlayerForName(player.getName()).isValid()).isTrue();
        assertThat(playerService.getOrCreatePlayerForName(player.getName())).isEqualTo(player);
    }

}
