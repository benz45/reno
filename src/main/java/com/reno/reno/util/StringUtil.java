package com.reno.reno.util;

public class StringUtil {

    private StringUtil() {
    }

    public static String replaceSpecialString(String text) {
        if (Util.isNullOrEmpty(text)) {
            return text
                    .replaceAll("[^\u0E01-\u0E7Fa-zA-Z\u0020\u0031-\u0039\u002D\u0028\u0029\\[\\]\u005F']",
                            "")
                    .replace("'", "");
        }
        return text;
    }
}
