package de.coding.kata.nim.entity;

import de.coding.kata.nim.exception.InvalidMatchCountException;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameTest {

    private static final int MIN_MATCHES = 1;
    private static final int MAX_MATCHES = 13;
    private static final int INITIAL_MATCH_COUNT = 13;

    private Player player1, player2;
    private Game game;

    @Before
    public void setup() {
        this.player1 = new Player("player1");
        this.player2 = new Player("player2");

        this.game = new Game(player1, player2, MIN_MATCHES, MAX_MATCHES, INITIAL_MATCH_COUNT);
    }

    @Test
    public void testCanSubtract() {
        game.takeMatches(MAX_MATCHES);
    }

    @Test(expected = InvalidMatchCountException.class)
    public void testCannotSubtract() {
        game.takeMatches(MAX_MATCHES + 1);
    }

    @Test
    public void testGameEnded() {
        game.takeMatches(MAX_MATCHES);
        assertThat(game.getWinner()).isEqualTo(player2);
        assertThat(game.getCurrentPlayer()).isNull();
    }

    @Test
    public void testGameNotEnded() {
        game.takeMatches(MAX_MATCHES - 1);
        assertThat(game.getWinner()).isNull();
        assertThat(game.getCurrentPlayer()).isEqualTo(player2);
    }

    @Test
    public void testPlayer1Term() {
        assertThat(game.isMyTurn(player1)).isTrue();
        assertThat(game.isMyTurn(player2)).isFalse();
    }

    @Test
    public void testPlayer2Term() {
        game.takeMatches(3);
        assertThat(game.isMyTurn(player1)).isFalse();
        assertThat(game.isMyTurn(player2)).isTrue();
    }
}
