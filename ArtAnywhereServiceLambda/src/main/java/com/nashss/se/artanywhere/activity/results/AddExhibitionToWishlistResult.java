package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.ExhibitionModel;

import java.util.ArrayList;
import java.util.List;

public class AddExhibitionToWishlistResult {

    private final List<ExhibitionModel> exhibitions;

    public AddExhibitionToWishlistResult(List<ExhibitionModel> exhibitionModels) {
        this.exhibitions = exhibitionModels;
    }

    public List<ExhibitionModel> getExhibitions() {
        return new ArrayList<>(exhibitions);
    }

    @Override
    public String toString() {
        return "AddExhibitionToWishlistResult{" +
                "exhibitions=" + exhibitions +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private List<ExhibitionModel> exhibitionModels;
        public Builder withExhibitions(List<ExhibitionModel> models) {
            this.exhibitionModels =
                   new ArrayList<>(models);
            return this;
        }
        public AddExhibitionToWishlistResult build() {
            return new AddExhibitionToWishlistResult(exhibitionModels);
        }
    }
}
