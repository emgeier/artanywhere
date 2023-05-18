package com.nashss.se.artanywhere.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

@JsonDeserialize(builder = CreateWishlistRequest.Builder.class)
public class CreateWishlistRequest {
    private final String listName;
    private final String email;
    private final List<String> tags;

    public CreateWishlistRequest(String listName, String email, List<String> tags) {
        this.listName = listName;
        this.email = email;
        this.tags = tags;
    }

    public String getListName() {
        return listName;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "CreateWishlistRequest{" +
                "listName='" + listName + '\'' +
                ", email='" + email + '\'' +
                ", tags=" + tags +
                '}';
    }
    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String listName;
        private String email;
        private List<String> tags;

        public Builder withListName(String name) {
            this.listName = name;
            return this;
        }
        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder withTags(List<String> tags) {
            this.tags = List.copyOf(tags);
            return this;
        }
        public CreateWishlistRequest build() {
            return new CreateWishlistRequest(listName, email, tags);
        }
    }
}
