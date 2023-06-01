package com.nashss.se.artanywhere.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = SearchExhibitionsByCityAndMediumRequest.Builder.class)
public class SearchExhibitionsByCityAndMediumRequest {
    private final String cityCountry;
    private final String medium;

    public SearchExhibitionsByCityAndMediumRequest(String cityCountry, String medium) {
        this.cityCountry = cityCountry;
        this.medium = medium;
    }

    public String getCityCountry() {
        return cityCountry;
    }

    public String getMedium() {
        return medium;
    }

    @Override
    public String toString() {
        return "SearchExhibitionsByCityAndMediumRequest{" +
                "cityCountry='" + cityCountry + '\'' +
                ", medium='" + medium + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String cityCountry;
        private String medium;

        public Builder withCityCountry(String cityCountry) {
            this.cityCountry = cityCountry;
            return this;
        }
        public Builder withMedium(String medium) {
            this.medium = medium;
            return this;
        }
        public SearchExhibitionsByCityAndMediumRequest build() {
            return new SearchExhibitionsByCityAndMediumRequest(cityCountry, medium);
        }
    }
}
