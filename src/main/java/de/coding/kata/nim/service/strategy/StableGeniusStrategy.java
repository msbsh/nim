package de.coding.kata.nim.service.strategy;

public class StableGeniusStrategy implements GameStrategy {

    @Override
    public int execute(int remainingMatches) {
        final int maxMatches = Math.min(remainingMatches, MAX_MATCHES);
        return MIN_MATCHES == maxMatches ? 1 : (remainingMatches > 3 ? maxMatches : remainingMatches - 1);
    }
}
