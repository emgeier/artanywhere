package com.nashss.se.artanywhere.models;

import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import java.util.List;
import java.util.Objects;

public class WishlistModel {
    private String listName;
    private String email;
    private List<Exhibition> exhibitions;
    private List<String> tags;

    public WishlistModel(String name, String email,  List<Exhibition> exhibitions, List<String> tags) {
        this.listName = name;
        this.email = email;

        this.exhibitions = exhibitions;

        this.tags = tags;
    }



    public String getListName() {
        return listName;
    }

    public void setListName(String name) {
        this.listName = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public List<Exhibition> getExhibitions() {
        return exhibitions;
    }

    public void setExhibitions(List<Exhibition> exhibitions) {
        this.exhibitions = exhibitions;
    }


    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WishlistModel that = (WishlistModel) o;
        return Objects.equals(listName, that.listName) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listName, email);
    }
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String listName;
        private String email;

        private List<Exhibition> exhibitions;

        private List<String> tags;
        public Builder withListName(String name) {
            this.listName = name;
            return this;
        }
        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withExhibitions(List<Exhibition> exhibitions) {
            this.exhibitions = exhibitions;
            return this;
        }
        public Builder withTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public WishlistModel build() {
            return  new WishlistModel(listName, email,exhibitions, tags);

        }
    }
}
