package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.ExhibitionModel;

public class GetExhibitionResult {
    private final ExhibitionModel exhibition;
    public GetExhibitionResult(ExhibitionModel exhibition) {
        this.exhibition = exhibition;
    }

    public ExhibitionModel getExhibition() {
        return exhibition;
    }

    @Override
    public String toString() {
        return "GetExhibitionResult{" +
                "exhibitionModel=" + exhibition +
                '}';
    }
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private ExhibitionModel exhibition;

        public Builder withExhibition(ExhibitionModel exhibitionModel) {
            this.exhibition = exhibitionModel;
            return this;
        }

        public GetExhibitionResult build() {
            return new GetExhibitionResult(exhibition);
        }
    }
}
