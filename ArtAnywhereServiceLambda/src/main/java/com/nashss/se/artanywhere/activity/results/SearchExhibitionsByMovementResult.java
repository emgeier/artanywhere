package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.ExhibitionModel;

import java.util.List;

public class SearchExhibitionsByMovementResult {
    private final List<ExhibitionModel> exhibitions;

    public SearchExhibitionsByMovementResult(List<ExhibitionModel> exhibitionModels) {
        exhibitions = exhibitionModels;
    }

    public List<ExhibitionModel> getExhibitions() {
        return exhibitions;
    }

    @Override
    public String toString() {
        return "SearchExhibitionsByMovementResult{" +
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
        public SearchExhibitionsByMovementResult build() {
            return new SearchExhibitionsByMovementResult(exhibitions);
        }
    }
}
