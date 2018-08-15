/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  org.apache.hadoop.conf.Configuration
 *  org.apache.hadoop.fs.FSDataOutputStream
 *  org.apache.hadoop.fs.FileSystem
 *  org.apache.hadoop.fs.Path
 *  org.apache.hadoop.mapred.JobConf
 */
package org.thunlp.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;

public class TextFileWriter {
    BufferedWriter osw;

    public TextFileWriter(String filename) throws IOException {
        this(new File(filename), "UTF-8", false);
    }

    public TextFileWriter(String filename, boolean append) throws IOException {
        this(new File(filename), "UTF-8", append);
    }

    public TextFileWriter(File file) throws IOException {
        this(file, "UTF-8", false);
    }

    public TextFileWriter(File file, String charset) throws IOException {
        this(file, charset, false);
    }

    public TextFileWriter(String filename, String charset) throws IOException {
        this(new File(filename), charset, false);
    }

    public TextFileWriter(String filename, String charset, boolean append) throws IOException {
        this(new File(filename), charset, append);
    }

    public TextFileWriter(File file, String charset, boolean append) throws IOException {
        this.osw = this.constructWriter(file, charset, append);
    }

    public TextFileWriter(Path p) throws IOException {
        this(p, "UTF-8", false);
    }

    public TextFileWriter(Path p, String charset) throws IOException {
        this(p, charset, false);
    }

    public TextFileWriter(Path p, String charset, boolean append) throws IOException {
        this.osw = new BufferedWriter(new OutputStreamWriter((OutputStream)FileSystem.get((Configuration)new JobConf()).create(p, !append), charset));
    }

    protected BufferedWriter constructWriter(File file, String charset, boolean append) throws IOException {
        return new BufferedWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(file, append), charset));
    }

    public void write(String str) throws IOException {
        this.osw.write(str);
    }

    public void writeLine(String str) throws IOException {
        this.osw.write(str);
        this.osw.write(10);
    }

    public void flush() throws IOException {
        this.osw.flush();
    }

    public void close() throws IOException {
        this.osw.close();
    }

    public void append(CharSequence cs) throws IOException {
        this.osw.append(cs);
    }

    public static void writeToFile(String content, File file, String encoding) throws IOException {
        TextFileWriter w = new TextFileWriter(file, encoding, false);
        w.write(content);
        w.close();
    }
}

