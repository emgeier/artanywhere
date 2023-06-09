package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.ExhibitionModel;

import java.util.List;

public class SearchExhibitionsByMediumResult {
    private final List<ExhibitionModel> exhibitions;

    public SearchExhibitionsByMediumResult(List<ExhibitionModel> exhibitions) {
        this.exhibitions = exhibitions;
    }

    public List<ExhibitionModel> getExhibitions() {
        return exhibitions;
    }

    @Override
    public String toString() {
        return "SearchExhibitionsByMediumResult{" +
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
        public SearchExhibitionsByMediumResult build() {
            System.out.println(exhibitions + " :result build");
            return new SearchExhibitionsByMediumResult(exhibitions);
        }
    }
}
