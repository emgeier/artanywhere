package com.nashss.se.artanywhere.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize()
public class SearchExhibitionsByDateRequest {
    private final String startDate;
    private final String endDate;


    public SearchExhibitionsByDateRequest(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return "SearchExhibitionsByDateRequest{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String startDate;
        private String endDate;
        public Builder withStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }
        public Builder withEndDate(String endDate) {
            this.endDate = endDate;
            return this;
        }
        public SearchExhibitionsByDateRequest build() {
            return new SearchExhibitionsByDateRequest(startDate, endDate);
        }
    }
}
