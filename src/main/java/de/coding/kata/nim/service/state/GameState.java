package de.coding.kata.nim.service.state;

import de.coding.kata.nim.entity.Game;
import de.coding.kata.nim.entity.Player;

public interface GameState {

    void takeMatches(final Game context, final Player player, final int number);

    boolean isMyTurn(final Game context, final Player player);

    boolean isRunning(final Game context);

}
