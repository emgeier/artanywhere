package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.models.ExhibitionModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetExhibitionResultTest {

    @Test
    void toString_validInput_returnsStringWithAttribute() {
        Exhibition exhibition = new Exhibition();
        ExhibitionModel model = new ModelConverter().toExhibitionModel(exhibition);
        GetExhibitionResult result = GetExhibitionResult.builder()
                .withExhibition(model).build();
        assertTrue(result.toString().contains("exhibitionModel"));
    }
    @Test
    void builder_validInput_buildsResultWithModelExhibition() {
        Exhibition exhibition = new Exhibition();
        ExhibitionModel model = new ModelConverter().toExhibitionModel(exhibition);
        GetExhibitionResult result = GetExhibitionResult.builder()
                .withExhibition(model).build();
        assertEquals(model, result.getExhibition());
    }
}