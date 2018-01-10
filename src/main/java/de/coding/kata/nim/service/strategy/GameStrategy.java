package de.coding.kata.nim.service.strategy;

public interface GameStrategy {

    int MIN_MATCHES = 1;
    int MAX_MATCHES = 3;

    int execute(final int remainingMatches);

}
