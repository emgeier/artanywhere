package com.nashss.se.artanywhere.converters;

import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelConverterTest {
private ModelConverter modelConverter = new ModelConverter();

    @Test
    public void toExhibitionModel_validExhibition__returnsModel() {
    //GIVEN
    Exhibition testExhibition = new Exhibition();
    testExhibition.setExhibitionName("xName");
    testExhibition.setCityCountry("Paris");
    testExhibition.setDescription("great");
    List<String> artists = new ArrayList<>();
    artists.add("joe");
    testExhibition.setAddress("555 Jones St. Paris, TN");
    testExhibition.setArtists(artists);
    LocalDate date = LocalDate.now();
    testExhibition.setEndDate(date);
    testExhibition.setStartDate(date);
    testExhibition.setMovement(Exhibition.MOVEMENT.IMPRESSIONISM);
    List<Exhibition.MEDIUM> testMedia = new ArrayList<>();
    testMedia.add(Exhibition.MEDIUM.CERAMICS);
    testExhibition.setMedia(testMedia);
    String dateString = new DateConverter().convertToWords(date);
    //WHEN/THEN
    assertEquals(testExhibition.getCityCountry(), modelConverter.toExhibitionModel(testExhibition).getCityCountry() );
    assertEquals(testExhibition.getMovement(), modelConverter.toExhibitionModel(testExhibition).getMovement());
    assertTrue(testExhibition.getMedia().get(0).equals(modelConverter.toExhibitionModel(testExhibition).getMedia().get(0)));


    }

    public static class MediaListConverterTest {
        private MediaListConverter mediaListConverter = new MediaListConverter();
        @Test
        public void convert_listOfStrings_returnsMedia() {
            //GIVEN
            List<Exhibition.MEDIUM> testMedia = new ArrayList<>();
            testMedia.add(Exhibition.MEDIUM.CERAMICS);
            testMedia.add(Exhibition.MEDIUM.FILM);
            //WHEN
            List<String> result = mediaListConverter.convert(testMedia);
            //THEN
            assertEquals(result.get(0), "CERAMICS");
            assertEquals(result.get(1), "FILM");

        }
        @Test
        public void unconvert_listOfStrings_returnsMedia() {
            //GIVEN
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
}