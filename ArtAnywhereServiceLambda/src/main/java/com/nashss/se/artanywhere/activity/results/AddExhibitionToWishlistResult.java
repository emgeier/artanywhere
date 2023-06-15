package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.ExhibitionModel;

import java.util.ArrayList;
import java.util.List;

public class AddExhibitionToWishlistResult {

    private final List<String> exhibitions;

    public AddExhibitionToWishlistResult(List<String> exhibitions) {
        this.exhibitions = exhibitions;
    }

    public List<String> getExhibitions() {
        return exhibitions;
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
        private List<String> exhibitions;
        public Builder withExhibitions(List<String> exhibitions) {
            System.out.println("withExhibitions Models");
            this.exhibitions = new ArrayList<>(exhibitions);
            return this;
        }
        public AddExhibitionToWishlistResult build() {
            return new AddExhibitionToWishlistResult(exhibitions);
        }
    }
}
