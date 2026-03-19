# 3×3 Matrix Determinant Solver

Name: Yves Chandler E. Castillo  
Section: BSCS-DS 2  
Course: Math 101 – Linear Algebra, UPHSD Molino Campus  

Assignment
Programming Assignment 3×3 Matrix Determinant Solver (Assignment 12)

Assigned Matrix
2 6 3
4 1 5
3 2 4


## How to Run
### Java
cd /Midterm-Lab-2/uphsd-cs-castillo-yveschandler
javac DeterminantSolver.java
java DeterminantSolver
Output
    yev@yev-HP-Laptop-15-fc0xxx:~/Midterm/Midterm-Lab-2/Midterm-Lab-2/uphsd-cs-castillo-yveschandler$ javac DeterminantSolver.java
    java DeterminantSolver
    Matrix:
    2  6  3
    4  1  5
    3  2  4

    Step 1: Expansion along first row

    Minor 1: (1*4 - 5*2) = -6
    Minor 2: (4*4 - 5*3) = 1
    Minor 3: (4*2 - 1*3) = 5

    Step 2: Cofactor Expansion
    2(-6) - 6(1) + 3(5)

    Step 3: Final Calculation
    -12 - 6 + 15

    Determinant = -3


### Javascript
cd /Midterm-Lab-2/uphsd-cs-castillo-yveschandler
node determinant_solver.js
Output:
    Matrix:
    2  6  3
    4  1  5
    3  2  4

    Step 1: Expansion along first row

    Minor 1: (1*4 - 5*2) = -6
    Minor 2: (4*4 - 5*3) = 1
    Minor 3: (4*2 - 1*3) = 5

    Step 2: Cofactor Expansion
    2(-6) - 6(1) + 3(5)

    Step 3: Final Calculation
    -12 - 6 + 15

    Determinant = -3

