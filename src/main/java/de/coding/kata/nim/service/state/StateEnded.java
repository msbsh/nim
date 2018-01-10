package de.coding.kata.nim.service.state;

import de.coding.kata.nim.entity.Game;
import de.coding.kata.nim.entity.Player;

public class StateEnded implements GameState {

    @Override
    public void takeMatches(final Game context, Player player, int number) {
        throw new IllegalStateException("Game has ended, thus you cannot take matches!");
    }

    @Override
    public boolean isMyTurn(final Game context, Player player) {
        return false;
    }

    @Override
    public boolean isRunning(final Game context) {
        return false;
    }

}
