/*
 * Decompiled with CFR 0_123.
 */
package org.thunlp.misc;

import java.util.Comparator;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class WeightString
implements Comparable<WeightString> {
    public String text = null;
    public double weight = 0.0;
    public static Comparator<WeightString> COMPARATOR = new Comparator<WeightString>(){

        @Override
        public int compare(WeightString o1, WeightString o2) {
            return Double.compare(o1.weight, o2.weight);
        }
    };
    public static Comparator<WeightString> REVERSE_COMPARATOR = new Comparator<WeightString>(){

        @Override
        public int compare(WeightString o1, WeightString o2) {
            return Double.compare(o2.weight, o1.weight);
        }
    };

    public WeightString(String text, double weight) {
        this.text = text;
        this.weight = weight;
    }

    @Override
    public int compareTo(WeightString o) {
        if (o instanceof WeightString) {
            double w = o.weight;
            return Double.compare(this.weight, w);
        }
        return 0;
    }

    public String toString() {
        return this.text + ":" + this.weight;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

}

