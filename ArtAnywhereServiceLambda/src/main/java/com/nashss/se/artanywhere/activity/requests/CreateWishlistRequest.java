package com.nashss.se.artanywhere.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

@JsonDeserialize(builder = CreateWishlistRequest.Builder.class)
public class CreateWishlistRequest {
    private final String listName;
    private final String email;
    private final String description;

    public CreateWishlistRequest(String listName, String email, String description) {
        this.listName = listName;
        this.email = email;
        this.description = description;
    }

    public String getListName() {
        return listName;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "CreateWishlistRequest{" +
                "listName='" + listName + '\'' +
                ", email='" + email + '\'' +
                ", description=" + description +
                '}';
    }
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String listName;
        private String email;
        private String description;

        public Builder withListName(String name) {
            this.listName = name;
            return this;
        }
        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }
        public CreateWishlistRequest build() {
            return new CreateWishlistRequest(listName, email, description);
        }
    }
}
