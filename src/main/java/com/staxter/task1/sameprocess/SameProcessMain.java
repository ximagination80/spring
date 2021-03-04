package com.staxter.task1.sameprocess;

import com.staxter.task1.Message;
import com.staxter.task1.Player;
import com.staxter.task1.StopCondition;

/**
 * Starter class for single thread player game
 */
public class SameProcessMain {

    public static void main(String[] args) {
        StopCondition stopCondition = new StopCondition(20);

        Player initiator = new Player("initiator");
        Player receiver = new Player("receiver");

        Message result = initiator.
                sendMessage(receiver, new Message("TEST -> "), stopCondition);

        System.out.println(result);
    }

}
