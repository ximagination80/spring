package com.staxter.task1;

/**
 * Player provides functionality for
 * sending message to another player
 */
public class Player {

    private final String name;

    public Player(String name) {
        this.name = name;
    }

    public Message sendMessage(Player receiver, Message message, StopCondition stopCondition) {
        return sendMessage(receiver, new MessageWrapper(message, 0), stopCondition);
    }

    private Message sendMessage(Player receiver, MessageWrapper wrapper, StopCondition stopCondition) {
        if (stopCondition.apply(wrapper.getCounter())) {
            return wrapper.getMessage();
        } else {
            int newCounter = wrapper.getCounter() + 1;
            Message newMessage = Message.newMessage(wrapper.getMessage(), newCounter);
            System.out.println(receiver.toString() + " appends " + newCounter+". New message is "+ newMessage);
            return receiver.sendMessage(this, new MessageWrapper(newMessage, newCounter), stopCondition);
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}
