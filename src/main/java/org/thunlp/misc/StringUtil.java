//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.thunlp.misc;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class StringUtil {
    public StringUtil() {
    }

    public static String join(Collection strings, String delimiter) {
        return join(strings, delimiter, 2147483647);
    }

    public static String join(Object[] strings, String delimiter) {
        return join(strings, delimiter, 2147483647);
    }

    public static String join(String[] strings, String delimiter) {
        return join((Object[])strings, delimiter, 2147483647);
    }

    public static String join(Collection strings, String delimiter, int max) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        int n = 0;
        Iterator i$ = strings.iterator();

        while(i$.hasNext()) {
            Object s = i$.next();
            if (!first) {
                sb.append(delimiter);
            }

            sb.append(s.toString());
            first = false;
            ++n;
            if (n >= max) {
                break;
            }
        }

        return sb.toString();
    }

    public static String join(Object[] objects, String delimiter, int max) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        int n = 0;
        Object[] arr$ = objects;
        int len$ = objects.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Object o = arr$[i$];
            if (!first) {
                sb.append(delimiter);
            }

            sb.append(o.toString());
            first = false;
            ++n;
            if (n >= max) {
                break;
            }
        }

        return sb.toString();
    }

    public static void split(String text, char delimiter, List<String> tokens) {
        int start = 0;

        for(boolean var4 = false; start < text.length() && text.charAt(start) == delimiter; ++start) {
            ;
        }

        for(int end = start; start < text.length(); start = end) {
            while(end < text.length() && text.charAt(end) != delimiter) {
                ++end;
            }

            tokens.add(text.substring(start, end));

            while(end < text.length() && text.charAt(end) == delimiter) {
                ++end;
            }
        }

    }
}
