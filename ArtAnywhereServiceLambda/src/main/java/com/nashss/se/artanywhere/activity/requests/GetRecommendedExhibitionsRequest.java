package com.nashss.se.artanywhere.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = GetRecommendedExhibitionsRequest.Builder.class)
public class GetRecommendedExhibitionsRequest {
    private final String cityCountry;
    private final String exhibitionName;

    public GetRecommendedExhibitionsRequest(String cityCountry, String exhibitionName) {
        this.cityCountry = cityCountry;
        this.exhibitionName = exhibitionName;
    }

    public String getCityCountry() {
        return cityCountry;
    }

    public String getExhibitionName() {
        return exhibitionName;
    }

    @Override
    public String toString() {
        return "GetRecommendedExhibitionsRequest{" +
                "cityCountry='" + cityCountry + '\'' +
                ", exhibitionName='" + exhibitionName + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String cityCountry;
        private String exhibitionName;

        public Builder withCityCountry(String cityCountry) {
            this.cityCountry = cityCountry;
            return this;
        }

        public Builder withExhibitionName(String exhibitionName) {
            this.exhibitionName = exhibitionName;
            return this;
        }
        public GetRecommendedExhibitionsRequest build() {
            return new GetRecommendedExhibitionsRequest(cityCountry, exhibitionName);
        }
    }
}
