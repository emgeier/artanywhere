package com.nashss.se.artanywhere.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = SearchExhibitionsByCityRequest.Builder.class)
public class SearchExhibitionsByCityRequest {
    private final String cityCountry;

    public SearchExhibitionsByCityRequest(String cityCountry) {
        this.cityCountry = cityCountry;
    }

    public String getCityCountry() {
        return cityCountry;
    }

    @Override
    public String toString() {
        return "SearchExhibitionsByCityRequest{" +
                "cityCountry='" + cityCountry + '\'' +
                '}';
    }
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String cityCountry;

        public Builder withCityCountry(String cityCountry) {
            this.cityCountry = cityCountry;
            return this;
        }
        public SearchExhibitionsByCityRequest build() {
            return new SearchExhibitionsByCityRequest(cityCountry);
        }
    }
}
