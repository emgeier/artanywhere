package com.nashss.se.artanywhere.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = RemoveExhibitionFromWishlistRequest.Builder.class)
public class RemoveExhibitionFromWishlistRequest {
    private final String email;
    private final String listName;
    private final String cityCountry;
    private final String exhibitionName;

    public RemoveExhibitionFromWishlistRequest(String email, String listName, String cityCountry, String exhibitionName) {
        this.email = email;
        this.listName = listName;
        this.cityCountry = cityCountry;
        this.exhibitionName = exhibitionName;
    }

    public String getEmail() {
        return email;
    }

    public String getListName() {
        return listName;
    }

    public String getCityCountry() {
        return cityCountry;
    }

    public String getExhibitionName() {
        return exhibitionName;
    }

    @Override
    public String toString() {
        return "RemoveExhibitionFromWishlistRequest{" +
                "email='" + email + '\'' +
                ", listName='" + listName + '\'' +
                ", cityCountry='" + cityCountry + '\'' +
                ", exhibitionName='" + exhibitionName + '\'' +
                '}';
    }
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String email;
        private String listName;
        private String cityCountry;
        private String exhibitionName;

        public Builder withExhibitionName(String exhibitionName) {
            this.exhibitionName = exhibitionName;
            return this;
        }

        public Builder withCityCountry(String cityCountry) {
            this.cityCountry = cityCountry;
            return this;
        }

        public Builder withListName(String listName) {
            this.listName = listName;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }
        public RemoveExhibitionFromWishlistRequest build() {
            return new RemoveExhibitionFromWishlistRequest(email, listName, cityCountry, exhibitionName);
        }
    }
}

