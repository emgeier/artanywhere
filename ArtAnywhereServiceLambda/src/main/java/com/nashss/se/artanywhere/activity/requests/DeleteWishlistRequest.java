package com.nashss.se.artanywhere.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = DeleteWishlistRequest.class)
public class DeleteWishlistRequest {
    public DeleteWishlistRequest(String email, String listName) {
        this.email = email;
        this.listName = listName;
    }

    private final String email;
    private final String listName;

    public String getEmail() {
        return email;
    }

    public String getListName() {
        return listName;
    }

    @Override
    public String toString() {
        return "DeleteWishlistRequest{" +
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

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder withListName(String listName) {
            this.listName = listName;
            return this;
        }
        public DeleteWishlistRequest build() {
            return new DeleteWishlistRequest(email, listName);
        }
    }
}
