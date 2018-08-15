//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.thunlp.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.thunlp.io.TextFileWriter;
import org.thunlp.misc.IntPair;
//import org.thunlp.tool.FolderReader;

public class Lexicon implements Serializable {
    private static final long serialVersionUID = 1L;
    protected static String COLON_REPLACER = "~CLN~";
    protected Hashtable<Integer, Lexicon.Word> idHash = new Hashtable(50000);
    protected Hashtable<String, Lexicon.Word> nameHash = new Hashtable(50000);
    protected boolean locked = false;
    protected long numDocs = 0L;
    public static String NUM_DOCS_STR = "";
    private Set<Integer> termSet = new HashSet(256);

    public Lexicon() {
    }

    public Lexicon(File f) {
        this.loadFromFile(f);
    }

    public void setLock(boolean locked) {
        this.locked = locked;
    }

    public boolean getLock() {
        return this.locked;
    }

    public Lexicon.Word getWord(int id) {
        return (Lexicon.Word)this.idHash.get(id);
    }

    public Lexicon.Word getWord(String name) {
        return (Lexicon.Word)this.nameHash.get(name);
    }

    public void addDocument(String[] doc) {
        this.termSet.clear();
        String[] arr$ = doc;
        int len$ = doc.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String token = arr$[i$];
            Lexicon.Word t = (Lexicon.Word)this.nameHash.get(token);
            if (t == null) {
                if (this.locked) {
                    continue;
                }

                t = new Lexicon.Word();
                t.name = token;
                t.id = this.nameHash.size();
                t.tf = 0;
                t.df = 0;
                this.nameHash.put(t.name, t);
                this.idHash.put(t.id, t);
            }

            ++t.tf;
            if (!this.termSet.contains(t.id)) {
                this.termSet.add(t.id);
                ++t.df;
            }
        }

        ++this.numDocs;
    }

    public Lexicon.Word[] convertDocument(String[] doc) {
        Lexicon.Word[] terms = new Lexicon.Word[doc.length];
        int n = 0;

        for(int i = 0; i < doc.length; ++i) {
            String token = doc[i];
            Lexicon.Word t = (Lexicon.Word)this.nameHash.get(token);
            if (t == null) {
                if (this.locked) {
                    continue;
                }

                t = new Lexicon.Word();
                t.name = token;
                t.tf = 1;
                t.df = 1;
                t.id = this.nameHash.size();
                this.nameHash.put(t.name, t);
                this.idHash.put(t.id, t);
            }

            terms[n++] = t;
        }

        if (n < terms.length) {
            Lexicon.Word[] finalterms = new Lexicon.Word[n];

            for(int i = 0; i < n; ++i) {
                finalterms[i] = terms[i];
            }

            terms = finalterms;
        }

        return terms;
    }

    public int getSize() {
        return this.idHash.size();
    }

    public long getNumDocs() {
        return this.numDocs;
    }

    public boolean saveToFile(File f) {
        try {
            FileOutputStream fos = new FileOutputStream(f);
            Enumeration<Lexicon.Word> e = this.nameHash.elements();
            String numDocsStr = this.numDocs + "\n";
            fos.write(numDocsStr.getBytes());

            while(e.hasMoreElements()) {
                Lexicon.Word t = (Lexicon.Word)e.nextElement();
                String termString = t.toString() + "\n";
                fos.write(termString.getBytes("utf8"));
            }

            fos.close();
            return true;
        } catch (FileNotFoundException var7) {
            return false;
        } catch (UnsupportedEncodingException var8) {
            return false;
        } catch (IOException var9) {
            return false;
        }
    }

    public boolean loadFromInputStream(InputStream input) {
        this.nameHash.clear();
        this.idHash.clear();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            this.numDocs = (long)Integer.parseInt(reader.readLine());

            String termString;
            while((termString = reader.readLine()) != null) {
                Lexicon.Word t = this.buildWord(termString);
                if (t != null) {
                    this.nameHash.put(t.name, t);
                    this.idHash.put(t.id, t);
                }
            }

            reader.close();
            return true;
        } catch (UnsupportedEncodingException var5) {
            return false;
        } catch (IOException var6) {
            return false;
        }
    }

    public boolean loadFromFile(File f) {
        FileInputStream fis;
        try {
            fis = new FileInputStream(f);
        } catch (FileNotFoundException var4) {
            return false;
        }

        return this.loadFromInputStream(fis);
    }

    protected Lexicon.Word buildWord(String termString) {
        Lexicon.Word t = null;
        String[] parts = termString.split(":");
        if (parts.length == 4) {
            t = new Lexicon.Word();
            t.id = Integer.parseInt(parts[0]);
            t.name = parts[1].replace(COLON_REPLACER, ":");
            t.tf = Integer.parseInt(parts[2]);
            t.df = Integer.parseInt(parts[3]);
        }

        return t;
    }

    public void mergeFrom(Lexicon another) {
        for(int i = 0; i < another.getSize(); ++i) {
            Lexicon.Word other = another.getWord(i);
            Lexicon.Word local = this.getWord(other.name);
            if (local == null) {
                local = new Lexicon.Word();
                local.name = other.name;
                local.df = other.df;
                local.tf = other.tf;
                local.id = this.idHash.size();
                this.idHash.put(local.id, local);
                this.nameHash.put(local.name, local);
            } else {
                local.df += other.df;
                local.tf += other.tf;
            }
        }

        this.numDocs += another.numDocs;
    }

    public Lexicon map(Map<Integer, Integer> translation) {
        Lexicon newlex = new Lexicon();
        Hashtable<Integer, Lexicon.Word> newIdHash = new Hashtable();
        Hashtable<String, Lexicon.Word> newNameHash = new Hashtable();
        Iterator i$ = translation.entrySet().iterator();

        while(i$.hasNext()) {
            Entry<Integer, Integer> e = (Entry)i$.next();
            Lexicon.Word w = (Lexicon.Word)this.idHash.get(e.getKey());
            Lexicon.Word nw = (Lexicon.Word)w.clone();
            nw.id = (Integer)e.getValue();
            newIdHash.put(nw.id, nw);
            newNameHash.put(nw.getName(), nw);
        }

        newlex.idHash = newIdHash;
        newlex.nameHash = newNameHash;
        newlex.numDocs = this.numDocs;
        return newlex;
    }

    public Lexicon removeLowCoverageWords(double coverage) {
        int minDf = (int)((double)this.numDocs * coverage);
        return this.removeLowDfWords(minDf);
    }

    public Lexicon removeLowDfWords(int minDf) {
        int id = 0;
        Hashtable<Integer, Integer> translation = new Hashtable();
        Iterator i$ = this.idHash.entrySet().iterator();

        while(i$.hasNext()) {
            Entry<Integer, Lexicon.Word> e = (Entry)i$.next();
            Lexicon.Word w = (Lexicon.Word)e.getValue();
            if (w.df >= minDf) {
                translation.put(w.id, id);
                ++id;
            }
        }

        return this.map(translation);
    }

    public Lexicon removeLowFreqWords(int minFreq) {
        int id = 0;
        Hashtable<Integer, Integer> translation = new Hashtable();
        Iterator i$ = this.idHash.entrySet().iterator();

        while(i$.hasNext()) {
            Entry<Integer, Lexicon.Word> e = (Entry)i$.next();
            Lexicon.Word w = (Lexicon.Word)e.getValue();
            if (w.tf >= minFreq) {
                translation.put(w.id, id);
                ++id;
            }
        }

        return this.map(translation);
    }

    public Lexicon removeStopwords(Set<String> stopwords) {
        int id = 0;
        Hashtable<Integer, Integer> translation = new Hashtable();
        Iterator i$ = this.idHash.entrySet().iterator();

        while(i$.hasNext()) {
            Entry<Integer, Lexicon.Word> e = (Entry)i$.next();
            Lexicon.Word w = (Lexicon.Word)e.getValue();
            if (!stopwords.contains(w.name)) {
                translation.put(w.id, id);
                ++id;
            }
        }

        return this.map(translation);
    }

    public Lexicon reorderWordsByFreq() {
        IntPair[] freq = new IntPair[this.idHash.size()];
        int n = 0;

        for(Iterator i$ = this.idHash.entrySet().iterator(); i$.hasNext(); ++n) {
            Entry<Integer, Lexicon.Word> e = (Entry)i$.next();
            freq[n] = new IntPair((Integer)e.getKey(), ((Lexicon.Word)e.getValue()).tf);
        }

        Arrays.sort(freq, new Comparator<IntPair>() {
            public int compare(IntPair o1, IntPair o2) {
                return o2.second - o1.second;
            }
        });
        Map<Integer, Integer> translation = new Hashtable();

        for(int i = 0; i < freq.length; ++i) {
            translation.put(freq[i].first, i);
        }

        return this.map(translation);
    }

    public String[] removeOov(String[] words) {
        List<String> output = new LinkedList();
        String[] arr$ = words;
        int len$ = words.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String w = arr$[i$];
            if (this.getWord(w) != null) {
                output.add(w);
            }
        }

        return (String[])output.toArray(new String[output.size()]);
    }

//    public static void MakeLexiconFromSeqFile(Path seqFile, File lexicon) throws IOException {
//        FolderReader reader = new FolderReader(seqFile);
//        long numDocs = 0L;
//        ArrayList<String> wordInfos = new ArrayList();
//        Text key = new Text();
//        Text value = new Text();
//        long wordId = 0L;
//
//        while(reader.next(key, value)) {
//            String valueStr = value.toString();
//            int splitPoint = valueStr.indexOf(32);
//            if (splitPoint > 0) {
//                long tf = Long.parseLong(valueStr.substring(0, splitPoint));
//                long df = Long.parseLong(valueStr.substring(splitPoint + 1));
//                if (key.toString().equals(NUM_DOCS_STR)) {
//                    numDocs = df;
//                } else {
//                    wordInfos.add(wordId + ":" + key.toString() + ":" + tf + ":" + df);
//                    ++wordId;
//                }
//            }
//        }
//
//        reader.close();
//        TextFileWriter writer = new TextFileWriter(lexicon.getAbsolutePath(), "UTF-8");
//        writer.writeLine(Long.toString(numDocs));
//        Iterator i$ = wordInfos.iterator();
//
//        while(i$.hasNext()) {
//            String wordInfo = (String)i$.next();
//            writer.writeLine(wordInfo);
//        }
//
//        writer.close();
//    }

    public static class Word implements Serializable {
        private static final long serialVersionUID = 1L;
        protected int id;
        protected String name;
        protected int tf;
        protected int df;

        protected Word() {
        }

        protected Word(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String toString() {
            return this.id + ":" + this.name.replace(":", Lexicon.COLON_REPLACER) + ":" + this.tf + ":" + this.df;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public int getFrequency() {
            return this.tf;
        }

        public int getDocumentFrequency() {
            return this.df;
        }

        public boolean equals(Object other) {
            if (!(other instanceof Lexicon.Word)) {
                return false;
            } else {
                Lexicon.Word ot = (Lexicon.Word)other;
                if (!ot.name.equals(this.name)) {
                    return false;
                } else {
                    return ot.id == this.id;
                }
            }
        }

        protected Object clone() {
            Lexicon.Word t = new Lexicon.Word();
            t.name = this.name;
            t.id = this.id;
            t.tf = this.tf;
            t.df = this.df;
            return t;
        }
    }

    public static class LexiconReducer implements Reducer<Text, Text, Text, Text> {
        Text outvalue = new Text();

        public LexiconReducer() {
        }

        public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter r) throws IOException {
            long sumTf = 0L;
            long sumDf = 0L;

            while(values.hasNext()) {
                String value = ((Text)values.next()).toString();
                int splitPoint = value.indexOf(32);
                if (splitPoint > 0) {
                    long tf = Long.parseLong(value.substring(0, splitPoint));
                    long df = Long.parseLong(value.substring(splitPoint + 1));
                    sumTf += tf;
                    sumDf += df;
                }
            }

            this.outvalue.set(sumTf + " " + sumDf);
            output.collect(key, this.outvalue);
        }

        public void configure(JobConf conf) {
        }

        public void close() throws IOException {
        }
    }
}
