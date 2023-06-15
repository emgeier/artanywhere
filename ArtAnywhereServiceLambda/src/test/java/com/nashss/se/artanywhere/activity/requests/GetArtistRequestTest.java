package com.nashss.se.artanywhere.activity.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetArtistRequestTest {
    private GetArtistRequest request = new GetArtistRequest("Degas");


    @Test
    void getArtistName_validStringGiven_returnsNameString() {
        assertEquals("Degas", request.getArtistName());
    }
    @Test
    void getArtistName_emptyStringGiven_returnsEmptyNameString() {
        GetArtistRequest request1 = GetArtistRequest.builder().withArtistName("").build();

        assertEquals("", request1.getArtistName());
    }

    @Test
    void builder_withName_buildsRequestAccurately() {
        GetArtistRequest request1 = GetArtistRequest.builder().withArtistName("Degas").build();
        assertEquals("Degas", request1.getArtistName());
    }
}