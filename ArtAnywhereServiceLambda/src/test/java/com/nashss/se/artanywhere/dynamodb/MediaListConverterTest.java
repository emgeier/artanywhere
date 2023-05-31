package com.nashss.se.artanywhere.dynamodb;

import com.nashss.se.artanywhere.converters.MediaListConverter;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MediaListConverterTest {
    private MediaListConverter mediaListConverter = new MediaListConverter();
    @Test
    public void convert_listOfStrings_returnsMedia() {

        List<Exhibition.MEDIUM> testMedia = new ArrayList<>();
        testMedia.add(Exhibition.MEDIUM.CERAMICS);
        testMedia.add(Exhibition.MEDIUM.FILM);

        List<String> result = mediaListConverter.convert(testMedia);
        assertEquals(result.get(0), "CERAMICS");
        assertEquals(result.get(1), "FILM");

    }
    @Test
    public void unconvert_listOfStrings_returnsMedia() {
        //GIVE
        List<Exhibition.MEDIUM> testMedia = new ArrayList<>();
        testMedia.add(Exhibition.MEDIUM.CERAMICS);
        testMedia.add(Exhibition.MEDIUM.FILM);
        List<String> previousResult = mediaListConverter.convert(testMedia);
        //WHEN
        List<Exhibition.MEDIUM> media = mediaListConverter.unconvert(previousResult);
        //THEN
        assertEquals(media.get(0), Exhibition.MEDIUM.CERAMICS);
    }
}
