package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.GetArtistRequest;
import com.nashss.se.artanywhere.activity.results.GetArtistResult;
import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.ArtistDao;
import com.nashss.se.artanywhere.dynamodb.models.Artist;

import com.nashss.se.artanywhere.exceptions.ArtistNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class GetArtistActivity {
    private final Logger log = LogManager.getLogger();
    private final ArtistDao artistDao;
    @Inject
    public GetArtistActivity(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }
    public GetArtistResult handleRequest(GetArtistRequest request) {
        log.info("GetExhibitionRequest received {}.", request);
        List<Artist> artistList;
        try {
            artistList = artistDao.getArtist(request.getArtistName());
        } catch (ArtistNotFoundException ex) {
            log.error("Artist named {} is not found in database.",
                    request.getArtistName());
            throw new ArtistNotFoundException(ex.getMessage(), ex.getCause());
        }
        return GetArtistResult.builder()
                .withArtists(new ModelConverter().toArtistModelList(artistList))
                .build();
    }

}
