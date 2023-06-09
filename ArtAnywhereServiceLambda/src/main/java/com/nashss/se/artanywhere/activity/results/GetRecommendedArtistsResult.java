package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.ArtistModel;

import java.util.List;

public class GetRecommendedArtistsResult {
    private final List<ArtistModel> artists;

    public GetRecommendedArtistsResult(List<ArtistModel> artists) {
        this.artists = artists;
    }

    public List<ArtistModel> getArtists() {
        return artists;
    }

    public static Builder builder() {
            return new Builder();
        }
    public static class Builder {
        private List<ArtistModel> artists;

        public Builder withArtists(List<ArtistModel> artistModels) {
            this.artists = artistModels;
            return this;
        }

        public GetRecommendedArtistsResult build() {
                return new GetRecommendedArtistsResult(artists);
        }
    }
}

