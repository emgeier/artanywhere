package com.nashss.se.artanywhere.administrative;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Set;

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

    }
    @Test
    void fixDescription_inputStringWithoutHtmlNotation_returnsStringUnchanged() {
        String htmlString = "Dürer was schooled in painting by Michael Wolgemut and began making works on paper in the 1490s.";
        String result = ArtInstituteDataInput.fixDescription(htmlString);
        assertEquals("Dürer was schooled in painting by Michael Wolgemut and began making works on paper in the 1490s.", result);

    }
    @Test
    void fixDescription_inputStringEmpty_returnsStringUnchanged() {
        String htmlString = "";
        String result = ArtInstituteDataInput.fixDescription(htmlString);
        assertEquals("", result);

    }
    @Test
    void findTags_inputWithTags_returnsListOfTags() {

        String input = "abstraction, Russian, Japanese, prints, fashion designer, calligraphy, Baroque, 50 years, photographer, filmmaker, musician, and author Gordon Parks created an iconic body of work that documented American life and culture, with a focus on social justice, the civil rights movement, and the African American experience.";
        Set<String> tags =  ArtInstituteDataInput.findTags(input);
        assertTrue(tags.contains("abstract"));
        assertTrue(tags.contains("Japanese"));
        assertTrue(tags.contains("prints"));
        assertTrue(tags.contains("calligraphy"));
        assertTrue(tags.contains("Baroque"));
        assertTrue(tags.contains("calligraphy"));
    }
    @Test
    void findMedia_inputWithTags_returnsListOfMedia() {

        String input = "abstraction lithographic sculptor, installation, paints with digital Russian drawings, Japanese prints, Expressionism, fashion designer, calligraphy, Baroque, 50 years, photographer, filmmaker, musician, and author Gordon Parks created an iconic body of work that documented American life and culture, with a focus on social justice, the civil rights movement, and the African American experience.";
        Set<String> output =  ArtInstituteDataInput.findMedia(input);
        assertTrue(output.contains("PHOTOGRAPHY"));
        assertTrue(output.contains("PAINTING"));
        assertTrue(output.contains("INSTALLATION"));
        assertTrue(output.contains("DIGITAL"));
        assertTrue(output.contains("TEXTILES"));
        assertTrue(output.contains("LITHOGRAPH"));
        assertTrue(output.contains("SCULPTURE"));
        assertTrue(output.contains("DRAWING"));


    }
    @Test
    void findMovements_inputWithTags_returnsList() {

        String input = "Modernism wields surrealist Neoclassical abstraction, Expressionist Futurist Russian Cubism, Japanese Impressionism, prints, fashion designer, calligraphy, Baroque, 50 years, Renaissance photographer, realistic filmmaker of the Dark Ages musician, and author Gordon Parks created an iconic body of work that documented American life and culture, with a focus on social justice, the civil rights movement, and the African American experience.";
        List<String> output =  ArtInstituteDataInput.findMovements(input);

        assertTrue(output.contains("EXPRESSIONISM"));
        assertTrue(output.contains("CUBISM"));
        assertTrue(output.contains("IMPRESSIONISM"));
        assertTrue(output.contains("FUTURISM"));
        assertTrue(output.contains("SURREALISM"));
        assertTrue(output.contains("MODERNISM"));
        assertTrue(output.contains("MEDIEVAL"));
        assertTrue(output.contains("RENAISSANCE"));
        assertTrue(output.contains("NEOCLASSICAL"));
        assertTrue(output.contains("REALISM"));
    }
}