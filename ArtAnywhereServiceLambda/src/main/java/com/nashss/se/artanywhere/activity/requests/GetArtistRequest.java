package com.nashss.se.artanywhere.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = GetArtistRequest.Builder.class)
public class GetArtistRequest {
    private final String artistName;

    public GetArtistRequest(String artistName) {
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
        public GetArtistRequest build() {
            return new GetArtistRequest(artistName);
        }
    }
}
