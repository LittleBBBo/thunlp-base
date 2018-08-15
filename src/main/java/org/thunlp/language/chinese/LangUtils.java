/*
 * Decompiled with CFR 0_123.
 */
package org.thunlp.language.chinese;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.thunlp.language.chinese.ChineseLanguageConstants;

public class LangUtils {
    protected static Pattern allMarksPat;
    protected static Hashtable<Character, Character> t2s;
    protected static Hashtable<Character, Character> s2t;

    private LangUtils() {
    }

    public static String removeExtraSpaces(String text) {
        text = text.replace(ChineseLanguageConstants.SPACE[0], ChineseLanguageConstants.SPACE[1]);
        text = text.replaceAll("[ \t\u000b\f\u00a0\ue5f1]+", " ");
        text = text.replaceAll("(^ +)|( +$)", "");
        return text;
    }

    public static String removeEmptyLines(String text) {
        text = text.replaceAll("^[ " + ChineseLanguageConstants.SPACE[0] + "\t\u000b\f\u00a0\ue5f1\r\n]*\n", "");
        text = text.replaceAll("[\r\n][ " + ChineseLanguageConstants.SPACE[0] + "\t\u000b\f\u00a0\ue5f1\r\n]*\n", "\n");
        return text;
    }

    public static String removePunctuationMarks(String text) {
        return LangUtils.removeExtraSpaces(allMarksPat.matcher(text).replaceAll(" "));
    }

    public static String removePunctuationMarksExcept(String text, String exception) {
        String pat = "[" + ChineseLanguageConstants.ALL_MARKS[0].replaceAll(exception, "") + ChineseLanguageConstants.ALL_MARKS[1].replaceAll(exception, "").replace("\\", "\\\\").replace("]", "\\]").replace("[", "\\[").replace("-", "\\-") + "]";
        Pattern toRemove = Pattern.compile(pat);
        return toRemove.matcher(text).replaceAll(" ");
    }

    public static String mapFullWidthLetterToHalfWidth(String text) {
        char[] buf = new char[text.length()];
        text.getChars(0, text.length(), buf, 0);
        block54 : for (int i = 0; i < buf.length; ++i) {
            switch (buf[i]) {
                case '\uff21': {
                    buf[i] = 65;
                    continue block54;
                }
                case '\uff22': {
                    buf[i] = 66;
                }
                case '\uff23': {
                    buf[i] = 67;
                    continue block54;
                }
                case '\uff24': {
                    buf[i] = 68;
                    continue block54;
                }
                case '\uff25': {
                    buf[i] = 69;
                    continue block54;
                }
                case '\uff26': {
                    buf[i] = 70;
                    continue block54;
                }
                case '\uff27': {
                    buf[i] = 71;
                    continue block54;
                }
                case '\uff28': {
                    buf[i] = 72;
                    continue block54;
                }
                case '\uff29': {
                    buf[i] = 73;
                    continue block54;
                }
                case '\uff2a': {
                    buf[i] = 74;
                    continue block54;
                }
                case '\uff2b': {
                    buf[i] = 75;
                    continue block54;
                }
                case '\uff2c': {
                    buf[i] = 76;
                    continue block54;
                }
                case '\uff2d': {
                    buf[i] = 77;
                    continue block54;
                }
                case '\uff2e': {
                    buf[i] = 78;
                    continue block54;
                }
                case '\uff2f': {
                    buf[i] = 79;
                    continue block54;
                }
                case '\uff30': {
                    buf[i] = 80;
                    continue block54;
                }
                case '\uff31': {
                    buf[i] = 81;
                    continue block54;
                }
                case '\uff32': {
                    buf[i] = 82;
                    continue block54;
                }
                case '\uff33': {
                    buf[i] = 83;
                    continue block54;
                }
                case '\uff34': {
                    buf[i] = 84;
                    continue block54;
                }
                case '\uff35': {
                    buf[i] = 85;
                    continue block54;
                }
                case '\uff36': {
                    buf[i] = 86;
                    continue block54;
                }
                case '\uff37': {
                    buf[i] = 87;
                    continue block54;
                }
                case '\uff38': {
                    buf[i] = 88;
                    continue block54;
                }
                case '\uff39': {
                    buf[i] = 89;
                    continue block54;
                }
                case '\uff3a': {
                    buf[i] = 90;
                    continue block54;
                }
                case '\uff41': {
                    buf[i] = 97;
                    continue block54;
                }
                case '\uff42': {
                    buf[i] = 98;
                    continue block54;
                }
                case '\uff43': {
                    buf[i] = 99;
                    continue block54;
                }
                case '\uff44': {
                    buf[i] = 100;
                    continue block54;
                }
                case '\uff45': {
                    buf[i] = 101;
                    continue block54;
                }
                case '\uff46': {
                    buf[i] = 102;
                    continue block54;
                }
                case '\uff47': {
                    buf[i] = 103;
                    continue block54;
                }
                case '\uff48': {
                    buf[i] = 104;
                    continue block54;
                }
                case '\uff49': {
                    buf[i] = 105;
                    continue block54;
                }
                case '\uff4a': {
                    buf[i] = 106;
                    continue block54;
                }
                case '\uff4b': {
                    buf[i] = 107;
                    continue block54;
                }
                case '\uff4c': {
                    buf[i] = 108;
                    continue block54;
                }
                case '\uff4d': {
                    buf[i] = 109;
                    continue block54;
                }
                case '\uff4e': {
                    buf[i] = 110;
                    continue block54;
                }
                case '\uff4f': {
                    buf[i] = 111;
                    continue block54;
                }
                case '\uff50': {
                    buf[i] = 112;
                    continue block54;
                }
                case '\uff51': {
                    buf[i] = 113;
                    continue block54;
                }
                case '\uff52': {
                    buf[i] = 114;
                    continue block54;
                }
                case '\uff53': {
                    buf[i] = 115;
                    continue block54;
                }
                case '\uff54': {
                    buf[i] = 116;
                    continue block54;
                }
                case '\uff55': {
                    buf[i] = 117;
                    continue block54;
                }
                case '\uff56': {
                    buf[i] = 118;
                    continue block54;
                }
                case '\uff57': {
                    buf[i] = 119;
                    continue block54;
                }
                case '\uff58': {
                    buf[i] = 120;
                    continue block54;
                }
                case '\uff59': {
                    buf[i] = 121;
                    continue block54;
                }
                case '\uff5a': {
                    buf[i] = 122;
                    break;
                }
            }
        }
        text = new String(buf);
        return text;
    }

    public static String mapFullWidthNumberToHalfWidth(String text) {
        char[] buf = new char[text.length()];
        text.getChars(0, text.length(), buf, 0);
        block12 : for (int i = 0; i < buf.length; ++i) {
            switch (buf[i]) {
                case '\uff10': {
                    buf[i] = 48;
                    continue block12;
                }
                case '\uff11': {
                    buf[i] = 49;
                    continue block12;
                }
                case '\uff12': {
                    buf[i] = 50;
                    continue block12;
                }
                case '\uff13': {
                    buf[i] = 51;
                    continue block12;
                }
                case '\uff14': {
                    buf[i] = 52;
                    continue block12;
                }
                case '\uff15': {
                    buf[i] = 53;
                    continue block12;
                }
                case '\uff16': {
                    buf[i] = 54;
                    continue block12;
                }
                case '\uff17': {
                    buf[i] = 55;
                    continue block12;
                }
                case '\uff18': {
                    buf[i] = 56;
                    continue block12;
                }
                case '\uff19': {
                    buf[i] = 57;
                    break;
                }
            }
        }
        text = new String(buf);
        return text;
    }

    public static String mapChineseMarksToAnsi(String text) {
        char[] buf = text.toCharArray();
        block25 : for (int i = 0; i < buf.length; ++i) {
            switch (buf[i]) {
                case '\u201c': 
                case '\u201d': {
                    buf[i] = 34;
                    continue block25;
                }
                case '\u2018': 
                case '\u2019': {
                    buf[i] = 39;
                    continue block25;
                }
                case '\uff08': {
                    buf[i] = 40;
                    continue block25;
                }
                case '\uff09': {
                    buf[i] = 41;
                    continue block25;
                }
                case '\uff5e': {
                    buf[i] = 126;
                    continue block25;
                }
                case '\uff40': {
                    buf[i] = 96;
                    continue block25;
                }
                case '\uff01': {
                    buf[i] = 33;
                    continue block25;
                }
                case '\uff20': {
                    buf[i] = 64;
                    continue block25;
                }
                case '\uff03': {
                    buf[i] = 35;
                    continue block25;
                }
                case '\uffe5': {
                    buf[i] = 36;
                    continue block25;
                }
                case '\uff05': {
                    buf[i] = 37;
                    continue block25;
                }
                case '\uff06': {
                    buf[i] = 38;
                    continue block25;
                }
                case '\uff0a': {
                    buf[i] = 42;
                    continue block25;
                }
                case '\uff0b': {
                    buf[i] = 43;
                    continue block25;
                }
                case '\uff0d': {
                    buf[i] = 45;
                    continue block25;
                }
                case '\uff1d': {
                    buf[i] = 61;
                    continue block25;
                }
                case '\uff1b': {
                    buf[i] = 59;
                    continue block25;
                }
                case '\uff1a': {
                    buf[i] = 58;
                    continue block25;
                }
                case '\uff0c': {
                    buf[i] = 44;
                    continue block25;
                }
                case '\uff0f': {
                    buf[i] = 47;
                    continue block25;
                }
                case '\uff1f': {
                    buf[i] = 63;
                    continue block25;
                }
                case '\uff5c': {
                    buf[i] = 124;
                    continue block25;
                }
                case '\u3000': {
                    buf[i] = 32;
                    break;
                }
            }
        }
        text = new String(buf);
        return text;
    }

    public static String removeLineEnds(String text) {
        return text.replaceAll("[\r\n]+", " ").trim();
    }

    public static boolean isChinese(int codePoint) {
        return codePoint >= ChineseLanguageConstants.CHINESE_START && codePoint <= ChineseLanguageConstants.CHINESE_END;
    }

    public static String T2S(String text) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            Character replacement = t2s.get(Character.valueOf(chars[i]));
            if (replacement == null) continue;
            chars[i] = replacement.charValue();
        }
        return new String(chars);
    }

    public static String S2T(String text) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            Character replacement = s2t.get(Character.valueOf(chars[i]));
            if (replacement == null) continue;
            chars[i] = replacement.charValue();
        }
        return new String(chars);
    }

    static {
        String pat = "[" + ChineseLanguageConstants.ALL_MARKS[0] + ChineseLanguageConstants.ALL_MARKS[1].replace("\\", "\\\\").replace("]", "\\]").replace("[", "\\[").replace("-", "\\-") + "]";
        allMarksPat = Pattern.compile(pat);
        t2s = new Hashtable();
        s2t = new Hashtable();
        String schars = ChineseLanguageConstants.SIMPLIFIED_CHARS;
        String tchars = ChineseLanguageConstants.TRADITIONAL_CHARS;
        for (int i = 0; i < schars.length(); ++i) {
            t2s.put(Character.valueOf(tchars.charAt(i)), Character.valueOf(schars.charAt(i)));
            s2t.put(Character.valueOf(schars.charAt(i)), Character.valueOf(tchars.charAt(i)));
        }
    }
}

