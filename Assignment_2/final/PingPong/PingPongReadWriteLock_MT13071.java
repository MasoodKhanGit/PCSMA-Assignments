package PingPong;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PingPongReadWriteLock_MT13071 {

    public static int mMaxIterations = 10;
    public static class PlayPingPongThread extends Thread {

        Condition c1, c2;
        Lock lock;
        static boolean flag = false;

        public PlayPingPongThread(String stringToPrint, Lock lock, Condition c1, Condition c2) {
            this.mStringToPrint = stringToPrint;
            this.c1 = c1;
            this.c2 = c2;
            this.lock = lock;
        }

        
        public void run() {
            for (int loopsDone = 1; loopsDone <= mMaxIterations; ++loopsDone) {
                try 
                {
                    lock.lock();
                    if (Thread.currentThread().getName().equals("Thread-0")) {
                        while (flag) {
                            c1.await();
                        }
                    } else {
                        while (!flag) {
                            c1.await();
                        }
                    }
                    if (Thread.currentThread().getName().equals("Thread-0")) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                    System.out.println(mStringToPrint + "(" + loopsDone + ")");
                    c2.signal();

                    lock.unlock();
                } 
                catch (InterruptedException ex) {
                    Logger.getLogger(PingPongConditioinVariablesLocks_MT13071.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        
        private String mStringToPrint;
    }

    public static void main(String[] args) {
        try {
            System.out.println("Ready...Set...Go!");
            ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
//            Lock lock = new ReentrantLock();
            Lock w = lock.writeLock();
            Condition c1 = w.newCondition();
            Condition c2 = w.newCondition();
            PlayPingPongThread ping
                    = new PlayPingPongThread("Ping!", w, c1, c2);
            PlayPingPongThread pong
                    = new PlayPingPongThread("Pong!", w, c2, c1);
            ping.start();
            pong.start();
            ping.join();
            pong.join();

            System.out.println("Done!");
        } catch (java.lang.InterruptedException e) {
        }
    }
}
