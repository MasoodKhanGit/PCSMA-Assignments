package ProducerConsumer;

import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProducerConsumerSemaphore_MT13071 {
    

    static class Queue {

        
        private List<String> mQ = new ArrayList<String>();
        boolean flag = false;

        Semaphore scons = new Semaphore(0);
        Semaphore sprod = new Semaphore(1);

        public void put(String msg) throws InterruptedException {

            sprod.acquire();
            mQ.add(msg);
            System.out.println("Put: " + msg);
            scons.release();

        }

        public void take() throws InterruptedException {

            scons.acquire();
            String str = mQ.remove(0);
            System.out.println("Take: " + str);
            sprod.release();

        }
    } 

    static int mMaxIterations = 100;

    public static void main(String argv[]) {
        final Queue buggyQueue = new Queue();

        Thread producer
                = new Thread(new Runnable() {
                    public void run() {
                        for (int i = 0; i < mMaxIterations; i++) {
                            try {
                                buggyQueue.put(Integer.toString(i));
                            } catch (InterruptedException ex) {
                                Logger.getLogger(ProducerConsumerSynchronized_MT13071.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
        
        Thread consumer
                = new Thread(new Runnable() {
                    public void run() {
                        for (int i = 0; i < mMaxIterations; i++) {
                            try {
                                buggyQueue.take();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(ProducerConsumerSynchronized_MT13071.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });


        producer.start();
        consumer.start();
    } 
} 
