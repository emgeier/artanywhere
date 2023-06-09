package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.activity.requests.SearchExhibitionsByArtistRequest;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.models.ExhibitionModel;

import java.util.List;

public class SearchExhibitionsByArtistResult {
    List<ExhibitionModel> exhibitions;

    public SearchExhibitionsByArtistResult(List<ExhibitionModel> exhibitions) {
        this.exhibitions = exhibitions;
    }

    public List<ExhibitionModel> getExhibitions() {
        return exhibitions;
    }
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        List<ExhibitionModel> exhibitions;
        public Builder withExhibitions(List<ExhibitionModel> exhibitions) {
            this.exhibitions = exhibitions;
            return this;
        }
        public SearchExhibitionsByArtistResult build() {
            return new SearchExhibitionsByArtistResult(exhibitions);
        }
    }
}
