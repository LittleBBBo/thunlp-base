/*
 * Decompiled with CFR 0_123.
 */
package org.thunlp.matrix.pagerank;

import java.io.PrintStream;
import java.util.ArrayList;
import org.thunlp.matrix.MatrixInterface;
import org.thunlp.matrix.NormalMatrix;
import org.thunlp.matrix.SparseMatrix;

public class PageRank {
    public static final double DEFAULT_D = 0.85;
    public static final double DEFAULT_I = 1.0;

    public static void prepareMatrix(MatrixInterface adjMatrix) {
        if (adjMatrix instanceof SparseMatrix) {
            PageRank.prepareSparseMatrix((SparseMatrix)adjMatrix);
        } else {
            for (int i = 0; i != adjMatrix.getRowsCount(); ++i) {
                int j;
                double sum = 0.0;
                for (j = 0; j != adjMatrix.getColsCount(); ++j) {
                    sum += adjMatrix.get(i, j);
                }
                if (sum != 0.0) {
                    for (j = 0; j != adjMatrix.getColsCount(); ++j) {
                        adjMatrix.set(i, j, adjMatrix.get(i, j) / sum);
                    }
                    continue;
                }
                double tmp = 1.0 / (double)adjMatrix.getColsCount();
                for (int j2 = 0; j2 != adjMatrix.getColsCount(); ++j2) {
                    adjMatrix.set(i, j2, tmp);
                }
            }
            adjMatrix.inv();
        }
    }

    protected static void prepareSparseMatrix(SparseMatrix adjMatrix) {
        for (int i = 0; i != adjMatrix.getRowsCount(); ++i) {
            int j;
            double sum = 0.0;
            ArrayList<SparseMatrix.Pair> row = adjMatrix.getRow(i);
            for (j = 0; j != row.size(); ++j) {
                sum += row.get(j).getSecond();
            }
            if (sum == 0.0) continue;
            for (j = 0; j != row.size(); ++j) {
                double old_value = row.get(j).getSecond();
                row.get(j).setSecond(old_value / sum);
            }
        }
        adjMatrix.inv();
    }

    public static double[] pageRank(MatrixInterface preparedMatrix, int maxIteration) {
        double[] init = new double[preparedMatrix.getRowsCount()];
        for (int i = 0; i != init.length; ++i) {
            init[i] = 1.0;
        }
        return PageRank.pageRank(preparedMatrix, maxIteration, 0.85, init);
    }

    public static double[] pageRank(MatrixInterface preparedMatrix, int maxIteration, double d, double[] init) {
        double[] impact = new double[preparedMatrix.getRowsCount()];
        for (int i = 0; i != init.length; ++i) {
            impact[i] = 1.0;
        }
        return PageRank.pageRank(preparedMatrix, maxIteration, d, init, impact);
    }

    public static double[] pageRank(MatrixInterface preparedMatrix, int maxIteration, double d, double[] init, double[] impact) {
        double[] result = new double[init.length];
        for (int i = 0; i != init.length; ++i) {
            result[i] = init[i];
        }
        int iteration = 0;
        while (iteration < maxIteration) {
            ++iteration;
            result = preparedMatrix.multiply(result);
            for (int i = 0; i != result.length; ++i) {
                result[i] = impact[i] * (1.0 - d) + d * result[i];
            }
        }
        return result;
    }

    public static void main(String[] argv) {
        NormalMatrix matrix = new NormalMatrix(8, 8);
        matrix.set(1, 0, 1.0);
        matrix.set(0, 1, 1.0);
        matrix.set(1, 3, 1.0);
        matrix.set(1, 5, 1.0);
        matrix.set(1, 7, 8.0);
        matrix.set(2, 0, 2.0);
        matrix.set(2, 1, 2.0);
        matrix.set(2, 7, 8.0);
        matrix.set(3, 2, 1.0);
        matrix.set(3, 4, 2.0);
        matrix.set(3, 7, 8.0);
        matrix.set(4, 1, 2.0);
        matrix.set(4, 6, 2.0);
        matrix.set(4, 7, 8.0);
        matrix.set(5, 6, 2.0);
        matrix.set(5, 7, 8.0);
        matrix.set(6, 7, 8.0);
        matrix.set(7, 4, 2.0);
        matrix.set(7, 7, 8.0);
        PageRank.prepareMatrix(matrix);
        for (double d : PageRank.pageRank(matrix, 100)) {
            System.out.println(d);
        }
        System.out.println("\n\n");
        SparseMatrix adjMatrix = new SparseMatrix(4, 4);
        double[] init = new double[]{1.0, 1.0, 1.0, 1.0};
        double[] impact = new double[]{1.0, 1.0, 1.0, 1.0};
        adjMatrix.add(0, 1, 1.0);
        adjMatrix.add(2, 0, 1.0);
        adjMatrix.add(1, 2, 1.0);
        adjMatrix.add(2, 1, 1.0);
        adjMatrix.add(2, 3, 1.0);
        adjMatrix.add(3, 1, 1.0);
        PageRank.prepareMatrix(adjMatrix);
        double[] result = PageRank.pageRank(adjMatrix, 10000, 0.85, impact, init);
        for (int i = 0; i < result.length; ++i) {
            System.out.println(result[i]);
        }
        System.out.println("\n\n");
        matrix = new NormalMatrix(4, 4);
        matrix.set(0, 1, 1.0);
        matrix.set(2, 0, 1.0);
        matrix.set(1, 2, 1.0);
        matrix.set(2, 1, 1.0);
        matrix.set(2, 3, 1.0);
        matrix.set(3, 1, 1.0);
        PageRank.prepareMatrix(matrix);
        double[] result2 = PageRank.pageRank(matrix, 10000, 0.85, impact, init);
        for (int i = 0; i < result2.length; ++i) {
            System.out.println(result2[i]);
        }
    }
}

