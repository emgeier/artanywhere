package com.nashss.se.artanywhere.models;

import com.nashss.se.artanywhere.converters.DateConverter;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ExhibitionModel {
    private String cityCountry;
    private String exhibitionName;
    private String institution;
    private String startDate;
    private String endDate;
    private String address;
    private List<String> tags;
    private List<Exhibition.MEDIUM> media;
    private Exhibition.MOVEMENT movement;
    private List<String> artists;
    private List<String> art;
    private String description;
    private String imageUrl;
    private String imageAttribution;

    public ExhibitionModel(String cityCountry, String exhibitionName, String institution, String startDate, String endDate, String address,
                           List<String> tags, List<Exhibition.MEDIUM> media, Exhibition.MOVEMENT movement, List<String> artists, List<String> art, String description, String imageUrl, String imageAttribution) {
        this.cityCountry = cityCountry;
        this.exhibitionName = exhibitionName;
        this.institution = institution;
        this.startDate = startDate;
        this.endDate = endDate;
        this.address = address;
        this.tags = tags;
        this.media = media;
        this.movement = movement;
        this.artists = artists;
        this.art = art;
        this.description = description;
        this.imageUrl = imageUrl;
        this.imageAttribution = imageAttribution;
    }

    public String getCityCountry() {
        return cityCountry;
    }

    public String getExhibitionName() {
        return exhibitionName;
    }

    public String getInstitution() {
        return institution;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<Exhibition.MEDIUM> getMedia() {
        return media;
    }

    public Exhibition.MOVEMENT getMovement() {
        return movement;
    }

    public List<String> getArtists() {
        return artists;
    }

    public List<String> getArt() {
        return art;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImageAttribution() {
        return imageAttribution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExhibitionModel that = (ExhibitionModel) o;
        return cityCountry.equals(that.cityCountry) && exhibitionName.equals(that.exhibitionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityCountry, exhibitionName);
    }
    public static Builder builder() {return new Builder();}

    public static class Builder {
        private String cityCountry;
        private String exhibitionName;
        private String institution;
        private String startDate;
        private String endDate;
        private String address;
        private List<String> tags;
        private List<Exhibition.MEDIUM> media;
        private Exhibition.MOVEMENT movement;
        private List<String> artists;
        private List<String> art;
        private String description;
        private String imageUrl;
        private String imageAttribution;
        public Builder withCityCountry(String cityCountry) {
            this.cityCountry = cityCountry;
            return this;
        }
        public Builder withExhibitionName(String name) {
            this.exhibitionName = name;
            return this;
        }
        public Builder withInstitution(String institution) {
            this.institution = institution;
            return this;
        }
        public Builder withStartDate(LocalDate startDate) {
            this.startDate = new DateConverter().convertToJson(startDate);
            return this;
        }
        public Builder withEndDate(LocalDate endDate) {
            this.endDate = new DateConverter().convertToJson(endDate);
            return this;
        }
        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }
        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }
        public Builder withArt(List<String> art) {
            this.art = art;
            return this;
        }
        public Builder withArtists(List<String> artists) {
            this.artists = artists;
            return this;
        }
        public Builder withTags(List<String> tags) {
            this.tags = tags;
            return this;
        }
        public Builder withMedia(List<Exhibition.MEDIUM> media) {
            this.media = media;
            return this;
        }
        public Builder withMovement(Exhibition.MOVEMENT movement) {
            this.movement = movement;
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

        public ExhibitionModel build() {
            return new ExhibitionModel(cityCountry, exhibitionName, institution, startDate, endDate, address,
                    tags, media, movement, artists, art, description, imageUrl, imageAttribution);
        }

    }
}
