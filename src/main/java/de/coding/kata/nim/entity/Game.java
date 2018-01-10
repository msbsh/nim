package de.coding.kata.nim.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Data
@Slf4j
@Entity
@ToString
@NoArgsConstructor
public class Game {

    public Game(final Player player1, final Player player2, final int initialMatchCount) {
        this.gameId = UUID.randomUUID();
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.remainingMatches = initialMatchCount;
        this.winner = null;
    }

    @Id
    private UUID gameId;

    @ManyToOne
    private Player player1;

    @ManyToOne
    private Player player2;

    @ManyToOne
    private Player currentPlayer;

    private int remainingMatches;

    @ManyToOne
    private Player winner;

    private boolean canSubtract(final int remainingMatches, final int matchesToTake) {
        return (remainingMatches - matchesToTake) >= 0;
    }

    private Player getOpponent(final Player player) {
        return player1.equals(player) ? player2 : player1;
    }

    private void endGame() {
        this.winner = getOpponent(currentPlayer);
        this.currentPlayer = null;
    }

    private void nextPlayer() {
        currentPlayer = getOpponent(currentPlayer);
    }

    public void takeMatches(final int numberOfMatches) {
        if(!canSubtract(remainingMatches, numberOfMatches)) {
            throw new IllegalArgumentException(
                    String.format("Cannot take %d of %d matches!", numberOfMatches, remainingMatches));
        }
        log.debug("{} took {} matches in game {}. {} remaining",
                currentPlayer, numberOfMatches, gameId, (remainingMatches - numberOfMatches));

        remainingMatches -= numberOfMatches;
        if (remainingMatches == 0) {
            endGame();
        } else {
            nextPlayer();
        }
    }

    public boolean isMyTurn(final Player player) {
        return currentPlayer != null && currentPlayer.equals(player);
    }

}
