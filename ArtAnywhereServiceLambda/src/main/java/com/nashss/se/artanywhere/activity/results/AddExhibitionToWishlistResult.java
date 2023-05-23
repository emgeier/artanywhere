package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.ExhibitionModel;

import java.util.ArrayList;
import java.util.List;

public class AddExhibitionToWishlistResult {

    private final List<ExhibitionModel> exhibitionModels;

    public AddExhibitionToWishlistResult(List<ExhibitionModel> exhibitionModels) {
        this.exhibitionModels = exhibitionModels;
    }

    public List<ExhibitionModel> getExhibitionModels() {
        return new ArrayList<>(exhibitionModels);
    }

    @Override
    public String toString() {
        return "AddExhibitionToWishlistResult{" +
                "exhibitionModels=" + exhibitionModels +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private List<ExhibitionModel> exhibitionModels;
        public Builder withExhibitionModels(List<ExhibitionModel> models) {
            this.exhibitionModels =
                   new ArrayList<>(models);
            return this;
        }
        public AddExhibitionToWishlistResult build() {
            return new AddExhibitionToWishlistResult(exhibitionModels);
        }
    }
}
