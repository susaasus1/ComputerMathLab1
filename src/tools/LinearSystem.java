package tools;

public class LinearSystem {
    private Matrix extendedMatrix;
    private int size;

    public LinearSystem(Matrix extendedMatrix, int size) {
        this.extendedMatrix = extendedMatrix;
        this.size = size;
        if (1 > size || size > 20) {
            throw new IllegalArgumentException("Размер должен быть равен от 1 до 20");
        }
        if (extendedMatrix.getRow() != size || extendedMatrix.getColumn() != size + 1) {
            throw new IllegalArgumentException("Размер расширенной матрицы не соответствует");
        }
    }

    public int[] getPermutedRowsIfPossible() {
        int[] rowsIndices = new int[size];
        boolean[] flag = new boolean[size];

        for (int i = 0; i < size; ++i) {
            double absoluteSum = 0D;
            int maxItemIndex = 0;
            for (int j = 0; j < size; ++j) {
                if (Math.abs(extendedMatrix.get(i, maxItemIndex)) < Math.abs(extendedMatrix.get(i, j))) {
                    maxItemIndex = j;
                }
                absoluteSum += Math.abs(extendedMatrix.get(i, j));
            }
            if (2 * Math.abs(extendedMatrix.get(i, maxItemIndex)) > absoluteSum && !flag[maxItemIndex]) {
                flag[maxItemIndex] = true;
                rowsIndices[maxItemIndex] = i;
            } else {
                return null;
            }
        }
        return rowsIndices;
    }

    public void doRowPermutation(int[] rowIndices) {
        if (rowIndices.length != size) {
            throw new IllegalArgumentException("Размер строки не совпадает");
        }
        this.extendedMatrix = extendedMatrix.subMatrix(rowIndices, 0, size);
    }

    public Matrix getMatrixCoefficients() {
        return this.extendedMatrix.subMatrix(0, 0, size - 1, size - 1);
    }

    public Matrix getMatrixFreeMembers() {
        return this.extendedMatrix.subMatrix(0, size, size - 1, size);
    }
}
