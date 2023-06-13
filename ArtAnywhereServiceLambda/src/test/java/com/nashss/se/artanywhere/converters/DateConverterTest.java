package com.nashss.se.artanywhere.converters;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateConverterTest {

    private DateConverter converter = new DateConverter();
    String testString = "2023-06-12";
    String testString2 = "06-12-2023";
    LocalDate date = LocalDate.of(2023, 06, 12);


    @Test
    void unconvert_givenString_returnsLocalDate() {

         assertTrue(converter.unconvert(testString).equals(date));
    }

    @Test
    void convert_givenLocalDate_returnsString() {
        assertEquals(converter.convert(date),testString);

    }

    @Test
    void convertToWords_givenLocalDate_returnsDateInWords() {
        assertEquals("June 12, 2023", converter.convertToWords(date));
    }

    @Test
    void convertFromWords_givenDateInWords_returnsLocalDate() {
        assertEquals(date, converter.convertFromWords("June 12, 2023"));
    }

    @Test
    void convertFromNumberString() {
        assertEquals(date, converter.convertFromNumberString(testString2));
    }

    @Test
    void convertToJson_givenLocalDate_returnsJsonDate() {
        assertEquals("{\"year\":2023,\"month\":6,\"day\":12}", converter.convertToJson(date));
    }

    @Test
    void convertFromJson_givenJsonDate_returnsLocalDate() {
        assertEquals(date, converter.convertFromJson("{\"year\":2023,\"month\":6,\"day\":12}"));

    }
}