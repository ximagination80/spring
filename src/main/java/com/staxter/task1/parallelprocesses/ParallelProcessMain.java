package com.staxter.task1.parallelprocesses;

import com.staxter.task1.Message;
import com.staxter.task1.MessageWrapper;
import com.staxter.task1.StopCondition;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Starter class for multi thread player game
 */
public class ParallelProcessMain {

    public static void main(String[] args) throws InterruptedException {
        StopCondition stopCondition = new StopCondition(20);
        BlockingQueue<MessageWrapper> queue1 = new LinkedBlockingDeque<>();
        BlockingQueue<MessageWrapper> queue2 = new LinkedBlockingDeque<>();

        PlayerProcess initiatorProcess = new PlayerProcess(
                "initiator", queue1, queue2, stopCondition);
        initiatorProcess.start();

        PlayerProcess receiverProcess = new PlayerProcess(
                "receiver", queue2, queue1, stopCondition);
        receiverProcess.start();

        queue2.put(new MessageWrapper(new Message("TEST ->"), 0));
    }
}
