package matrix;

import matrix.Matrix;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MatrixUtils {

    public static void sendMatrixToStream(Matrix matrix, ObjectOutputStream objOut){
        int numberOfRows = matrix.getNumberOfRows();
        int numberOfColumn = matrix.getNumberOfColumn();
        try {
            objOut.write(numberOfRows);
            objOut.write(numberOfColumn);

            for (int i = 0; i < numberOfRows; i++) {
                for (int j = 0; j < numberOfColumn; j++) {
                    objOut.writeDouble(matrix.getValue(i, j));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Matrix getMatrixFromStream(ObjectInputStream objIn) throws IOException {
        int numberOfRows = objIn.read();
        int numberOfColumn = objIn.read();

        Matrix matrix = new Matrix(numberOfRows, numberOfColumn);

        for (int i = 0; i < numberOfRows; i++){
            for (int j = 0; j < numberOfColumn; j++){
                double value = objIn.readDouble();
                matrix.updateValue(i, j, value);
            }
        }
        return matrix;
    }
}
