package client;

import matrix.Matrix;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class App {
    private static final String INPUT_DIRECTORY = "src/main/resources/matrices/input/";
    private static final String OUTPUT_DIRECTORY = "src/main/resources/matrices/output/";
    private static final String FILE_EXTENSION = ".txt";

    public static void main(String[] args) {
        Client matrixClient = Client.getInstance();
        matrixClient.setHost("localhost");

        try {
            matrixClient.start();
        } catch (IOException ex){
            System.err.println("Проверьте подключение и попробуйте снова");
            return;
        }

        Scanner in = new Scanner(System.in);
        Matrix firstMatrix = null;
        Matrix secondMatrix = null;
        try{
            System.out.print("First input filename: ");
            String firstInputFilename = in.nextLine();
            File firstInputMatrixFile = new File(INPUT_DIRECTORY + firstInputFilename + FILE_EXTENSION);
            firstMatrix = Matrix.readMatrixFromFile(firstInputMatrixFile);

            System.out.print("Second input  filename: ");
            String secondInputFilename = in.nextLine();
            File secondInputMatrixFile = new File(INPUT_DIRECTORY + secondInputFilename + FILE_EXTENSION);
            secondMatrix = Matrix.readMatrixFromFile(secondInputMatrixFile);
        } catch (IOException ex){
            System.err.println("Не удается считать с файла матрицу");
            return;
        }

        System.out.print("Output filename: ");
        String outputFilename = in.nextLine();

        Matrix matrixResult = null;
        try{
            matrixClient.sendMatrices(firstMatrix, secondMatrix);
            matrixResult = matrixClient.getMatrix();
        } catch (IOException ex){
            System.err.println("Сервер не отвечает");
            return;
        }

        File resultFile = new File(OUTPUT_DIRECTORY + outputFilename + FILE_EXTENSION);
        try {
            Matrix.writeMatrixToFile(matrixResult, resultFile);
        } catch (IOException ex){
            System.err.println("Не удается записать матрицу в файл");
            return;
        }
        in.close();
    }
}
