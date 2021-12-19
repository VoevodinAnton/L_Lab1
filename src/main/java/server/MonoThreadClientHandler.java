package server;

import matrix.Matrix;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

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
                    ArrayList<double[][]> matrices = (ArrayList<double[][]>) objIn.readObject();
                    if (matrices != null || !matrices.isEmpty()) {
                        Matrix matrix1 = new Matrix(matrices.get(0));
                        Matrix matrix2 = new Matrix(matrices.get(1));
                        Matrix matrixResult = Matrix.sum(matrix1, matrix2);

                        objOut.writeObject(matrixResult);
                        objOut.flush();
                    }
                } catch (SocketException ex) {
                    System.err.println("connection reset");
                    break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private ArrayList<Matrix> toMatrices(String matrices){
        return null;
    }
}
