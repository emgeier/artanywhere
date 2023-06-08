package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.GetArtistRequest;
import com.nashss.se.artanywhere.activity.requests.GetRecommendedArtistsRequest;
import com.nashss.se.artanywhere.activity.results.GetArtistResult;
import com.nashss.se.artanywhere.activity.results.GetRecommendedArtistsResult;
import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.ArtistDao;
import com.nashss.se.artanywhere.dynamodb.models.Artist;
import com.nashss.se.artanywhere.exceptions.ArtistNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class GetRecommendedArtistsActivity {
    private final Logger log = LogManager.getLogger();
    private final ArtistDao artistDao;
    @Inject
    public GetRecommendedArtistsActivity(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }
    public GetRecommendedArtistsResult handleRequest(GetRecommendedArtistsRequest request) {
        log.info("GetRecommendedArtistsRequest received by activity {}.", request);
        List<Artist> artistList;
        Artist artist;
        try {
            artist = artistDao.getArtist(request.getArtistName()).get(0);
            artistList = artistDao.getArtistsByMovement(artist.getMovements().get(0).name());
        } catch (ArtistNotFoundException ex) {
            log.error("Artist named {} is not found in database.",
                    request.getArtistName());
            throw new ArtistNotFoundException(ex.getMessage(), ex.getCause());
        }
        return GetRecommendedArtistsResult.builder()
                .withArtists(new ModelConverter().toArtistModelList(artistList))
                .build();
    }
}
