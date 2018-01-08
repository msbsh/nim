package de.coding.kata.nim.service.strategy;

import java.util.concurrent.ThreadLocalRandom;

public class RandomStrategy implements GameStrategy {

    private static final int MIN_MATCHES = 1;
    private static final int MAX_MATCHES = 3;

    @Override
    public int execute(final int remainingMatches) {
        final int maxMatches = Math.min(remainingMatches, MAX_MATCHES);
        return MIN_MATCHES == maxMatches ? 1 : ThreadLocalRandom.current().nextInt(MIN_MATCHES, maxMatches);
    }
}
