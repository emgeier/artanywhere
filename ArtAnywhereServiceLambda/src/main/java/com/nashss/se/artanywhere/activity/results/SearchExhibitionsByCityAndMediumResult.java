package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.ExhibitionModel;

import java.util.List;

public class SearchExhibitionsByCityAndMediumResult {
    private final List<ExhibitionModel> exhibitions;

    public SearchExhibitionsByCityAndMediumResult(List<ExhibitionModel> exhibitions) {
        this.exhibitions = exhibitions;
    }

    public List<ExhibitionModel> getExhibitions() {
        return exhibitions;
    }

    @Override
    public String toString() {
        return "SearchExhibitionByCityAndMediumResult{" +
                "exhibitions=" + exhibitions +
                '}';
    }
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private List<ExhibitionModel> exhibitions;

        public Builder withExhibitions(List<ExhibitionModel> exhibitionModels) {
            this.exhibitions = exhibitionModels;
            return this;
        }
        public SearchExhibitionsByCityAndMediumResult build() {
            return new SearchExhibitionsByCityAndMediumResult(exhibitions);
        }
    }
}
