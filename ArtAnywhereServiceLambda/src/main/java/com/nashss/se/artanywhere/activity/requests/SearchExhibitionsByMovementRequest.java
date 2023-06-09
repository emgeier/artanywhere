package com.nashss.se.artanywhere.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;

@JsonDeserialize(builder = SearchExhibitionsByMovementRequest.class)
public class SearchExhibitionsByMovementRequest {
    private final String movement;

    public SearchExhibitionsByMovementRequest(String movement) {
        this.movement = movement;
    }

    public String getMovement() {
        return movement;
    }

    @Override
    public String toString() {
        return "SearchExhibitionsByMovementRequest{" +
                "movement='" + movement + '\'' +
                '}';
    }
    public static  Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String movement;

        public Builder withMovement(String movement) {
            this.movement = movement;
            return this;
        }
        public SearchExhibitionsByMovementRequest build() {
            return new SearchExhibitionsByMovementRequest(movement);
        }
    }
}
