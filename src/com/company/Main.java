package com.company;

import Operations.Integral;
import functions.Function;
import functions.Graph;
import functions.basic.*;
import functions.meta.*;
import functions.parsers.FunctionParser;
import functions.parsers.GraphParser;

public class Main {

    public static void main(String[] args) {
        GraphParser graphParser = new GraphParser("[0,3,5,0,4,0,0;0,0,0,8,0,9,15;0,0,0,4,6,0,0;0,0,0,0,0,7,0;0,0,0,8,0,3,11;0,0,0,0,0,0,8;0,0,0,0,0,0,0]");
        Graph graph = graphParser.parseGraph();
        System.out.println(graph.Dijkstra(1,7));
    }
}