package de.coding.kata.nim;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.springframework.test.annotation.Repeat;

/**
 * Inspired by {@see https://stackoverflow.com/questions/1492856/easy-way-of-running-the-same-junit-test-over-and-over#1493121}
 */
public class RepeatRule implements TestRule {

    private static class RepeatStatement extends Statement {
        private final Statement statement;
        private final int repeat;

        RepeatStatement(Statement statement, int repeat) {
            this.statement = statement;
            this.repeat = repeat;
        }

        @Override
        public void evaluate() throws Throwable {
            for (int i = 0; i < repeat; i++) {
                statement.evaluate();
            }
        }

    }

    @Override
    public Statement apply(Statement statement, Description description) {
        Statement result = statement;
        Repeat repeat = description.getAnnotation(Repeat.class);
        if (repeat != null) {
            int times = repeat.value();
            result = new RepeatStatement(statement, times);
        }
        return result;
    }
}