package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.models.ExhibitionModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetRecommendedExhibitionsResultTest {

    @Test
    void testToString_returnsStringWithAttribute() {
        List<Exhibition> exhibitions = new ArrayList<>();
        List<ExhibitionModel> models = new ModelConverter().toExhibitionModelList(exhibitions);
        GetRecommendedExhibitionsResult result = GetRecommendedExhibitionsResult.builder()
                .withExhibitions(models).build();
        assertTrue(result.toString().contains("exhibitions"));
    }

    @Test
    void builder_validInput_buildsResult() {
        List<Exhibition> exhibitions = new ArrayList<>();
        List<ExhibitionModel> models = new ModelConverter().toExhibitionModelList(exhibitions);
        GetRecommendedExhibitionsResult result = GetRecommendedExhibitionsResult.builder()
                .withExhibitions(models).build();
        assertEquals(exhibitions, result.getExhibitions());
    }
}