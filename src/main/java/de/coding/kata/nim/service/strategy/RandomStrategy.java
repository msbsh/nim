package de.coding.kata.nim.service.strategy;

import java.util.Random;

/**
 * Computer game strategy for randomly taking matches
 */
public class RandomStrategy implements GameStrategy {

    private static final Random rand = new Random();

    @Override
    public int execute(final int remainingMatches) {
        if(remainingMatches < 1) throw new IllegalArgumentException("Cannot execute strategy!");

        final int maxMatches = Math.min(remainingMatches, MAX_MATCHES);
        return MIN_MATCHES == maxMatches ? 1 : rand.nextInt(maxMatches) + MIN_MATCHES;
    }
}
