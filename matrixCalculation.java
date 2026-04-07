package Array;
import java.util.Scanner;

/**
 * Matrix Calculator
 * Supports: Addition, Subtraction, Multiplication, Transpose
 * BONUS: 3D Tensor Addition and Transpose
 */
public class matrixCalculation {

    // ─────────────────────────────────────────────
    //  2D MATRIX OPERATIONS
    // ─────────────────────────────────────────────

    /** Add two matrices of the same dimensions */
    public static double[][] add(double[][] A, double[][] B) {
        int rows = A.length, cols = A[0].length;
        if (rows != B.length || cols != B[0].length)
            throw new IllegalArgumentException(
                    "Matrix dimensions must match for addition: "
                            + rows + "x" + cols + " vs " + B.length + "x" + B[0].length);

        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result[i][j] = A[i][j] + B[i][j];
        return result;
    }

    /** Subtract matrix B from matrix A */
    public static double[][] subtract(double[][] A, double[][] B) {
        int rows = A.length, cols = A[0].length;
        if (rows != B.length || cols != B[0].length)
            throw new IllegalArgumentException(
                    "Matrix dimensions must match for subtraction: "
                            + rows + "x" + cols + " vs " + B.length + "x" + B[0].length);

        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result[i][j] = A[i][j] - B[i][j];
        return result;
    }

    /** Multiply two matrices: A is (m x n), B is (n x p) → result is (m x p) */
    public static double[][] multiply(double[][] A, double[][] B) {
        int m = A.length, n = A[0].length, p = B[0].length;
        if (n != B.length)
            throw new IllegalArgumentException(
                    "Invalid dimensions for multiplication: columns of A (" + n
                            + ") must equal rows of B (" + B.length + ")");

        double[][] result = new double[m][p];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < p; j++)
                for (int k = 0; k < n; k++)
                    result[i][j] += A[i][k] * B[k][j];
        return result;
    }

    /** Transpose a matrix: swap rows and columns */
    public static double[][] transpose(double[][] A) {
        int rows = A.length, cols = A[0].length;
        double[][] result = new double[cols][rows];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                result[j][i] = A[i][j];
        return result;
    }

    // ─────────────────────────────────────────────
    //  🎁 BONUS: 3D TENSOR OPERATIONS
    // ─────────────────────────────────────────────

    /**
     * Add two 3D tensors of the same shape [depth][rows][cols].
     * Works element-wise across all three dimensions.
     */
    public static double[][][] tensorAdd(double[][][] A, double[][][] B) {
        int d = A.length, r = A[0].length, c = A[0][0].length;
        if (d != B.length || r != B[0].length || c != B[0][0].length)
            throw new IllegalArgumentException(
                    "Tensor shapes must match for addition.");

        double[][][] result = new double[d][r][c];
        for (int i = 0; i < d; i++)
            for (int j = 0; j < r; j++)
                for (int k = 0; k < c; k++)
                    result[i][j][k] = A[i][j][k] + B[i][j][k];
        return result;
    }

    /**
     * Transpose a 3D tensor by swapping rows and columns in each depth slice.
     * Shape [depth][rows][cols] → [depth][cols][rows]
     */
    public static double[][][] tensorTranspose(double[][][] A) {
        int d = A.length, r = A[0].length, c = A[0][0].length;
        double[][][] result = new double[d][c][r];
        for (int i = 0; i < d; i++)
            for (int j = 0; j < r; j++)
                for (int k = 0; k < c; k++)
                    result[i][k][j] = A[i][j][k];
        return result;
    }

    // ─────────────────────────────────────────────
    //  DISPLAY HELPERS
    // ─────────────────────────────────────────────

    /** Pretty-print a 2D matrix with aligned columns */
    public static void printMatrix(double[][] M) {
        // Find max width for alignment
        int width = 0;
        for (double[] row : M)
            for (double val : row) {
                String s = formatVal(val);
                if (s.length() > width) width = s.length();
            }

        String border = "+" + "-".repeat(M[0].length * (width + 3) - 1) + "+";
        System.out.println(border);
        for (double[] row : M) {
            System.out.print("| ");
            for (double val : row)
                System.out.printf("%" + (width) + "s  ", formatVal(val));
            System.out.println("|");
        }
        System.out.println(border);
    }

    /** Pretty-print a 3D tensor slice by slice */
    public static void printTensor(double[][][] T) {
        for (int i = 0; i < T.length; i++) {
            System.out.println("  Slice [" + i + "]:");
            printMatrix(T[i]);
        }
    }

    /** Format a double: show as integer if whole number, else 2 decimal places */
    private static String formatVal(double v) {
        if (v == Math.floor(v) && !Double.isInfinite(v))
            return String.valueOf((int) v);
        return String.format("%.2f", v);
    }

    // ─────────────────────────────────────────────
    //  INPUT HELPERS
    // ─────────────────────────────────────────────

    private static Scanner sc = new Scanner(System.in);

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextInt()) {
            System.out.print("  Please enter a valid integer: ");
            sc.next();
        }
        return sc.nextInt();
    }

    private static double readDouble(String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextDouble()) {
            System.out.print("  Please enter a valid number: ");
            sc.next();
        }
        return sc.nextDouble();
    }

    /** Read a 2D matrix from the user */
    private static double[][] readMatrix(String name) {
        int rows = readInt("  " + name + " rows: ");
        int cols = readInt("  " + name + " cols: ");
        double[][] M = new double[rows][cols];
        System.out.println("  Enter elements row by row:");
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                M[i][j] = readDouble("    [" + i + "][" + j + "]: ");
        return M;
    }

    /** Read a 3D tensor from the user */
    private static double[][][] readTensor(String name) {
        int depth = readInt("  " + name + " depth: ");
        int rows  = readInt("  " + name + " rows:  ");
        int cols  = readInt("  " + name + " cols:  ");
        double[][][] T = new double[depth][rows][cols];
        System.out.println("  Enter elements by [depth][row][col]:");
        for (int i = 0; i < depth; i++)
            for (int j = 0; j < rows; j++)
                for (int k = 0; k < cols; k++)
                    T[i][j][k] = readDouble("    [" + i + "][" + j + "][" + k + "]: ");
        return T;
    }

    // ─────────────────────────────────────────────
    //  DEMO (no user input – shows every feature)
    // ─────────────────────────────────────────────

    private static void runDemo() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         DEMO MODE – ALL FEATURES      ║");
        System.out.println("╚══════════════════════════════════════╝");

        double[][] A = {{1, 2, 3}, {4, 5, 6}};
        double[][] B = {{7, 8, 9}, {1, 2, 3}};
        double[][] C = {{1, 2}, {3, 4}, {5, 6}};

        System.out.println("\n── Matrix A (2×3) ──");
        printMatrix(A);

        System.out.println("\n── Matrix B (2×3) ──");
        printMatrix(B);

        System.out.println("\n── A + B ──");
        printMatrix(add(A, B));

        System.out.println("\n── A − B ──");
        printMatrix(subtract(A, B));

        System.out.println("\n── Matrix C (3×2) ──");
        printMatrix(C);

        System.out.println("\n── A × C  (2×3 × 3×2 → 2×2) ──");
        printMatrix(multiply(A, C));

        System.out.println("\n── Transpose of A  (2×3 → 3×2) ──");
        printMatrix(transpose(A));

        // BONUS: 3D Tensors
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        🎁 BONUS: 3D TENSORS           ║");
        System.out.println("╚══════════════════════════════════════╝");

        double[][][] T1 = {
                {{1, 2}, {3, 4}},
                {{5, 6}, {7, 8}}
        };
        double[][][] T2 = {
                {{9, 8}, {7, 6}},
                {{5, 4}, {3, 2}}
        };

        System.out.println("\n── Tensor T1 (2×2×2) ──");
        printTensor(T1);

        System.out.println("\n── Tensor T2 (2×2×2) ──");
        printTensor(T2);

        System.out.println("\n── T1 + T2 (element-wise) ──");
        printTensor(tensorAdd(T1, T2));

        System.out.println("\n── Transpose of T1 (rows ↔ cols per slice) ──");
        printTensor(tensorTranspose(T1));
    }

    // ─────────────────────────────────────────────
    //  INTERACTIVE MENU
    // ─────────────────────────────────────────────

    private static void runInteractive() {
        while (true) {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║       MATRIX CALCULATOR          ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1. Matrix Addition              ║");
            System.out.println("║  2. Matrix Subtraction           ║");
            System.out.println("║  3. Matrix Multiplication        ║");
            System.out.println("║  4. Matrix Transpose             ║");
            System.out.println("║  5. Tensor Addition (3D)         ║");
            System.out.println("║  6. Tensor Transpose (3D)        ║");
            System.out.println("║  7. Run Demo                     ║");
            System.out.println("║  0. Exit                         ║");
            System.out.println("╚══════════════════════════════════╝");

            int choice = readInt("Choose an option: ");

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.println("\n── Matrix Addition ──");
                        double[][] X = readMatrix("Matrix A");
                        double[][] Y = readMatrix("Matrix B");
                        System.out.println("\nResult (A + B):");
                        printMatrix(add(X, Y));
                    }
                    case 2 -> {
                        System.out.println("\n── Matrix Subtraction ──");
                        double[][] X = readMatrix("Matrix A");
                        double[][] Y = readMatrix("Matrix B");
                        System.out.println("\nResult (A − B):");
                        printMatrix(subtract(X, Y));
                    }
                    case 3 -> {
                        System.out.println("\n── Matrix Multiplication ──");
                        System.out.println("  Note: cols(A) must equal rows(B)");
                        double[][] X = readMatrix("Matrix A");
                        double[][] Y = readMatrix("Matrix B");
                        System.out.println("\nResult (A × B):");
                        printMatrix(multiply(X, Y));
                    }
                    case 4 -> {
                        System.out.println("\n── Matrix Transpose ──");
                        double[][] X = readMatrix("Matrix A");
                        System.out.println("\nTranspose of A:");
                        printMatrix(transpose(X));
                    }
                    case 5 -> {
                        System.out.println("\n── 3D Tensor Addition ──");
                        double[][][] X = readTensor("Tensor A");
                        double[][][] Y = readTensor("Tensor B");
                        System.out.println("\nResult (A + B):");
                        printTensor(tensorAdd(X, Y));
                    }
                    case 6 -> {
                        System.out.println("\n── 3D Tensor Transpose ──");
                        double[][][] X = readTensor("Tensor A");
                        System.out.println("\nTransposed Tensor:");
                        printTensor(tensorTranspose(X));
                    }
                    case 7 -> runDemo();
                    case 0 -> {
                        System.out.println("\nGoodbye!");
                        return;
                    }
                    default -> System.out.println("  Invalid choice. Please select 0–7.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("\n  ⚠ Error: " + e.getMessage());
            }
        }
    }

    // ─────────────────────────────────────────────
    //  ENTRY POINT
    // ─────────────────────────────────────────────

    public static void main(String[] args) {
        System.out.println("Starting in Demo Mode...");
        System.out.println("(To use interactively, call runInteractive() or change main)\n");
        runDemo();

        System.out.println("\n\n─────────────────────────────────────");
        System.out.println("Now switching to Interactive Mode...");
        runInteractive();
    }
}