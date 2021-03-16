package com.company;

import Operations.Integral;
import functions.Function;
import functions.basic.*;
import functions.meta.*;
import functions.parsers.FunctionParser;
import functions.parsers.GraphParser;

public class Main {

    public static void main(String[] args) {
		Function f = new Abs(new Tan());
        System.out.println(f);
		System.out.println(f.derivative());
    }
}