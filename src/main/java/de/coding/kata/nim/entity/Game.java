package de.coding.kata.nim.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.coding.kata.nim.service.converter.GameStateConverter;
import de.coding.kata.nim.service.state.GameState;
import de.coding.kata.nim.service.state.StateRunning;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Entity
@ToString
public class Game {

    @JsonIgnore
    @Convert(converter = GameStateConverter.class)
    private GameState gameState;

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

    public Game() {
    }

    public Game(final Player player1, final Player player2) {
        this.gameId = UUID.randomUUID();
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.winner = null;
        this.remainingMatches = 13;

        this.gameState = new StateRunning();
    }

    @JsonIgnore
    public void takeMatches(Player player, int number) {
        gameState.takeMatches(this, player, number);
    }

    @JsonIgnore
    public boolean isMyTurn(Player player) {
        return gameState.isMyTurn(this, player);
    }

    @JsonIgnore
    public boolean isRunning() {
        return gameState.isRunning(this);
    }

    @JsonIgnore
    public void endGame(@NotNull final Player lastPlayer) {
        this.winner = player1.equals(lastPlayer) ? player2 : player1;
        this.currentPlayer = null;
    }

}
