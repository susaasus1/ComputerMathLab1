import tools.JacobiAnswer;
import tools.LinearSystem;
import tools.LinearSystemSolver;
import tools.Matrix;

import java.util.Scanner;

public class Main {
    private double accuracy;
    private int choice;
    private int size;
    private final LinearSystemSolver solver = new LinearSystemSolver();
    private double[][] elements;
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        System.out.println("Выберите хотите ли вы ввести сами или хотите ввести данные из файла");
        System.out.println("1 - вводить данные вручную \n2 - вводить данные из файла");
        choice = Integer.parseInt(scanner.nextLine());
        while (choice >= 3 && choice <= 0) {
            System.out.println("Вы ввели не то что нужно, попробуйте еще раз");
            choice = Integer.parseInt(scanner.nextLine());
        }
        if (choice == 1) {
            inputAccurasy();
            inputMatrix();
            outputSolve();
        }
    }

    private void inputAccurasy() {
        System.out.print("Введите точность: ");
        accuracy = Double.parseDouble(scanner.nextLine());
    }

    private void inputMatrix() {
        System.out.print("Введите размер матрицы: ");
        int j = 0, size = -1;
        double[][] elements;
        String row, num[] = new String[0];
        size = Integer.parseInt(scanner.nextLine().trim());
        System.out.println("Введите матрицу:");
        if (size < 1 || size > 20) {
            throw new RuntimeException();
        }
        elements = new double[size][size + 1];
        int i = 0;
        while (i < size) {
            row = scanner.nextLine().trim().replaceAll(",", ".");
            if (row.isEmpty()) {
                continue;
            }
            num = row.split("(\\s++)");
            if (num.length != size + 1) {
                throw new RuntimeException();
            } else {
                for (j = 0; j <= size; ++j) {
                    elements[i][j] = Double.parseDouble(num[j]);
                }
                i++;
            }
        }
        this.size = size;
        this.elements = elements;
    }

    private void outputSolve() {
        Matrix matrix = new Matrix(elements, size, size + 1);
        LinearSystem system = new LinearSystem(matrix, size);
        JacobiAnswer answer;
        answer = solver.solveByJacobi(system, accuracy);
        System.out.println(answer);
    }
}
