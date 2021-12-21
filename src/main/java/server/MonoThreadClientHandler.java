package server;

import matrix.Matrix;
import matrix.MatrixUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class MonoThreadClientHandler implements Runnable{
    private static Socket socket;

    public MonoThreadClientHandler(Socket socket) {
        MonoThreadClientHandler.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());

            while (!socket.isClosed()) {
                System.out.println("Server reading from channel");
                try {
                    Matrix firstMatrix = MatrixUtils.getMatrixFromStream(objIn);
                    Matrix secondMatrix = MatrixUtils.getMatrixFromStream(objIn);

                    Matrix resultMatrix = Matrix.sum(firstMatrix, secondMatrix);

                    MatrixUtils.sendMatrixToStream(resultMatrix, objOut);
                    objOut.flush();
                } catch (SocketException ex) {
                    System.err.println("connection reset");
                    break;
                }
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
