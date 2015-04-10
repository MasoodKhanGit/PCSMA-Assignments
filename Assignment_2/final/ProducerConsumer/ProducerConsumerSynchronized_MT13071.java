package ProducerConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProducerConsumerSynchronized_MT13071 {

    static class Queue {
        private List<String> mQ = new ArrayList<String>();
        boolean flag = false;
        
        public synchronized void put(String msg) throws InterruptedException {
            while (flag)
                wait();

            mQ.add(msg);
            flag = true;
            System.out.println("Put: " + msg);

            notify();
        }

        public synchronized void take() throws InterruptedException {
            while (!flag) 
                wait();
            
            String str = mQ.remove(0);
            System.out.println("Take: " + str);
            flag = false;
            notify();

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
