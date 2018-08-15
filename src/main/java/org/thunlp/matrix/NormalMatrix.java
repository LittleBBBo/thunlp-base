/*
 * Decompiled with CFR 0_123.
 */
package org.thunlp.matrix;

import org.thunlp.matrix.MatrixInterface;

public class NormalMatrix
implements MatrixInterface {
    private double[][] matrix;
    int rows;
    int cols;

    public NormalMatrix(int rows, int cols) {
        this.matrix = new double[rows][];
        for (int i = 0; i != rows; ++i) {
            this.matrix[i] = new double[cols];
        }
        this.rows = rows;
        this.cols = cols;
    }

    public double get(int row, int col) {
        return this.matrix[row][col];
    }

    public void set(int row, int col, double value) {
        this.matrix[row][col] = value;
    }

    public void add(int row, int col, double value) {
        double[] arrd = this.matrix[row];
        int n = col;
        arrd[n] = arrd[n] + value;
    }

    public int getRowsCount() {
        return this.rows;
    }

    public int getColsCount() {
        return this.cols;
    }

    public void inv() {
        if (this.rows == this.cols) {
            for (int i = 0; i != this.rows; ++i) {
                for (int j = i + 1; j != this.cols; ++j) {
                    double tmp = this.matrix[i][j];
                    this.matrix[i][j] = this.matrix[j][i];
                    this.matrix[j][i] = tmp;
                }
            }
        } else {
            double[][] newMatrix = new double[this.cols][];
            for (int i = 0; i != this.cols; ++i) {
                newMatrix[i] = new double[this.rows];
                for (int j = 0; j != this.rows; ++j) {
                    newMatrix[i][j] = this.matrix[j][i];
                }
            }
            this.matrix = newMatrix;
        }
    }

    public double[] multiply(double[] vector) {
        double[] result = new double[vector.length];
        for (int i = 0; i != this.matrix.length; ++i) {
            double sum = 0.0;
            for (int j = 0; j != this.matrix[i].length; ++j) {
                sum += this.matrix[i][j] * vector[j];
            }
            result[i] = sum;
        }
        return result;
    }

    public Object clone() {
        NormalMatrix result = new NormalMatrix(this.rows, this.cols);
        for (int i = 0; i != this.rows; ++i) {
            for (int j = 0; j != this.cols; ++j) {
                result.matrix[i][j] = this.matrix[i][j];
            }
        }
        return result;
    }

    public String toString() {
        if (this.rows == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i != this.rows; ++i) {
            for (int j = 0; j < this.cols - 1; ++j) {
                sb.append("" + this.matrix[i][j] + "\t");
            }
            sb.append("" + this.matrix[i][this.cols - 1] + "\n");
        }
        return sb.toString();
    }
}

