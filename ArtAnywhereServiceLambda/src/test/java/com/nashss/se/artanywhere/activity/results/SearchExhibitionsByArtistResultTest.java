package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.models.ExhibitionModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchExhibitionsByArtistResultTest {
    List<ExhibitionModel> exhibitionModelList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        List<String> artists = new ArrayList<>();
        artists.add("DaVinci");
        List<String> art = new ArrayList<>();
        String monaLisa = "Mona Lisa";
        art.add(monaLisa);
        List<Exhibition.MEDIUM> media = new ArrayList<>();
        media.add(Exhibition.MEDIUM.PAINTING);
        List<String> tags = new ArrayList<>();
        tags.add("Florentine");
        ExhibitionModel exhibit1 = new ExhibitionModel("Florence, Italy", "exhibitionName", "institution", "String startDate", "String endDate", "String address",
                tags, media, Exhibition.MOVEMENT.RENAISSANCE, artists, art, "description", "imageUrl", "imageAttribution");
        exhibitionModelList.add(exhibit1);
    }
    @Test
    void builder_validInput_buildsResult() {
        SearchExhibitionsByArtistResult result = SearchExhibitionsByArtistResult.builder().withExhibitions(exhibitionModelList).build();
        assertEquals(exhibitionModelList, result.getExhibitions());
    }
    @Test
    void toString_validResult_returnsResultStringWithAttribute() {
        SearchExhibitionsByArtistResult result = SearchExhibitionsByArtistResult.builder().withExhibitions(exhibitionModelList).build();

        assertTrue(result.toString().contains("exhibitions"));

    }

}