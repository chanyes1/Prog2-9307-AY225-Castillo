/*
Name: Yves Chandler E. Castillo
Section: BSCS-DS 2
Course Code: Math 101
Assignment: Programming Assignment 3×3 Matrix Determinant Solver
Date: March 19, 2025

Description:
This program computes the determinant of a 3×3 matrix using cofactor expansion.
It prints the matrix, shows each step of the calculation, and outputs the final determinant.
*/

public class DeterminantSolver {

    // Function to print the matrix
    public static void printMatrix(int[][] m) {
        System.out.println("Matrix:");
        for (int i = 0; i < 3; i++) {
            System.out.println(m[i][0] + "  " + m[i][1] + "  " + m[i][2]);
        }
        System.out.println();
    }

    // Function to compute 2x2 minor determinant
    public static int computeMinor(int a, int b, int c, int d) {
        return (a * d) - (b * c);
    }

    // Function to compute determinant using cofactor expansion
    public static int solveDeterminant(int[][] m) {

        System.out.println("Step 1: Expansion along first row\n");

        // Compute minors
        int minor1 = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2]);
        int minor2 = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2]);
        int minor3 = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1]);

        // Print minors
        System.out.println("Minor 1: (" + m[1][1] + "*" + m[2][2] + " - " + m[1][2] + "*" + m[2][1] + ") = " + minor1);
        System.out.println("Minor 2: (" + m[1][0] + "*" + m[2][2] + " - " + m[1][2] + "*" + m[2][0] + ") = " + minor2);
        System.out.println("Minor 3: (" + m[1][0] + "*" + m[2][1] + " - " + m[1][1] + "*" + m[2][0] + ") = " + minor3);

        // Cofactor expansion
        int term1 = m[0][0] * minor1;
        int term2 = m[0][1] * minor2;
        int term3 = m[0][2] * minor3;

        System.out.println("\nStep 2: Cofactor Expansion");
        System.out.println(m[0][0] + "(" + minor1 + ") - " + m[0][1] + "(" + minor2 + ") + " + m[0][2] + "(" + minor3 + ")");

        // Final determinant
        int det = term1 - term2 + term3;

        System.out.println("\nStep 3: Final Calculation");
        System.out.println(term1 + " - " + term2 + " + " + term3);

        return det;
    }

    public static void main(String[] args) {

        // Hardcoded matrix
        int[][] matrix = {
            {2, 6, 3},
            {4, 1, 5},
            {3, 2, 4}
        };

        printMatrix(matrix);

        int determinant = solveDeterminant(matrix);

        System.out.println("\nDeterminant = " + determinant);

        // Check singular
        if (determinant == 0) {
            System.out.println("The matrix is SINGULAR — it has no inverse.");
        }
    }
}
