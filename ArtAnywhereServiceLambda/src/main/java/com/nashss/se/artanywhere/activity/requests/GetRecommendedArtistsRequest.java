package com.nashss.se.artanywhere.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = GetRecommendedArtistsRequest.Builder.class)
public class GetRecommendedArtistsRequest {
    private final String artistName;

    public GetRecommendedArtistsRequest(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistName() {
        return artistName;
    }
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String artistName;
        public Builder withArtistName(String artistName) {
            this.artistName = artistName;
            return this;
        }
        public GetRecommendedArtistsRequest build() {
            return new GetRecommendedArtistsRequest(artistName);
        }
    }
}


