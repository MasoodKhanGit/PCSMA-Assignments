package PingPong;

import ProducerConsumer.ProducerConsumerSynchronized_MT13071;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PingPongSynchronized_MT13071 {

    static class Sync {
        boolean flag = false;
        public synchronized void put(String msg) throws InterruptedException {
            while (flag) {
                wait();
            }

            flag = true;
            System.out.println("Ping");

            notify();
        }

        public synchronized void take() throws InterruptedException {
            while (!flag) {
                wait();
            }
            System.out.println("Pong");
            flag = false;
            notify();

        }
    } 

    static int mMaxIterations = 10;

    
    public static void main(String argv[]) {
        final Sync buggyQueue = new Sync();

      
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
