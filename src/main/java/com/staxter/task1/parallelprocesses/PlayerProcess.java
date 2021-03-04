package com.staxter.task1.parallelprocesses;

import com.staxter.task1.Message;
import com.staxter.task1.MessageWrapper;
import com.staxter.task1.StopCondition;

import java.util.concurrent.BlockingQueue;

/**
 * Concurrent implementation of Player class.
 * Runs in the new thread, communicates with other players via queues
 */
public class PlayerProcess extends Thread {

    private final BlockingQueue<MessageWrapper> readQueue;
    private final BlockingQueue<MessageWrapper> writeQueue;
    private final StopCondition stopCondition;

    public PlayerProcess(String processName,
                         BlockingQueue<MessageWrapper> readQueue,
                         BlockingQueue<MessageWrapper> writeQueue,
                         StopCondition stopCondition) {
        super(processName);
        this.readQueue = readQueue;
        this.writeQueue = writeQueue;
        this.stopCondition = stopCondition;
    }

    @Override
    public void run() {
        while (true) {
            MessageWrapper wrapper = readOrWait();

            if (wrapper == MessageWrapper.STOP_SIGNAL) {
                System.out.println(getName() + " received stop signal");
                break;
            } else if (stopCondition.apply(wrapper.getCounter())) {
                System.out.println(getName() + " hit stop condition. Sending stop signal to other players");
                writeOrWait(MessageWrapper.STOP_SIGNAL);
                break;
            } else {
                int newCounter = wrapper.getCounter() + 1;
                Message newMessage = Message.newMessage(wrapper.getMessage(), newCounter);
                System.out.println(getName() + " appends " + newCounter + ". New message is " + newMessage);
                writeOrWait(new MessageWrapper(newMessage, newCounter));
            }
        }

        System.out.println("THREAD " + getName() + " EXITED ");
    }

    private void writeOrWait(MessageWrapper messageWrapper) {
        try {
            writeQueue.put(messageWrapper);
        } catch (InterruptedException shouldNotHappen) {
            throw new RuntimeException(shouldNotHappen);
        }
    }

    private MessageWrapper readOrWait() {
        try {
            return readQueue.take();
        } catch (InterruptedException shouldNotHappen) {
            throw new RuntimeException(shouldNotHappen);
        }
    }
}