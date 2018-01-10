package de.coding.kata.nim.service.strategy;

import java.util.concurrent.ThreadLocalRandom;

public class RandomStrategy implements GameStrategy {

    @Override
    public int execute(final int remainingMatches) {
        final int maxMatches = Math.min(remainingMatches, MAX_MATCHES);
        return MIN_MATCHES == maxMatches ? 1 : ThreadLocalRandom.current().nextInt(MIN_MATCHES, maxMatches);
    }
}
