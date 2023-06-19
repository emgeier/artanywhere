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
import java.util.*;

public class GetRecommendedArtistsActivity {
    private final Logger log = LogManager.getLogger();
    private final ArtistDao artistDao;
    @Inject
    public GetRecommendedArtistsActivity(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }
    public GetRecommendedArtistsResult handleRequest(GetRecommendedArtistsRequest request) {
        log.error("GetRecommendedArtistsRequest received by activity {}.", request);

        Set<Artist> artistSet = new HashSet<>();
        Artist artist;
        try {

            List<Artist> getResult = artistDao.getArtist(request.getArtistName());
            if (getResult == null) {
                log.error("Artist named {} is not found in database.",
                    request.getArtistName());
                throw new ArtistNotFoundException("Artist named {} is not found in database.");
            }
            artist = getResult.get(0);

            log.info("GetRecommendedArtistActivity found artist {}.", request.getArtistName());

            if (artist.getMovements() != null ) {
                log.info("GetRecommendedArtistsRequest movements not null {}.", artist.getMovements());
                List<Artist> similarArtistsMovement = artistDao.getArtistsByMovement(artist.getMovements().get(0).name());
                artistSet.addAll(Optional.ofNullable(similarArtistsMovement).orElse(new ArrayList<>()));
                log.info("GetRecommendedArtistsActivity found {} similar artists", artistSet.size());
            }
            if( artistSet.size() < 2 && artist.getPrimaryMedium() != null) {
                log.info("GetRecommendedArtistsActivity did not enough find similar artists by movements. " +
                        "Looking by medium {} similar artists", artist.getPrimaryMedium());
                List<Artist> artistListMedium = artistDao.getArtistsByMediumAndBirthYear(artist.getPrimaryMedium(),
                        artist.getBirthYear());
                artistSet.addAll(Optional.ofNullable(artistListMedium).orElse(new ArrayList<>()));
            }

        } catch (ArtistNotFoundException ex) {
            log.error("Artist named {} is not found in database.",
                    request.getArtistName());
            throw new ArtistNotFoundException(ex.getMessage(), ex.getCause());
        }
        artistSet.remove(artist);
        if (artistSet.isEmpty()) {
            throw new ArtistNotFoundException(String.format("No similar artists to %s found.", artist));
        }
       //Could reduce processing by making the result use a set instead of a list
        List<Artist> artistList = new ArrayList<>(artistSet);

        return GetRecommendedArtistsResult.builder()
                .withArtists(new ModelConverter().toArtistModelList(artistList))
                .build();
    }
}
