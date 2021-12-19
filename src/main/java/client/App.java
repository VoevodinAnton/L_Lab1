package client;

import matrix.Matrix;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class App {
    private static final String INPUT_DIRECTORY = "src/main/resources/matrices/input/";
    private static final String OUTPUT_DIRECTORY = "src/main/resources/matrices/output/";
    private static final String FILE_EXTENSION = ".txt";

    public static void main(String[] args) throws IOException, ClassNotFoundException {


        Client matrixClient = Client.getInstance();
        matrixClient.setHost("localhost");

        matrixClient.start();

        Scanner in = new Scanner(System.in);

        System.out.print("First input filename: ");
        String firstInputFilename = in.nextLine();
        File firstInputMatrixFile = new File(INPUT_DIRECTORY + firstInputFilename + FILE_EXTENSION);
        double[][] firstMatrix = Matrix.readMatrixFromFile(firstInputMatrixFile);

        System.out.print("Second input  filename: ");
        String secondInputFilename = in.nextLine();
        File secondInputMatrixFile = new File(INPUT_DIRECTORY + secondInputFilename + FILE_EXTENSION);
        double[][] secondMatrix = Matrix.readMatrixFromFile(secondInputMatrixFile);

        System.out.print("Output filename: ");
        String outputFilename = in.nextLine();


        matrixClient.sendMatrices(firstMatrix, secondMatrix);
        Matrix matrixResult = matrixClient.getMatrix();

        Matrix.writeMatrixToFile(matrixResult.getMatrix(), OUTPUT_DIRECTORY + outputFilename + FILE_EXTENSION);

        in.close();
    }
}
