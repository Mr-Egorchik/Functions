package functions;

import java.util.Arrays;

public class Graph {
    private double[][] weights_matrix; // матрица весов ребер
    private int n; // количество вершин

    public Graph (double[][] m, int s) {
        weights_matrix = m;
        n = s;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                res.append(weights_matrix[i][j])
                    .append(' ');
                if (j == n-1)
                    res.setCharAt(res.length()-1, '\n');
            }
        }
        return res.toString();
    }
}
