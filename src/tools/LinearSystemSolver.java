package tools;

public class LinearSystemSolver {

    public LinearSystemSolver() {
    }

    public JacobiAnswer solveByJacobi(LinearSystem system, double accuracy) {
        int[] rowIndices = system.getPermutedRowsIfPossible();

        if (rowIndices == null) {
            throw new RuntimeException("Не возможно получить диагональное преобладание");
        } else {
            system.doRowPermutation(rowIndices);
        }
        Matrix coefficients = system.getMatrixCoefficients();
        Matrix freeMembers = system.getMatrixFreeMembers();

        return iterate(coefficients, freeMembers, accuracy);
    }


    private JacobiAnswer iterate(Matrix coefficients, Matrix freeMembers, double accuracy) {
        double[] diagonal = coefficients.getDiagonalArray();

        Matrix a = coefficients.div(diagonal).minus(Matrix.identity(diagonal.length));
        Matrix b = freeMembers.div(diagonal);

        Matrix prev, next = b.copy();
        int iters = 0;
        do {
            prev = next.copy();
            next = b.minus(a.mult(prev)); // X = B - A * X
            iters++;
        } while (next.minus(prev).getAbsMax() > Math.abs(accuracy));

        Matrix errors = next.minus(prev);

        return new JacobiAnswer(next, errors, iters);
    }
}
