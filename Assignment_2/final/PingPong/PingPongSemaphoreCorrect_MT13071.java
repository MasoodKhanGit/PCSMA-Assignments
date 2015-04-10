package PingPong;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PingPongSemaphoreCorrect_MT13071 {

    public static int mMaxIterations = 10;
    static Semaphore sem = new Semaphore(1);

    public static class PlayPingPongThread extends Thread {

        private String mStringToPrint;
        Semaphore p = new Semaphore(1);
        Semaphore q = new Semaphore(0);

        public PlayPingPongThread(String stringToPrint, Semaphore p, Semaphore q) {
            this.mStringToPrint = stringToPrint;
            this.p = p;
            this.q = q;
        }

        public void run() {
            for (int loopsDone = 1; loopsDone <= mMaxIterations; ++loopsDone) {

                try {
                    p.acquire();
                    System.out.println(mStringToPrint + "(" + loopsDone + ")");
                    q.release();
                } catch (InterruptedException ex) {
                    Logger.getLogger(PingPongSemaphoreCorrect_MT13071.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }

    public static void main(String[] args) {
        try {
            System.out.println("Ready...Set...Go!");

            Semaphore p = new Semaphore(1);
            Semaphore q = new Semaphore(0);
            // Ping pong threads creatioin
            PlayPingPongThread ping = new PlayPingPongThread("Ping!", p, q);
            PlayPingPongThread pong = new PlayPingPongThread("Pong!", q, p); 
            //callaing respective run methods
            ping.start();
            pong.start();

            // exit both threads before main
            ping.join();
            pong.join();

            System.out.println("Done!");
        } catch (java.lang.InterruptedException e) {
        }
    }
}
