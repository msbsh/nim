package de.coding.kata.nim.service.state;

import de.coding.kata.nim.entity.Game;
import de.coding.kata.nim.entity.Player;

public class StateRunning implements GameState {

    private boolean canSubtract(final int remainingMatches, final int matchesToTake) {
        return (remainingMatches - matchesToTake) >= 0;
    }

    @Override
    public void takeMatches(final Game context, final Player player, final int matchesToTake) {
        int remainingMatches = context.getRemainingMatches();

        if(!canSubtract(remainingMatches, matchesToTake)) {
            throw new IllegalArgumentException(
                    String.format("Cannot take %d of %d matches!", matchesToTake, remainingMatches));
        }

        if((remainingMatches -= matchesToTake) == 0) {
            context.setGameState(new StateEnded());
            context.endGame(player);
        }

        context.setRemainingMatches(remainingMatches);
    }

    @Override
    public boolean isMyTurn(final Game context, final Player player) {
        return context.getCurrentPlayer().equals(player);
    }

    @Override
    public boolean isRunning(final Game context) {
        return true;
    }

}
