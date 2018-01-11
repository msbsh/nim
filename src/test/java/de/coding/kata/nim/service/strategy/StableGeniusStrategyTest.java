package de.coding.kata.nim.service.strategy;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StableGeniusStrategyTest {

    private StableGeniusStrategy strategy;

    @Before
    public void setup() {
        strategy = new StableGeniusStrategy();
    }

    @Test
    public void testTakeMax() {
        assertThat(strategy.execute(13)).isEqualTo(GameStrategy.MAX_MATCHES);
    }

    @Test
    public void testLastThree() {
        assertThat(strategy.execute(3)).isEqualTo(2);
    }

    @Test
    public void testLastTwo() {
        assertThat(strategy.execute(2)).isEqualTo(1);
    }

    @Test
    public void testLastOne() {
        assertThat(strategy.execute(1)).isEqualTo(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoneAvailable() {
        strategy.execute(0);
    }

}
