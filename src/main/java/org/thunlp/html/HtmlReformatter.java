//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.thunlp.html;

import java.util.regex.Pattern;
import org.htmlparser.util.Translate;

public class HtmlReformatter {
    protected static Pattern commentBlockRE = Pattern.compile("<!--.*?-->", 40);
    protected static Pattern scriptBlockRE = Pattern.compile("<script.*?</script>", 42);
    protected static Pattern styleBlockRE = Pattern.compile("<style.*?</style>", 42);
    protected static Pattern htmlTagRE = Pattern.compile("<[^<>]+>", 8);

    public HtmlReformatter() {
    }

    public static String getPlainText(String htmlPage) {
        String content = commentBlockRE.matcher(htmlPage).replaceAll("");
        content = styleBlockRE.matcher(content).replaceAll("");
        content = scriptBlockRE.matcher(content).replaceAll("");
        content = htmlTagRE.matcher(content).replaceAll("");
        content = Translate.decode(content);
        content = content.replaceAll("nbsp", " ");
        return content;
    }

    public static String reformat(String htmlPage) {
        return htmlPage;
    }
}
