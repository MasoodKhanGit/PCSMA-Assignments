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
import java.net.Socket;

/**
 *
 * @author Masood
 */
public class Client {
    
        Socket clientSocket = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        DataOutputStream os = null;
    
    public void clientConnect() throws IOException{
        
        int port = 3456;
        clientSocket = new Socket("localhost", port);
        br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
os = new DataOutputStream(clientSocket.getOutputStream());
        
        while (true) {
            System.out.print("Enter you number (0 to stop client and -1 to stop server): ");
            BufferedReader bufread = new BufferedReader(new InputStreamReader(System.in));
            String outgoing;
            outgoing = bufread.readLine();
//            bw.write(outgoing);
            os.writeBytes( outgoing + "\n" );
            int m = Integer.parseInt(outgoing);
            if(m ==0 || m==-1){
                break;
            }
            String incoming = br.readLine();
            System.out.println("From Server - Double of number is: " + incoming);
            
        }
        
        br.close();
        bw.close();
        os.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.clientConnect();      
    }

}
