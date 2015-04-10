package ProducerConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProducerConsumerConditionVariablesLocks_MT13071 {
    

    static class Queue {

        private List<String> mQ = new ArrayList<String>();
        boolean flag = false;

        Lock lock = new ReentrantLock();
        
        Condition c1 = lock.newCondition();
        Condition c2 = lock.newCondition();

        public void put(String msg) throws InterruptedException {
            
            lock.lock();

            while (flag) {
                c1.await();
            }

            mQ.add(msg);
            flag = true;
            System.out.println("Put: " + msg);

            c2.signal();
            lock.unlock();

        }

        public void take() throws InterruptedException {

            lock.lock();

            while (!flag) {
                c2.await();
            }
            String str = mQ.remove(0);
            System.out.println("Take: " + str);
            flag = false;
            c1.signal();
            lock.unlock();

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
//                            System.out.println(buggyQueue.take());
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
