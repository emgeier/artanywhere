package com.nashss.se.artanywhere.models;

import com.amazonaws.services.dynamodbv2.xspec.B;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import org.checkerframework.checker.units.qual.A;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ArtistModel {
    private String artistName;
    private Integer birthYear;
    private Integer deathYear;
    private List<Exhibition.MEDIUM> media;
    private List<Exhibition.MOVEMENT> movements;
    private List<String> tags;
    private Map<String, String> art;
    private String imageUrl;
    private String imageAttribution;
    private List<String> exhibitions;
    private String birthCountry;

    public ArtistModel(String artistName, Integer birthYear, Integer deathYear, List<Exhibition.MEDIUM> media, List<Exhibition.MOVEMENT> movements, List<String> tags, Map<String, String> art, String imageUrl, String imageAttribution, List<String> exhibitions, String birthCountry) {
        this.artistName = artistName;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.media = media;
        this.movements = movements;
        this.tags = tags;
        this.art = art;
        this.imageUrl = imageUrl;
        this.imageAttribution = imageAttribution;
        this.exhibitions = exhibitions;
        this.birthCountry = birthCountry;
    }

    public String getArtistName() {
        return artistName;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public List<Exhibition.MEDIUM> getMedia() {
        return media;
    }

    public List<Exhibition.MOVEMENT> getMovements() {
        return movements;
    }

    public List<String> getTags() {
        return tags;
    }

    public Map<String, String> getArt() {
        return art;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageAttribution() {
        return imageAttribution;
    }

    public List<String> getExhibitions() {
        return exhibitions;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistModel that = (ArtistModel) o;
        return artistName.equals(that.artistName) && birthYear.equals(that.birthYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistName, birthYear);
    }
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String artistName;
        private Integer birthYear;
        private Integer deathYear;
        private List<Exhibition.MEDIUM> media;
        private List<Exhibition.MOVEMENT> movements;
        private List<String> tags;
        private Map<String, String> art;
        private String imageUrl;
        private String imageAttribution;
        private List<String> exhibitions;
        private String birthCountry;
        public Builder withArtistName(String artistName) {
            this.artistName = artistName;
            return this;
        }
        public Builder withBirthYear(Integer birthYear) {
            this.birthYear = birthYear;
            return this;
        }
        public Builder withDeathYear(Integer deathYear) {
            this.deathYear = deathYear;
            return this;
        }
        public Builder withTags(List<String> tags) {
            this.tags = tags;
            return this;
        }
        public Builder withExhibitions(List<String> exhibitions) {
            this.exhibitions = exhibitions;
            return this;
        }
        public Builder withArt(Map<String, String> art) {
            //json this, right?
            this.art = art;
            return this;
        }
        public Builder withMovements(List<Exhibition.MOVEMENT> movements) {
            this.movements = movements;
            return this;
        }
        public Builder withMedia(List<Exhibition.MEDIUM> media) {
            this.media = media;
            return this;
        }
        public Builder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }
        public Builder withImageAttribution(String imageAttribution) {
            this.imageAttribution = imageAttribution;
            return this;
        }
        public ArtistModel build() {
            return new ArtistModel(artistName,birthYear,deathYear,media,movements,tags,art,
                    imageUrl,imageAttribution,exhibitions,birthCountry);
        }
    }
}
