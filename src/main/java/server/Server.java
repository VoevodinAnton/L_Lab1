package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 1488;

    private static Server instance;
    private final ExecutorService executorService;

    public Server() {
        this.executorService = Executors.newFixedThreadPool(2);
    }

    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server socket created");

            while (true) {
                Socket socket = serverSocket.accept();

                executorService.execute(new MonoThreadClientHandler(socket));
                System.out.print("Connection accepted");
            }
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }
}
