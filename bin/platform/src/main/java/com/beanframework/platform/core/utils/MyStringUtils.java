package com.beanframework.platform.core.utils;

import java.util.regex.Pattern;

import com.beanframework.platform.core.base.Base;

public class MyStringUtils extends Base {
    public static final Pattern HTML_PATTERN = Pattern.compile("<[^>]+>");

    public static String removeHtmlTag(String string) {
        if (notEmpty(string)) {
            return HTML_PATTERN.matcher(string).replaceAll(BLANK);
        }
        return string;
    }
}
