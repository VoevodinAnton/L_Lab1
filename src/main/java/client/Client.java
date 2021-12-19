package client;

import matrix.Matrix;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private static final int PORT = 1488;

    private static Client instance;
    private String host;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Matrix getMatrix() throws IOException, ClassNotFoundException {
        Matrix matrix = (Matrix) in.readObject();

        return matrix;
    }

    public void sendMatrices(double[][] matrix1, double[][] matrix2) throws IOException {
        List<double[][]> matrices = new ArrayList();
        matrices.add(matrix1);
        matrices.add(matrix2);

        out.writeObject(matrices);
        out.flush();
    }

    public void start() throws IOException {
        socket = new Socket(host, PORT);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }
}
