package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.ExhibitionModel;

import java.util.List;

public class SearchExhibitionsByDateResult {
    private final List<ExhibitionModel> exhibitions;

    public SearchExhibitionsByDateResult(List<ExhibitionModel> exhibitionModels) {
        exhibitions = exhibitionModels;
    }

    public List<ExhibitionModel> getExhibitions() {
        return exhibitions;
    }

    @Override
    public String toString() {
        return "SearchExhibitionsByDateResult{" +
                "exhibitions=" + exhibitions +
                '}';
    }
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private List<ExhibitionModel> exhibitions;

        public Builder withExhibitions(List<ExhibitionModel> exhibitions) {
            this.exhibitions = exhibitions;
            return this;
        }
        public SearchExhibitionsByDateResult build() {
            return new SearchExhibitionsByDateResult(exhibitions);
        }
    }
}
