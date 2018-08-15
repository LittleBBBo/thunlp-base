//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.thunlp.language.chinese;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class Stopwords {
    private static Pattern stopwordRE = null;
    private static Pattern stopwordsRemovalRE = null;
    private static Set<String> stopwords = null;
    private static Logger LOG = Logger.getAnonymousLogger();

    public Stopwords() {
    }

    public static boolean isStopword(String word) {
        return stopwords.contains(word);
    }

    public static String removeAllStopwords(String content) {
        return stopwordsRemovalRE.matcher(content).replaceAll(" ");
    }

    private static String loadStopwordsAsPattern(Set<String> wordset) {
        InputStream input = null;
        if (System.getProperties().containsKey("wordsegment.stopwords.cn.file")) {
            try {
                input = new FileInputStream(System.getProperty("wordsegment.stopwords.cn.file"));
            } catch (FileNotFoundException var6) {
                var6.printStackTrace();
                input = null;
            }
        }

        if (input == null) {
            LOG.warning("Property 'wordsegment.stopwords.cn.file' is not valid, will use default word list instead.");
            input = Stopwords.class.getClassLoader().getResourceAsStream("org/thunlp/language/chinese/stopwords.cn.txt");
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;

        try {
            BufferedReader reader;
            String line;
            for(reader = new BufferedReader(new InputStreamReader((InputStream)input, "UTF-8")); (line = reader.readLine()) != null; first = false) {
                wordset.add(line);
                if (!first) {
                    sb.append("|");
                }

                sb.append(line.trim());
            }

            reader.close();
        } catch (UnsupportedEncodingException var7) {
            var7.printStackTrace();
        } catch (IOException var8) {
            LOG.warning("Cannot load stopwords, ignore stopwords.");
        }

        return sb.toString();
    }

    static {
        stopwords = new HashSet();
        String patternStr = loadStopwordsAsPattern(stopwords);
        stopwordRE = Pattern.compile(patternStr, 10);
        stopwordsRemovalRE = Pattern.compile(" " + patternStr + " ", 10);
    }
}
