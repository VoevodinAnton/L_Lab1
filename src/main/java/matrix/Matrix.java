package matrix;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Matrix implements Serializable {

    @Serial
    private static final long serialVersionUID = 5248799925194507175L;
    private double[][] matrix;

    public Matrix(int row, int column) {
        this.matrix = new double[row][column];
    }

    public Matrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public int getNumberOfRows() {
        return matrix.length;
    }

    public int getNumberOfColumn() {
        return matrix[0].length;
    }

    public double getValue(int row, int column) {
        return matrix[row][column];
    }

    public void updateValue(int row, int column, double value) {
        matrix[row][column] = value;
    }

    public static Matrix sum(Matrix a, Matrix b) {
        Matrix matrixResult = new Matrix(a.getNumberOfRows(), a.getNumberOfColumn());
        matrixResult.matrix = IntStream.range(0, a.matrix.length)
                .mapToObj(r -> IntStream.range(0, a.matrix[r].length)
                        .mapToDouble(c -> a.matrix[r][c] + b.matrix[r][c]).toArray())
                .toArray(double[][]::new);
        return matrixResult;
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        Matrix matrixResult = new Matrix(a.getNumberOfColumn(), a.getNumberOfRows());

        matrixResult.matrix = Arrays.stream(a.matrix)
                .map(r -> IntStream.range(0, b.matrix[0].length)
                        .mapToDouble(i -> IntStream.range(0, b.matrix.length)
                                .mapToDouble(j -> r[j] * b.matrix[j][i]).sum())
                        .toArray())
                .toArray(double[][]::new);

        return matrixResult;
    }

    public static void writeMatrixToFile(double[][] matrix, String path) throws IOException {
        File file = new File(path);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                bw.write(String.valueOf(matrix[i][j]));
                bw.write(" ");
            }
            bw.newLine();
        }
        bw.close();
    }

    public static double[][] readMatrixFromFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));

        List<String> lines = new ArrayList<>();
        while (br.ready()) {
            lines.add(br.readLine());
        }
        int matrixWidth = lines.get(0).split(" ").length;
        int matrixHeight = lines.size();

        double[][] matrix = new double[matrixHeight][matrixWidth];

        for (int i = 0; i < matrixHeight; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                String[] line = lines.get(i).split(" ");
                matrix[i][j] = Double.parseDouble(line[j]);

            }
        }
        return matrix;
    }

    public String toString() {
        StringBuilder matrixToString = new StringBuilder();
        for (int i = 0; i < this.getNumberOfRows(); i++) {
            matrixToString.append(Arrays.toString(this.matrix[i]));
            matrixToString.append("\n");
        }

        return matrixToString.toString();
    }
}
