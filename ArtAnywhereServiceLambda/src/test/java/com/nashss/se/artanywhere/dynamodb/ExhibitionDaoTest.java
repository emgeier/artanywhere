package com.nashss.se.artanywhere.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;

import com.nashss.se.artanywhere.exceptions.ExhibitionNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class ExhibitionDaoTest {
    @Mock
    DynamoDBMapper dynamoDBMapper;
    @InjectMocks
    ExhibitionDao dao;
    @BeforeEach
    void setup() {
        openMocks(this);
    }
    @Test
    public void getExhibition_callsMapperWithKey() {
        Exhibition test = new Exhibition();
        test.setCityCountry("Madrid");
        test.setExhibitionName("Picasso Rules");
        List<Exhibition.MEDIUM> testMedia = new ArrayList<>();
        testMedia.add(Exhibition.MEDIUM.CERAMICS);
        testMedia.add(Exhibition.MEDIUM.FILM);
        test.setMedia(testMedia);

        when(dynamoDBMapper.load(Exhibition.class, "Madrid", "Picasso Rules")).thenReturn(test);
        Exhibition result = dao.getExhibition("Madrid", "Picasso Rules");
        verify(dynamoDBMapper).load(Exhibition.class, "Madrid", "Picasso Rules");
        assertEquals(test.getCityCountry(), result.getCityCountry());
        assertEquals(result.getMedia().get(0), Exhibition.MEDIUM.CERAMICS);
    }
    @Test
    public void getExhibition_notInDatabase_throwsException() {
        assertThrows(ExhibitionNotFoundException.class, () -> {
            dao.getExhibition("test", "test");
        });
    }
}
