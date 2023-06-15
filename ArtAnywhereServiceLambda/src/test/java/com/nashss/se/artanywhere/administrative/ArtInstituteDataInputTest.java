package com.nashss.se.artanywhere.administrative;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ArtInstituteDataInputTest {
    @Mock
    private DynamoDBMapper dynamoDBMapper;
    @InjectMocks
    private ArtInstituteDataInput artInstituteDataInput;

    @BeforeEach
    public void setup() {
        openMocks(this);
    }
    @Test
    void fixDescription_inputStringWithHtmlNotation_returnsStringWithoutHtmlNotation() {
        String htmlString = "Dürer was schooled in painting by <a href=\\\"https://www.artic.edu/collection?artist_" +
                "ids=Michel+Wolgemut\\\" target=\\\"_blank\\\">Michael Wolgemut</a> " +
                "and began making works on paper in the 1490s.";
        String result = ArtInstituteDataInput.fixDescription(htmlString);
        assertEquals("Dürer was schooled in painting by Michael Wolgemut and began making works on paper in the 1490s.", result);
        System.out.println(result);
    }
    @Test
    void fixDescription_inputStringWithoutHtmlNotation_returnsStringUnchanged() {
        String htmlString = "Dürer was schooled in painting by Michael Wolgemut and began making works on paper in the 1490s.";
        String result = ArtInstituteDataInput.fixDescription(htmlString);
        assertEquals("Dürer was schooled in painting by Michael Wolgemut and began making works on paper in the 1490s.", result);
        System.out.println(result);
    }
    @Test
    void fixDescription_inputStringEmpty_returnsStringUnchanged() {
        String htmlString = "";
        String result = ArtInstituteDataInput.fixDescription(htmlString);
        assertEquals("", result);
        System.out.println(result);
    }
}