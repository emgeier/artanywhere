package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.ArtistModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetArtistResultTest {


    @Test
    void builder_withArtists_buildsResult() {
        List<ArtistModel> artistModelList = new ArrayList<>();
        GetArtistResult result = GetArtistResult.builder()
                .withArtists(artistModelList).build();

        assertEquals(artistModelList, result.getArtist());
    }
}