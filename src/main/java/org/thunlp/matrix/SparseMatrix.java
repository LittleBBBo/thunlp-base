/*
 * Decompiled with CFR 0_123.
 */
package org.thunlp.matrix;

import java.util.ArrayList;
import org.thunlp.matrix.MatrixInterface;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SparseMatrix
implements MatrixInterface {
    private ArrayList<Pair>[] matrix;
    int rows;
    int cols;

    public SparseMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new ArrayList[rows];
        for (int i = 0; i < rows; ++i) {
            this.matrix[i] = new ArrayList();
        }
    }

    @Override
    public double get(int row, int col) {
        ArrayList<Pair> rowList = this.matrix[row];
        for (Pair pair : rowList) {
            if (pair.first != col) continue;
            return pair.second;
        }
        return 0.0;
    }

    @Override
    public int getColsCount() {
        return this.cols;
    }

    @Override
    public int getRowsCount() {
        return this.rows;
    }

    public ArrayList<Pair> getRow(int row) {
        return this.matrix[row];
    }

    @Override
    public void inv() {
        int i;
        ArrayList[] newMatrix = new ArrayList[this.cols];
        for (i = 0; i < this.cols; ++i) {
            newMatrix[i] = new ArrayList();
        }
        for (i = 0; i < this.rows; ++i) {
            ArrayList<Pair> row = this.matrix[i];
            for (int j = 0; j != row.size(); ++j) {
                int col = row.get(j).getFirst();
                double val = row.get(j).getSecond();
                newMatrix[col].add(new Pair(i, val));
            }
        }
        this.matrix = newMatrix;
    }

    @Override
    public double[] multiply(double[] vector) {
        double[] result = new double[vector.length];
        for (int i = 0; i != this.matrix.length; ++i) {
            double sum = 0.0;
            ArrayList<Pair> row = this.matrix[i];
            for (int j = 0; j != row.size(); ++j) {
                int col = row.get(j).getFirst();
                double val = row.get(j).getSecond();
                sum += val * vector[col];
            }
            result[i] = sum;
        }
        return result;
    }

    @Override
    public void set(int row, int col, double value) {
        ArrayList<Pair> rowList = this.matrix[row];
        for (Pair pair : rowList) {
            if (pair.first != col) continue;
            pair.second = value;
            return;
        }
        this.add(row, col, value);
    }

    @Override
    public void add(int row, int col, double value) {
        this.matrix[row].add(new Pair(col, value));
    }

    public void inc(int row, int col, double value) {
        ArrayList<Pair> rowList = this.matrix[row];
        for (Pair pair : rowList) {
            if (pair.first != col) continue;
            Pair.access$118(pair, value);
            return;
        }
        this.add(row, col, value);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("" + this.rows + "\t" + this.cols + "\n");
        for (int i = 0; i != this.matrix.length; ++i) {
            for (Pair pair : this.matrix[i]) {
                sb.append("" + i + "\t" + pair.first + "\t" + pair.second + "\n");
            }
        }
        return sb.toString();
    }

    public static class Pair {
        private final int first;
        private double second;

        public Pair(int f, double s) {
            this.first = f;
            this.second = s;
        }

        public int getFirst() {
            return this.first;
        }

        public double getSecond() {
            return this.second;
        }

        public void setSecond(double new_second) {
            this.second = new_second;
        }

        public boolean equals(Object oth) {
            Pair other = (Pair)this.getClass().cast(oth);
            if (other.getFirst() == this.first && other.getSecond() == this.second) {
                return true;
            }
            return false;
        }

        static /* synthetic */ double access$118(Pair x0, double x1) {
            return x0.second += x1;
        }
    }

}

