package com.cyrusinnovation.flatwiki;

import java.util.regex.*;

public class WikiPage {
    private static final Pattern WIKI_WORD_PATTERN = Pattern.compile(
            "(?<=\\W|^)" + // preceded by a non-word character or the start of the line
                    "[A-Z][a-z]+([A-Z][a-z]+)+" // two or more words smashed together
    );
    private final String name;
    private final String source;

    public WikiPage(String name, String source) {
        this.name = name;
        this.source = source;
    }

    public String getOutputFilename() {
        return name + ".html";
    }

    public String asHtml() {
        return String.format(
                "<h1>%s</h1>" +
                        "<div>%s</div>",
                name, wikifySource());
    }

    private String wikifySource() {
        StringBuffer result = new StringBuffer();
        Matcher matcher = WIKI_WORD_PATTERN.matcher(source);
        while (matcher.find()) {
            String pageName = matcher.group();
            String link = String.format("<a href=\"%s.html\">%s</a>", pageName, pageName);
            matcher.appendReplacement(result, link);
        }
        matcher.appendTail(result);
        return result.toString();
    }
}