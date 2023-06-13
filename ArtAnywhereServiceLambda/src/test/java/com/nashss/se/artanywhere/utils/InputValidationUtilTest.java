package com.nashss.se.artanywhere.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InputValidationUtilTest {

    @Test
    void isValidString_inputValid_returnsTrue() {
        assertTrue(InputValidationUtil.isValidString("artanywhere"));
    }
    @Test
    void isValidString_inputInValid_returnsFalse() {
        assertFalse(InputValidationUtil.isValidString("arta\\nywhere"));
    }
    @Test
    void isValidString_inputEmpty_returnsFalse() {
        assertFalse(InputValidationUtil.isValidString(""));
    }

    @Test
    void noDangerousCharacters_inputEmpty_returnsTrue() {
        assertTrue(InputValidationUtil.noDangerousCharacters(" "));
    }
    @Test
    void noDangerousCharacters_inputInValid_returnsFalse() {
        assertFalse(InputValidationUtil.noDangerousCharacters("arta\\nywhere"));
    }
    @Test
    void noDangerousCharacters_inputValid_returnsTrue() {
        assertTrue(InputValidationUtil.noDangerousCharacters("artanywhere"));
    }
}