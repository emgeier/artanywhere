package com.nashss.se.artanywhere.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class InputValidationUtil {
    private static final Pattern INVALID_CHARACTER_PATTERN = Pattern.compile("[\"'\\\\]");

    public static boolean isValidString(String stringToValidate) {
        if (StringUtils.isBlank(stringToValidate)) {
            return false;
        } else {
            return !INVALID_CHARACTER_PATTERN.matcher(stringToValidate).find();
        }
    }
    public static boolean noDangerousCharacters(String stringToValidate) {
        if(StringUtils.isBlank(stringToValidate)) {
            return true;
        } else {
            return !INVALID_CHARACTER_PATTERN.matcher(stringToValidate).find();
        }
    }
}
