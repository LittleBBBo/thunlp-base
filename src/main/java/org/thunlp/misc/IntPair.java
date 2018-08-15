/*
 * Decompiled with CFR 0_123.
 */
package org.thunlp.misc;

public class IntPair {
    public int first;
    public int second;

    public IntPair() {
        this.first = 0;
        this.second = 0;
    }

    public IntPair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public boolean equals(Object o) {
        if (!(o instanceof IntPair)) {
            return false;
        }
        IntPair ip = (IntPair)o;
        if (ip.first == this.first && ip.second == this.second) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.first + this.second;
    }

    public String toString() {
        return "" + this.first + ":" + this.second;
    }

    public boolean fromString(String str) {
        String[] cols = str.split(" ");
        if (cols.length != 2) {
            return false;
        }
        this.first = Integer.parseInt(cols[0]);
        this.second = Integer.parseInt(cols[1]);
        return true;
    }
}

