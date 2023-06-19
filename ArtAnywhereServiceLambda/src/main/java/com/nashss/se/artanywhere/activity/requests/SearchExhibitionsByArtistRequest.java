package com.nashss.se.artanywhere.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = SearchExhibitionsByArtistRequest.Builder.class)
public class SearchExhibitionsByArtistRequest {
    private final String artistName;

    public SearchExhibitionsByArtistRequest(String artistName) {
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
        public SearchExhibitionsByArtistRequest build() {
            return new SearchExhibitionsByArtistRequest(artistName);
        }
    }
}
