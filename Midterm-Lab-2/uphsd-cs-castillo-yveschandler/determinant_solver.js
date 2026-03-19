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

// Function to print matrix
function printMatrix(m) {
    console.log("Matrix:");
    for (let i = 0; i < 3; i++) {
        console.log(`${m[i][0]}  ${m[i][1]}  ${m[i][2]}`);
    }
    console.log();
}

// Function to compute 2x2 minor
function computeMinor(a, b, c, d) {
    return (a * d) - (b * c);
}

// Function to solve determinant
function solveDeterminant(m) {

    console.log("Step 1: Expansion along first row\n");

    let minor1 = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2]);
    let minor2 = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2]);
    let minor3 = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1]);

    console.log(`Minor 1: (${m[1][1]}*${m[2][2]} - ${m[1][2]}*${m[2][1]}) = ${minor1}`);
    console.log(`Minor 2: (${m[1][0]}*${m[2][2]} - ${m[1][2]}*${m[2][0]}) = ${minor2}`);
    console.log(`Minor 3: (${m[1][0]}*${m[2][1]} - ${m[1][1]}*${m[2][0]}) = ${minor3}`);

    let term1 = m[0][0] * minor1;
    let term2 = m[0][1] * minor2;
    let term3 = m[0][2] * minor3;

    console.log("\nStep 2: Cofactor Expansion");
    console.log(`${m[0][0]}(${minor1}) - ${m[0][1]}(${minor2}) + ${m[0][2]}(${minor3})`);

    let det = term1 - term2 + term3;

    console.log("\nStep 3: Final Calculation");
    console.log(`${term1} - ${term2} + ${term3}`);

    return det;
}

// Main
const matrix = [
    [2, 6, 3],
    [4, 1, 5],
    [3, 2, 4]
];

printMatrix(matrix);

const determinant = solveDeterminant(matrix);

console.log(`\nDeterminant = ${determinant}`);

if (determinant === 0) {
    console.log("The matrix is SINGULAR — it has no inverse.");
}       
