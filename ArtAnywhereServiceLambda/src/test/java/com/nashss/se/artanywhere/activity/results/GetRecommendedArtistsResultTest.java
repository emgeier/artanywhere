package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.ArtistModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetRecommendedArtistsResultTest {



    @Test
    void builder_validInput_buildsResult() {
        List<ArtistModel> artistModelList = new ArrayList<>();
        GetRecommendedArtistsResult result = GetRecommendedArtistsResult.builder()
                .withArtists(artistModelList).build();
        assertEquals(artistModelList, result.getArtists());

    }
}