package Array;

import java.util.Scanner;

public class matrixCalculation {

    static Scanner sc = new Scanner(System.in);

    // ── 2D Operations ──────────────────────────────────────────
    static double[][] add(double[][] A, double[][] B) {
        check2D(A, B, "addition");
        double[][] R = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[0].length; j++)
                R[i][j] = A[i][j] + B[i][j];
        return R;
    }

    static double[][] subtract(double[][] A, double[][] B) {
        check2D(A, B, "subtraction");
        double[][] R = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[0].length; j++)
                R[i][j] = A[i][j] - B[i][j];
        return R;
    }

    static double[][] multiply(double[][] A, double[][] B) {
        if (A[0].length != B.length)
            throw new IllegalArgumentException("cols(A) must equal rows(B).");
        double[][] R = new double[A.length][B[0].length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < B[0].length; j++)
                for (int k = 0; k < B.length; k++)
                    R[i][j] += A[i][k] * B[k][j];
        return R;
    }

    static double[][] transpose(double[][] A) {
        double[][] R = new double[A[0].length][A.length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[0].length; j++)
                R[j][i] = A[i][j];
        return R;
    }

    // ── 3D Tensor Operations (Bonus) ───────────────────────────
    static double[][][] tensorAdd(double[][][] A, double[][][] B) {
        check3D(A, B);
        double[][][] R = new double[A.length][A[0].length][A[0][0].length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[0].length; j++)
                for (int k = 0; k < A[0][0].length; k++)
                    R[i][j][k] = A[i][j][k] + B[i][j][k];
        return R;
    }

    static double[][][] tensorTranspose(double[][][] A) {
        double[][][] R = new double[A.length][A[0][0].length][A[0].length];
        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[0].length; j++)
                for (int k = 0; k < A[0][0].length; k++)
                    R[i][k][j] = A[i][j][k];
        return R;
    }

    // ── Validation ─────────────────────────────────────────────
    static void check2D(double[][] A, double[][] B, String op) {
        if (A.length != B.length || A[0].length != B[0].length)
            throw new IllegalArgumentException("Dimensions must match for " + op + ".");
    }

    static void check3D(double[][][] A, double[][][] B) {
        if (A.length != B.length || A[0].length != B[0].length || A[0][0].length != B[0][0].length)
            throw new IllegalArgumentException("Tensor shapes must match.");
    }

    // ── Display ────────────────────────────────────────────────
    static String fmt(double v) {
        return (v == Math.floor(v) && !Double.isInfinite(v))
                ? String.valueOf((int) v) : String.format("%.2f", v);
    }

    static void print(double[][] M) {
        int w = 0;
        for (double[] row : M)
            for (double v : row) w = Math.max(w, fmt(v).length());
        String border = "+" + "-".repeat(M[0].length * (w + 3) - 1) + "+";
        System.out.println(border);
        for (double[] row : M) {
            System.out.print("| ");
            for (double v : row) System.out.printf("%" + w + "s  ", fmt(v));
            System.out.println("|");
        }
        System.out.println(border);
    }

    static void print(double[][][] T) {
        for (int i = 0; i < T.length; i++) {
            System.out.println("Slice [" + i + "]:");
            print(T[i]);
        }
    }

    // ── Input ──────────────────────────────────────────────────
    static double[][] readMatrix(String name) {
        System.out.print(name + " rows: "); int r = sc.nextInt();
        System.out.print(name + " cols: "); int c = sc.nextInt();
        double[][] M = new double[r][c];
        System.out.println("Enter " + r + "x" + c + " values:");
        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++) {
                System.out.print("  [" + i + "][" + j + "]: ");
                M[i][j] = sc.nextDouble();
            }
        print(M);
        return M;
    }

    static double[][][] readTensor(String name) {
        System.out.print(name + " depth: "); int d = sc.nextInt();
        System.out.print(name + " rows:  "); int r = sc.nextInt();
        System.out.print(name + " cols:  "); int c = sc.nextInt();
        double[][][] T = new double[d][r][c];
        System.out.println("Enter values:");
        for (int i = 0; i < d; i++)
            for (int j = 0; j < r; j++)
                for (int k = 0; k < c; k++) {
                    System.out.print("  [" + i + "][" + j + "][" + k + "]: ");
                    T[i][j][k] = sc.nextDouble();
                }
        print(T);
        return T;
    }

    // ── Main ───────────────────────────────────────────────────
    public static void main(String[] args) {
        double[][] A = null, B = null;
        double[][][] tA = null, tB = null;

        while (true) {
            System.out.println("\n+----------------------------------+");
            System.out.println("|      MATRIX CALCULATOR           |");
            System.out.println("+----------------------------------+");
            System.out.println("|  1. Enter Matrix A & B           |");
            System.out.println("|  2. Add          A + B           |");
            System.out.println("|  3. Subtract     A - B           |");
            System.out.println("|  4. Multiply     A x B           |");
            System.out.println("|  5. Transpose    A or B          |");
            System.out.println("|  6. Load Tensor A & B            |");
            System.out.println("|  7. Tensor Add   A + B           |");
            System.out.println("|  8. Tensor Transpose A or B      |");
            System.out.println("|  0. Exit                         |");
            System.out.println("+----------------------------------+");
            System.out.print("Choice: ");
            String choice = sc.next().trim();

            try {
                switch (choice) {
                    case "1" -> { A = readMatrix("A"); B = readMatrix("B"); }
                    case "2" -> { needLoaded(A, B); System.out.println("A + B:"); print(add(A, B)); }
                    case "3" -> { needLoaded(A, B); System.out.println("A - B:"); print(subtract(A, B)); }
                    case "4" -> { needLoaded(A, B); System.out.println("A x B:"); print(multiply(A, B)); }
                    case "5" -> {
                        needLoaded(A, B);
                        System.out.print("Transpose A or B? ");
                        String w = sc.next().trim().toUpperCase();
                        if (!w.equals("A") && !w.equals("B"))
                            throw new IllegalArgumentException("Invalid input. Enter A or B.");
                        print(transpose(w.equals("B") ? B : A));
                    }
                    case "6" -> { tA = readTensor("TA"); tB = readTensor("TB"); }
                    case "7" -> { needLoaded(tA, tB); System.out.println("TA + TB:"); print(tensorAdd(tA, tB)); }
                    case "8" -> {
                        needLoaded(tA, tB);
                        System.out.print("Transpose TA or TB? ");
                        String w = sc.next().trim().toUpperCase();
                        if (!w.equals("TA") && !w.equals("TB"))
                            throw new IllegalArgumentException("Invalid input. Enter TA or TB.");
                        print(tensorTranspose(w.equals("TB") ? tB : tA));
                    }
                    case "0" -> { System.out.println("Goodbye!"); return; }
                    default  -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    static void needLoaded(Object a, Object b) {
        if (a == null || b == null)
            throw new IllegalArgumentException("Not loaded yet. Load matrices first.");
    }
}