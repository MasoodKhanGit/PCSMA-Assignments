/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multithreadinng;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Masood
 */
public class Server {

    Integer connectNum = 0;

    public void startServer() throws IOException {
        int port = 3456;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server has started: ");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            connectNum++;
            ServerThread serverthread = new ServerThread(clientSocket, connectNum, this);
            Thread th = new Thread(serverthread);
            th.start();

        }
    }
    
    public void stopserver(){
        System.out.println("Server stopped");
        System.exit(0);
    }

    public static void main(String[] args) throws IOException {

        Server server = new Server();
        server.startServer();

    }

}

class ServerThread implements Runnable {

    BufferedReader br;
    BufferedWriter bw;
    DataOutputStream os;

    Socket clientsocket;
    Integer connectNum;
    Server server;

    ServerThread(Socket clientSocket, Integer connectNum, Server server) throws IOException {
        br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        
        os = new DataOutputStream(clientSocket.getOutputStream());
        System.out.println("Connection " + connectNum + " established with: " + clientSocket);

        this.clientsocket = clientsocket;
        this.connectNum = connectNum;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            String incoming;
            boolean serverstop = false;
            while (true) {

                incoming = br.readLine();
                System.out.println("Received " + incoming + " from client" + connectNum);
                int m = Integer.parseInt(incoming);
                if (m == 0) {
                    break;
                }
                if (m == -1) {
                    serverstop = true; // to properly close the connections
                    break;
                }
//                bw.write(m * 2 + "\n");
                
                os.writeBytes(m*2 + "\n");

            }
            System.out.println("Connection " + connectNum + " is closed");
            br.close();
            bw.close();
            os.close();
//            clientsocket.close();

            if (serverstop) {
                server.stopserver();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
