package tools;

public class Matrix {
    private double[][] elements;
    private int row, column;

    public Matrix(int row, int column) {
        this.elements = new double[row][column];
        this.row = row;
        this.column = column;
        if (row <= 0 || column <= 0) {
            throw new NumberFormatException("Размерность матрицы не может быть менбше либо равна нулю");
        }
    }

    public Matrix(double[][] elements, int row, int column) {
        this.elements = elements;
        this.row = row;
        this.column = column;
        if (row <= 0 || column <= 0) {
            throw new NumberFormatException("Размерность матрицы не может быть менбше либо равна нулю");
        }
        this.checkMatrixDimensions(elements);
    }

    private void checkMatrixDimensions(double[][] elements) {
        if (elements.length != row) {
            throw new IllegalArgumentException("Количество строк не совпадает");
        }
        for (int i = 0; i < row; ++i) {
            if (elements[i].length != column) {
                throw new IllegalArgumentException("Количество столбцов не совпадает");
            }
        }
    }

    public Matrix subMatrix(int i0, int j0, int i1, int j1) {
        Matrix matrix = new Matrix(i1 - i0 + 1, j1 - j0 + 1);
        double[][] elements = matrix.getAsArray();
        try {
            for (int i = i0; i <= i1; ++i) {
                for (int j = j0; j <= j1; ++j) {
                    elements[i - i0][j - j0] = this.elements[i][j];
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Нельзя взять такую подматрицу");
        }
        return matrix;
    }

    public double[][] getAsArray() {
        return this.elements;
    }

    public Matrix subMatrix(int[] rowIndices, int j0, int j1) {
        Matrix matrix = new Matrix(rowIndices.length, j1 - j0 + 1);
        double[][] elements = matrix.getAsArray();
        try {
            for (int i = 0; i < rowIndices.length; ++i) {
                for (int j = j0; j <= j1; ++j) {
                    elements[i][j - j0] = this.elements[rowIndices[i]][j];
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Нельзя взять такую подматрицу");
        }
        return matrix;
    }

    public double get(int i, int j) {
        return this.elements[i][j];
    }

    public double getAbsMax() {
        double maxItem = 0;
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                if (maxItem < Math.abs(this.elements[i][j]))
                    maxItem = Math.abs(this.elements[i][j]);
            }
        }
        return maxItem;
    }

    public Matrix mult(Matrix matrix) {
        if (column != matrix.row) {
            throw new IllegalArgumentException("Matrix inner dimensions must agree.");
        }
        Matrix matrix0 = new Matrix(row, matrix.column);
        double[][] elements = matrix0.getAsArray();

        for (int i = 0; i < matrix0.row; ++i) {

            for (int j = 0; j < matrix0.column; ++j) {
                for (int k = 0; k < column; ++k) {
                    elements[i][j] += this.elements[i][k] * matrix.elements[k][j];
                }
            }
        }
        return matrix0;
    }

    public Matrix plus(Matrix matrix) {
        if (row != matrix.row || column != matrix.column) {
            throw new IllegalArgumentException("Matrix dimensions must agree.");
        }
        Matrix matrix0 = new Matrix(row, column);
        double[][] elements = matrix0.getAsArray();

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                elements[i][j] = this.elements[i][j] + matrix.elements[i][j];
            }
        }
        return matrix0;
    }

    public Matrix minus(Matrix matrix) {
        if (row != matrix.row || column != matrix.column) {
            throw new IllegalArgumentException("Matrix dimensions must agree.");
        }
        Matrix matrix0 = new Matrix(row, column);
        double[][] elements = matrix0.getAsArray();

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                elements[i][j] = this.elements[i][j] - matrix.elements[i][j];
            }
        }
        return matrix0;
    }

    public double[] getDiagonalArray() {
        double[] diagonal = new double[Math.min(row, column)];

        for (int i = 0; i < row && i < column; ++i) {
            diagonal[i] = this.elements[i][i];
        }
        return diagonal;
    }

    public Matrix div(double[] divisor) {
        if (divisor.length != row) {
            throw new IllegalArgumentException("Не возможно произвести деление матриц");
        }
        Matrix matrix = new Matrix(row, column);
        double[][] elements = matrix.getAsArray();

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                elements[i][j] = this.elements[i][j] / divisor[i];
            }
        }
        return matrix;
    }

    public Matrix copy() {
        Matrix matrix = new Matrix(row, column);
        double[][] elements = matrix.getAsArray();

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < column; ++j) {
                elements[i][j] = this.elements[i][j];
            }
        }
        return matrix;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public static Matrix identity(int size) {
        Matrix matrix = new Matrix(size, size);
        double[][] elements = matrix.getAsArray();

        for (int i = 0; i < size; ++i) elements[i][i] = 1D;
        return matrix;
    }
}
