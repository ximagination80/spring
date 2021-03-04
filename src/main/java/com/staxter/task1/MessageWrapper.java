package com.staxter.task1;

/**
 * Wraps message and number of messages
 * Used for single & multi thread communication
 *
 * @see Player
 * @see com.staxter.task1.parallelprocesses.PlayerProcess
 */
public class MessageWrapper {

    public static final MessageWrapper STOP_SIGNAL = new MessageWrapper(null, -1);

    private final Message message;
    private final int counter;

    public MessageWrapper(Message message, int counter) {
        this.message = message;
        this.counter = counter;
    }

    public Message getMessage() {
        return message;
    }

    public int getCounter() {
        return counter;
    }

}
