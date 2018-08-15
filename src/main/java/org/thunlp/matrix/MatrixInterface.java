/*
 * Decompiled with CFR 0_123.
 */
package org.thunlp.matrix;

public interface MatrixInterface {
    public double get(int var1, int var2);

    public void set(int var1, int var2, double var3);

    public void add(int var1, int var2, double var3);

    public int getRowsCount();

    public int getColsCount();

    public void inv();

    public double[] multiply(double[] var1);
}

