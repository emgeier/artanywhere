package com.nashss.se.artanywhere.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = AddExhibitionToWishlistRequest.Builder.class)
public class AddExhibitionToWishlistRequest {
    private final String exhibitionName;
    private final String cityCountry;
    private final String listName;
    private final String email;
    public AddExhibitionToWishlistRequest(String exhibitionName, String cityCountry, String listName, String email) {
        this.exhibitionName = exhibitionName;
        this.cityCountry = cityCountry;
        this.listName = listName;
        this.email = email;
    }

    public String getExhibitionName() {
        return exhibitionName;
    }

    public String getCityCountry() {
        return cityCountry;
    }

    public String getListName() {
        return listName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "AddExhibitionToWishlistRequest{" +
                "exhibitionName='" + exhibitionName + '\'' +
                ", cityCountry='" + cityCountry + '\'' +
                ", listName='" + listName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
@JsonPOJOBuilder
    public static class Builder {
        private String exhibitionName;
        private String cityCountry;
        private String listName;
        private String email;

        public Builder withExhibitionName(String name) {
            this.exhibitionName = name;
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

        public AddExhibitionToWishlistRequest build() {
            return new AddExhibitionToWishlistRequest(exhibitionName, cityCountry, listName, email);
        }
    }
}
