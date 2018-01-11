package de.coding.kata.nim.service.strategy;

import de.coding.kata.nim.RepeatRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.test.annotation.Repeat;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomStrategyTest {

    private static final int RANDOM_REPEAT_COUNT = 1024;

    @Rule
    public RepeatRule repeatRule = new RepeatRule();

    private RandomStrategy strategy;

    @Before
    public void setup() {
        strategy = new RandomStrategy();
    }

    @Test
    @Repeat(value = RANDOM_REPEAT_COUNT)
    public void testRandom() {
        assertThat(strategy.execute(13)).isBetween(RandomStrategy.MIN_MATCHES, RandomStrategy.MAX_MATCHES);
    }

    @Test
    @Repeat(value = RANDOM_REPEAT_COUNT)
    public void testLastThree() {
        assertThat(strategy.execute(3)).isBetween(RandomStrategy.MIN_MATCHES, RandomStrategy.MAX_MATCHES);
    }

    @Test
    @Repeat(value = RANDOM_REPEAT_COUNT)
    public void testLastTwo() {
        assertThat(strategy.execute(2)).isBetween(RandomStrategy.MIN_MATCHES, 2);
    }

    @Test
    @Repeat(value = RANDOM_REPEAT_COUNT)
    public void testLastOne() {
        assertThat(strategy.execute(1)).isEqualTo(1);
    }

    @Test(expected = IllegalArgumentException.class)
    @Repeat(value = RANDOM_REPEAT_COUNT)
    public void testNoneAvailable() {
        strategy.execute(0);
    }

}
