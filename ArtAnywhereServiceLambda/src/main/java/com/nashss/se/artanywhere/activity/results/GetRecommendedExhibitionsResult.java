package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.ExhibitionModel;

import java.util.List;

public class GetRecommendedExhibitionsResult {
    private final List<ExhibitionModel> exhibitions;

    @Override
    public String toString() {
        return "GetRecommendedExhibitionsResult{" +
                "exhibitions=" + exhibitions +
                '}';
    }

    public GetRecommendedExhibitionsResult(List<ExhibitionModel> exhibitions) {
        this.exhibitions = exhibitions;
    }

    public List<ExhibitionModel> getExhibitions() {
        return exhibitions;
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
        public GetRecommendedExhibitionsResult build() {

            return new GetRecommendedExhibitionsResult(exhibitions);
        }
    }
}
