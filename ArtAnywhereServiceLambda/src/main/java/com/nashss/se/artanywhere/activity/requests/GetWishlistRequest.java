package com.nashss.se.artanywhere.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = GetWishlistRequest.class)
public class GetWishlistRequest {
    private final String email;
    private final String listName;

    public GetWishlistRequest(String email, String listName) {
        this.email = email;
        this.listName = listName;
    }

    public String getEmail() {
        return email;
    }

    public String getListName() {
        return listName;
    }

    @Override
    public String toString() {
        return "GetWishlistRequest{" +
                "email='" + email + '\'' +
                ", listName='" + listName + '\'' +
                '}';
    }
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String email;
        private String listName;
        public Builder withListName(String name) {
            System.out.println("withLName" + name);
            this.listName = name;
            return this;
        }
        public Builder withEmail(String email) {
            System.out.println("withEmail" + email);
            this.email = email;
            return this;
        }
        public GetWishlistRequest build() {
            System.out.println("build with key" + email+ listName);
            return new GetWishlistRequest(email, listName);
        }
    }

}
