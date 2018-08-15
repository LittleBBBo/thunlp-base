//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.thunlp.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.JobConf;

public class TextFileReader {
    BufferedReader br;

    public String readAll() throws IOException {
        int bufsize = 4096;
        char[] buffer = new char[bufsize];
        int fill = 0;

        while(true) {
            do {
                int read = this.br.read(buffer, fill, buffer.length - fill);
                if (read == -1) {
                    return new String(buffer, 0, fill);
                }

                fill += read;
            } while(fill < buffer.length);

            char[] newbuffer = new char[bufsize + buffer.length];

            for(int i = 0; i < buffer.length; ++i) {
                newbuffer[i] = buffer[i];
            }

//            char[] buffer = null;
            buffer = newbuffer;
        }
    }

    public static String readAll(String filename) throws IOException {
        return readAll(filename, "UTF-8");
    }

    public static String readAll(Path p) throws IOException {
        TextFileReader reader = new TextFileReader(p, "UTF-8");
        String result = reader.readAll();
        reader.close();
        return result;
    }

    public static String readAll(String filename, String encode) throws IOException {
        TextFileReader reader = new TextFileReader(filename, encode);
        String result = reader.readAll();
        reader.close();
        return result;
    }

    public String readLine() throws IOException {
        return this.br.readLine();
    }

    public TextFileReader(String filename) throws IOException {
        this(new File(filename), "UTF-8");
    }

    public TextFileReader(File file) throws IOException {
        this(file, "UTF-8");
    }

    public TextFileReader(String filename, String encode) throws IOException {
        this(new File(filename), encode);
    }

    public TextFileReader(File file, String encode) throws IOException {
        this.br = this.constructReader(file, encode);
    }

    public TextFileReader(Path p, String encode) throws IOException {
        this.br = new BufferedReader(new InputStreamReader(FileSystem.get(new JobConf()).open(p), encode));
    }

    public TextFileReader(Path p) throws IOException {
        this(p, "UTF-8");
    }

    protected BufferedReader constructReader(File file, String encode) throws IOException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), encode));
    }

    public void close() throws IOException {
        this.br.close();
    }
}
