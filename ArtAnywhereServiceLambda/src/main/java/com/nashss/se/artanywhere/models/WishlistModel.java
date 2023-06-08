package com.nashss.se.artanywhere.models;

import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import java.util.List;
import java.util.Objects;

public class WishlistModel {
    private String listName;
    private String email;
    private List<String> exhibitions;
    //private List<Exhibition> exhibitions;
    private String description;

    public WishlistModel(String email,  String name, List<String> exhibitions, String description) {
        this.listName = name;
        this.email = email;

        this.exhibitions = exhibitions;

        this.description = description;
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



    public List<String> getExhibitions() {
        return exhibitions;
    }

    public void setExhibitions(List<String> exhibitions) {
        this.exhibitions = exhibitions;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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

        private List<String> exhibitions;

        private String description;
        public Builder withListName(String name) {
            this.listName = name;
            return this;
        }
        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withExhibitions(List<String> exhibitions) {
            this.exhibitions = exhibitions;
            return this;
        }
        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public WishlistModel build() {
            return  new WishlistModel(email, listName, exhibitions, description);

        }
    }
}
