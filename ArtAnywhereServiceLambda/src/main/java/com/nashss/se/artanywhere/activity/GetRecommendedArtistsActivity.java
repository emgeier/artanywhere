package com.nashss.se.artanywhere.activity;

import com.nashss.se.artanywhere.activity.requests.GetRecommendedArtistsRequest;
import com.nashss.se.artanywhere.activity.results.GetRecommendedArtistsResult;
import com.nashss.se.artanywhere.converters.ModelConverter;
import com.nashss.se.artanywhere.dynamodb.ArtistDao;
import com.nashss.se.artanywhere.dynamodb.models.Artist;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.exceptions.ArtistNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GetRecommendedArtistsActivity {
    private final Logger log = LogManager.getLogger();
    private final ArtistDao artistDao;
    @Inject
    public GetRecommendedArtistsActivity(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }
    public GetRecommendedArtistsResult handleRequest(GetRecommendedArtistsRequest request) {
        log.error("GetRecommendedArtistsRequest received by activity {}.", request);
System.out.println("getrecommendedartist activity");
        List<Artist> artistList = new ArrayList<>();
        Artist artist;
        try {
System.out.println(request.getArtistName());
            artist = artistDao.getArtist(request.getArtistName()).get(0);
            log.error("GetRecommendedArtistActivity found artist {}.", request.getArtistName());
System.out.println("1");
            if (artist.getMovements() != null ) {
                log.error("GetRecommendedArtistsRequest movements not null {}.", artist.getMovements());
                System.out.println("2");
                artistList.addAll (artistDao.getArtistsByMovement(artist.getMovements().get(0).name()));
                System.out.println("3");
                log.error("GetRecommendedArtistsActivity found {} similar artists", artistList.size());
            }
            if( artistList.size() < 2 && artist.getPrimaryMedium() != null) {
                log.error("GetRecommendedArtistsActivity did not enough find similar artists by movements. " +
                        "Looking by medium {} similar artists", artist.getPrimaryMedium());
                List<Artist> artistListMedium = artistDao.getArtistsByMediumAndBirthYear(artist.getPrimaryMedium(),
                        artist.getBirthYear());
                artistList.addAll(artistListMedium);
            }

        } catch (ArtistNotFoundException ex) {
            log.error("Artist named {} is not found in database.",
                    request.getArtistName());
            throw new ArtistNotFoundException(ex.getMessage(), ex.getCause());
        }
        artistList.remove(artist);
        System.out.println("4");
        log.error("GetRecommendedArtistActivity artist list converted to model  {} .",
                new ModelConverter().toArtistModelList(artistList).toString());
        System.out.println("5");
        return GetRecommendedArtistsResult.builder()
                .withArtists(new ModelConverter().toArtistModelList(artistList))
                .build();
    }
}
