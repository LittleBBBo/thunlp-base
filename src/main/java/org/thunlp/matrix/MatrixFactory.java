/*
 * Decompiled with CFR 0_123.
 */
package org.thunlp.matrix;

import org.thunlp.matrix.MatrixInterface;
import org.thunlp.matrix.NormalMatrix;
import org.thunlp.matrix.SparseMatrix;

public class MatrixFactory {
    public static final String NORMAL_MATRIX = "normal";
    public static final String SPARSE_MATRIX = "sparse";

    public static MatrixInterface getMatrix(int rows, int cols, String matrixName) {
        if (matrixName.equals("normal")) {
            return new NormalMatrix(rows, cols);
        }
        if (matrixName.equals("sparse")) {
            return new SparseMatrix(rows, cols);
        }
        return null;
    }
}

