package com.example.springbootjpa.golbal;

import java.util.regex.Pattern;

public class RegexPattern {

    public static final Pattern STRING_REGEX_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9]+$");
}
