package com.staxter.task1;

import java.util.function.Function;

/**
 * Function which checks number of maximum possible messages between players
 * Returns True when number of messages hit the maximum, False if possible to send more messages
 *
 * @see Player
 */
public class StopCondition implements Function<Integer, Boolean> {

    private final int numberOfMessages;

    public StopCondition(int numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    @Override
    public Boolean apply(Integer integer) {
        return integer >= numberOfMessages;
    }
}
