package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.ExhibitionModel;

import java.util.List;

public class SearchExhibitionsByCityAndDateResult {
    private final List<ExhibitionModel> exhibitions;

    public SearchExhibitionsByCityAndDateResult(List<ExhibitionModel> exhibitions) {
        this.exhibitions = exhibitions;
    }

    public List<ExhibitionModel> getExhibitions() {
        return exhibitions;
    }

    @Override
    public String toString() {
        return "SearchExhibitionByCityAndDateResult{" +
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
        public SearchExhibitionsByCityAndDateResult build() {
            return new SearchExhibitionsByCityAndDateResult(exhibitions);
        }
    }
}
