package serversocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class OurServer {

    private static Socket clientSocket;

    public static void main(String[] args) throws IOException {

        try(ServerSocket serverSocket = new ServerSocket(29288)) {
            System.out.println("Server started");
            new InputData();
            while(true) {
                clientSocket = serverSocket.accept();
                new ServerThread(clientSocket).start();
            }
        }
    }
}
