package com.nashss.se.artanywhere.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = SearchExhibitionsByMediumRequest.Builder.class)
public class SearchExhibitionsByMediumRequest {

    private final String medium;

    public SearchExhibitionsByMediumRequest(String medium) {
        this.medium = medium;
    }



    public String getMedium() {
        return medium;
    }

    @Override
    public String toString() {
        return "SearchExhibitionsByMediumRequest{" +
                "medium='" + medium + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {

        private String medium;

        public Builder withMedium(String medium) {
            System.out.println(medium + " :request withMedium");
            this.medium = medium;
            return this;
        }
        public SearchExhibitionsByMediumRequest build() {
            return new SearchExhibitionsByMediumRequest(medium);
        }
    }
}
