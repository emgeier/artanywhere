package com.nashss.se.artanywhere.models;

import com.nashss.se.artanywhere.converters.DateConverter;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExhibitionModelTest {
    ExhibitionModel model;
    List<String> tags;
    List<String> art;
    List<String> artists;
    LocalDate date = LocalDate.now();

    LocalDate dateLater = LocalDate.of(2024, 06, 01);

    ExhibitionModel.Builder builder = new ExhibitionModel.Builder();

    @BeforeEach
    void setUp() {
        tags = new ArrayList<>();
        tags.add("Russian");
        List<Exhibition.MEDIUM> media = new ArrayList<>();
        media.add(Exhibition.MEDIUM.CERAMICS);
        artists = new ArrayList<>();
        artists.add("Kandinsky");
        art = new ArrayList<>();
        art.add("Mona Lisa");
        this.model = new ExhibitionModel(
                "Dublin, Ireland", "exName", "MOMA", "20220101", "20231201",
                "123 First Street", tags, media, Exhibition.MOVEMENT.IMPRESSIONISM,  artists, art,
                "simply the best", "url", "Erin Geier");
    }

    @Test
    void getCityCountry() {
        assertEquals("Dublin, Ireland", model.getCityCountry());
    }

    @Test
    void getExhibitionName() {
        assertEquals("exName", model.getExhibitionName());
    }

    @Test
    void getInstitution() {
        assertEquals("MOMA", model.getInstitution());
    }

    @Test
    void getStartDate() {
        assertEquals("20220101", model.getStartDate());
    }

    @Test
    void getEndDate() {
        assertEquals("20231201", model.getEndDate());
    }

    @Test
    void getAddress() {
        assertEquals("123 First Street", model.getAddress());
    }

    @Test
    void getTags() {
        assertEquals(tags, model.getTags());
    }

    @Test
    void getMedia() {
        assertEquals(Exhibition.MEDIUM.CERAMICS, model.getMedia().get(0));
    }

    @Test
    void getMovement() {
        assertEquals(Exhibition.MOVEMENT.IMPRESSIONISM, model.getMovement());
    }

    @Test
    void getArtists() {
        assertEquals("Kandinsky", model.getArtists().get(0));
    }

    @Test
    void getArt() {
        assertEquals("Mona Lisa", model.getArt().get(0));
    }

    @Test
    void getDescription() {
        assertEquals("simply the best", model.getDescription());
    }

    @Test
    void getImageUrl() {
        assertEquals("url", model.getImageUrl());
    }

    @Test
    void getImageAttribution() {
        assertEquals("Erin Geier", model.getImageAttribution());
    }
    @Test
    void builder_withStartDateinputLocalDate_returnsModelwithExpectedDateJsonString() {

        ExhibitionModel test = builder.withStartDate(date).build();
        assertEquals(new DateConverter().convertToJson(date), test.getStartDate());
    }
    @Test
    void builder_withEndDateinputLocalDate_returnsModelwithExpectedDateJsonString() {
        ExhibitionModel test = builder.withEndDate(dateLater).build();
        assertEquals(new DateConverter().convertToJson(dateLater), test.getEndDate());
    }
    @Test
    void builder_withMediainputListMedia_returnsModelwithExpectedAttribute() {
        ExhibitionModel test = builder.withMedia(model.getMedia()).build();
        assertEquals(model.getMedia(), test.getMedia());
    }
    @Test
    void builder_withTagsinputList_returnsModelwithExpectedAttribute() {
        ExhibitionModel test = builder.withTags(tags).build();
        assertEquals(tags, test.getTags());
    }
    @Test
    void builder_withArtinputList_returnsModelwithExpectedAttribute() {
        ExhibitionModel test = builder.withArt(art).build();
        assertEquals(art, test.getArt());
    }
    @Test
    void builder_withArtists_returnsModelwithExpectedAttribute() {
        ExhibitionModel test = builder.withArtists(artists).build();
        assertEquals(artists, test.getArtists());
    }
    @Test
    void builder_withMovement_returnsModelwithExpectedAttribute() {
        ExhibitionModel test = builder.withMovement(Exhibition.MOVEMENT.IMPRESSIONISM).build();
        assertEquals(Exhibition.MOVEMENT.IMPRESSIONISM, test.getMovement());
    }
    @Test
    void builder_withDescription_returnsModelwithExpectedAttribute() {
        ExhibitionModel test = builder.withDescription("great").build();
        assertEquals("great", test.getDescription());
    }
    @Test
    void builder_withCityCountry_returnsModelwithExpectedAttribute() {
        ExhibitionModel test = builder.withCityCountry("London").build();
        assertEquals("London", test.getCityCountry());
    }
    @Test
    void builder_withImageUrl_returnsModelwithExpectedAttribute() {
        ExhibitionModel test = builder.withImageUrl("url").build();
        assertEquals("url", test.getImageUrl());
    }
    @Test
    void builder_withImageAttribution_returnsModelwithExpectedAttribute() {
        ExhibitionModel test = builder.withImageAttribution("Man Ray").build();
        assertEquals("Man Ray", test.getImageAttribution());
    }
    @Test
    void builder_withAddress_returnsModelwithExpectedAttribute() {
        ExhibitionModel test = builder.withAddress("ABC Street").build();
        assertEquals("ABC Street", test.getAddress());
    }
}