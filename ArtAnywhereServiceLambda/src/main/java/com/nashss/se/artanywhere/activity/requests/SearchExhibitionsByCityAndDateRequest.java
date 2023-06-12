package com.nashss.se.artanywhere.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(builder = SearchExhibitionsByCityAndDateRequest.Builder.class)
public class SearchExhibitionsByCityAndDateRequest {
    private final String startDate;
    private final String endDate;
    private final String cityCountry;


    public SearchExhibitionsByCityAndDateRequest(String startDate, String endDate, String cityCountry) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.cityCountry = cityCountry;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getCityCountry() {
        return cityCountry;
    }
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String startDate;
        private String endDate;
        private String cityCountry;
        public Builder withStartDate(String startDate) {

            this.startDate = startDate;
            return this;
        }
        public Builder withEndDate(String endDate) {
            this.endDate = endDate;
            return this;
        }
        public Builder withCityCountry(String cityCountry) {
            this.cityCountry = cityCountry;
            return this;
        }
        public SearchExhibitionsByCityAndDateRequest build() {
            return new SearchExhibitionsByCityAndDateRequest(startDate, endDate, cityCountry);
        }

    }

}
