package de.coding.kata.nim.service;

import de.coding.kata.nim.Application;
import de.coding.kata.nim.entity.Game;
import de.coding.kata.nim.entity.Player;
import de.coding.kata.nim.exception.GameNotFoundException;
import de.coding.kata.nim.exception.InvalidMatchCountException;
import de.coding.kata.nim.repository.GameRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class GameServiceTest {

    private static final int INITIAL_MATCH_COUNT = 13;
    private static final int MIN_MATCHES = 1;
    private static final int MAX_MATCHES = 3;

    @Autowired
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    private Player player1, player2;
    private Game game;

    @Before
    public void setup() {
        this.player1 = new Player("player1");
        this.player2 = new Player("player2");

        this.game = gameRepository.save(new Game(player1, player2, MIN_MATCHES, MAX_MATCHES, INITIAL_MATCH_COUNT));
    }

    private void isValidGame(final Game game) {
        assertThat(game.getGameId()).isNotNull();
        assertThat(game.getGameId()).isNotBlank();
        assertThat(game.getPlayer1()).isNotNull();
        assertThat(game.getPlayer2()).isNotNull();
        assertThat(game.getRemainingMatches()).isGreaterThan(-1);
    }

    private void checkGame(final Game game, final Player currentPlayer, final int remainingMatches) {
        isValidGame(game);

        assertThat(game.getCurrentPlayer()).isEqualTo(currentPlayer);
        assertThat(game.getRemainingMatches()).isEqualTo(remainingMatches);
    }

    @Test
    public void testFindGameById() {
        assertThat(gameService.getGameById(game.getGameId())).isEqualTo(game);
    }

    @Test(expected = GameNotFoundException.class)
    public void testDoNotFindGameById() {
        gameService.getGameById("MY-GAME-ID");
    }

    @Test(expected = GameNotFoundException.class)
    public void testFindGameByNull() {
        gameService.getGameById(null);
    }

    @Test
    public void testStartGame() {
        isValidGame(gameService.startGame(player1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStartGameWithEmptyPlayer() {
        gameService.startGame(new Player());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStartGameWithNullPlayer() {
        gameService.startGame(null);
    }

    @Test
    public void testPlaySingleRound() {
        gameService.playRound(game, player1, 3);

        checkGame(game, player2, 10);
    }

    @Test
    public void testPlayFullMatch() {
        gameService.playRound(game, player1, 3);
        checkGame(game, player2, 10);

        gameService.playRound(game, player2, 3);
        checkGame(game, player1, 7);

        gameService.playRound(game, player1, 2);
        checkGame(game, player2, 5);

        gameService.playRound(game, player2, 3);
        checkGame(game, player1, 2);

        gameService.playRound(game, player1, 1);
        checkGame(game, player2, 1);

        gameService.playRound(game, player2, 1);
        checkGame(game, null, 0);
        assertThat(game.getWinner()).isEqualTo(player1);
    }

    @Test(expected = InvalidMatchCountException.class)
    public void testTakeTooMany() {
        gameService.playRound(game, player1, 3);
        checkGame(game, player2, 10);

        gameService.playRound(game, player2, 4);
        fail("Could take too many matches");
    }

    @Test(expected = InvalidMatchCountException.class)
    public void testTakeTooFew() {
        gameService.playRound(game, player1, 3);
        checkGame(game, player2, 10);

        gameService.playRound(game, player2, 0);
        fail("Could take too few matches");
    }
}
