package client;

import matrix.Matrix;
import matrix.MatrixUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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

    public Matrix getMatrix() throws IOException {
        Matrix matrix = MatrixUtils.getMatrixFromStream(in);
        return matrix;
    }

    public void sendMatrices(Matrix firstMatrix, Matrix secondMatrix) throws IOException {
        MatrixUtils.sendMatrixToStream(firstMatrix, out);
        MatrixUtils.sendMatrixToStream(secondMatrix, out);
        out.flush();
    }

    public void start() throws IOException {
        socket = new Socket(host, PORT);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }
}
