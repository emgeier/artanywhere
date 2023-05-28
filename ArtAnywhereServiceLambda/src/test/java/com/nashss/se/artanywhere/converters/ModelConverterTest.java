package com.nashss.se.artanywhere.converters;

import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.models.ExhibitionModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelConverterTest {
private ModelConverter modelConverter = new ModelConverter();

@Test
    public void toExhibitionModel_validExhibition__returnsModel() {
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
    assertEquals(testExhibition.getCityCountry(), modelConverter.toExhibitionModel(testExhibition).getCityCountry() );
    assertEquals(testExhibition.getEndDate(), modelConverter.toExhibitionModel(testExhibition).getEndDate() );
    assertEquals(testExhibition.getMovement(), modelConverter.toExhibitionModel(testExhibition).getMovement() );
    assertTrue(testExhibition.getMedia().get(0).equals(modelConverter.toExhibitionModel(testExhibition).getMedia().get(0)));
    System.out.println(modelConverter.toExhibitionModel(testExhibition).getEndDate());

    }
}