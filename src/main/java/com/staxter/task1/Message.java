package com.staxter.task1;

/**
 * Message is a class which holds information about Player communication
 *
 * @see Player
 */
public class Message {
    private final StringBuilder builder;

    public Message(String initial) {
        this.builder = new StringBuilder(initial);
    }

    private Message(Message original, Object value) {
        this(original.toString() + value);
    }

    public void append(int count){
        this.builder.append(count);
    }

    @Override
    public String toString() {
        return builder.toString();
    }

    public static Message newMessage(Message original, Object value) {
        return new Message(original, value);
    }

}
