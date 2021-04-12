package com.company;

import Operations.Integral;
import Operations.Systems;
import functions.Function;
import functions.Graph;
import functions.Matrix;
import functions.basic.*;
import functions.meta.*;
import functions.parsers.FunctionParser;
import functions.parsers.GraphParser;
import functions.parsers.MatrixParser;

public class Main {

    public static void main(String[] args) {
        MatrixParser matrixParser = new MatrixParser("[1,2,3;3,2, 1; 4,5,7]");
        Matrix matrix = matrixParser.parseMatrix();
        System.out.println(matrix);
        Matrix[] lu = matrix.LU_decomposition();
        System.out.println("L\n");
        System.out.println(lu[0]);
        System.out.println("U\n");
        System.out.println(lu[1]);
        System.out.println(Systems.from_p_to_q("AAABF56", 16, 10));
        Function f = new Comp(new Sin(), new Cos());
        System.out.println();
        System.out.println(f);
        System.out.println(f.derivative());

    }
}