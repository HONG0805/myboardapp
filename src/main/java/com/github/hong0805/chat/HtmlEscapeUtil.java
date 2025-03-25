package com.github.hong0805.chat;

public class HtmlEscapeUtil {
    public static String escapeHtml(String str) {
        if (str == null) return null;
        return str.replace("&", "&amp;")
                 .replace("<", "&lt;")
                 .replace(">", "&gt;")
                 .replace("\"", "&quot;")
                 .replace("'", "&#039;");
    }
}