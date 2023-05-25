package com.nashss.se.artanywhere.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = AddExhibitionToWishlistRequest.Builder.class)
public class AddExhibitionToWishlistRequest {
    private final String email;
    private final String listName;
    private final String cityCountry;
    private final String exhibitionName;


    public AddExhibitionToWishlistRequest(String email, String listName, String cityCountry, String exhibitionName) {
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
        private String exhibitionName;
        private String cityCountry;
        private String listName;
        private String email;

        public Builder withExhibitionName(String exhibitionName) {
            System.out.println("withExName");
            this.exhibitionName = exhibitionName;
            return this;
        }
        public Builder withCityCountry(String cityCountry) {
            System.out.println("withCC");

            this.cityCountry = cityCountry;
            return this;
        }
        public Builder withListName(String listName) {
            System.out.println("withLName");

            this.listName = listName;
            return this;
        }
        public Builder withEmail(String email) {
            System.out.println("withEamil");

            this.email = email;
            return this;
        }

        public AddExhibitionToWishlistRequest build() {
            return new AddExhibitionToWishlistRequest(email, listName, cityCountry, exhibitionName);
        }
    }
}
