package com.nashss.se.artanywhere.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.nashss.se.artanywhere.converters.DateConverter;
import com.nashss.se.artanywhere.converters.MediaListConverter;
import com.nashss.se.artanywhere.converters.MovementEnumConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Java object corresponding to a record on the exhibitions table.
 */
@DynamoDBTable(tableName = "exhibitions")
public class Exhibition {
    public static final String MOVEMENT_INDEX = "ArtisticMovementExhibitionIndex";
    private String cityCountry;
    private String exhibitionName;
    private String institution;
    private LocalDate startDate;
    private LocalDate endDate;
    private String address;
    private List<String> tags;
    private List<MEDIUM> media;
    private MOVEMENT movement;
    private List<String> artists;
    private List<String> art;
    private String description;
    private String imageUrl;
    private String imageAttribution;

    public enum MEDIUM {
        PAINTING, TEXTILES, SCULPTURE, FILM, CERAMICS, PHOTOGRAPHY, DRAWING, LITHOGRAPH, DIGITAL,
        PERFORMANCE, INSTALLATION, VIDEO, MUSIC, ARCHITECTURE
    }
    public enum MOVEMENT {
        IMPRESSIONISM, SURREALISM, POP_ART, RENAISSANCE, CUBISM, MEDIEVAL, MAYAN, MODERNISM, FUTURISM, NEOCLASSICISM,
        EXPRESSIONISM, CONTEMPORARY, REALISM, BAROQUE

    }
    @DynamoDBHashKey(attributeName = "cityCountry")
    public String getCityCountry() {
        return cityCountry;
    }

    @DynamoDBRangeKey(attributeName = "exhibitionName")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = MOVEMENT_INDEX)
    public String getExhibitionName() {

        return exhibitionName;
    }

    @DynamoDBAttribute(attributeName = "institution")
    public String getInstitution() {

        return institution;
    }

    @DynamoDBTypeConverted(converter = DateConverter.class)
    @DynamoDBAttribute(attributeName = "startDate")
    public LocalDate getStartDate() {

        return startDate;
    }

    @DynamoDBTypeConverted(converter = DateConverter.class)
    @DynamoDBAttribute(attributeName = "endDate")
    public LocalDate getEndDate() {return endDate;}

    @DynamoDBAttribute(attributeName = "address")
    public String getAddress() {return address;}

    @DynamoDBAttribute(attributeName = "tags")
    public List<String> getTags() {
        if (tags == null) {
            return new ArrayList<>();
        }
        return tags;
    }
    @DynamoDBTypeConverted(converter = MediaListConverter.class)
    @DynamoDBAttribute(attributeName = "media")
    public List<MEDIUM> getMedia() {
        return media;
    }

    //@DynamoDBTypeConvertedEnum
    @DynamoDBTypeConverted(converter = MovementEnumConverter.class)
    @DynamoDBIndexHashKey(globalSecondaryIndexName = MOVEMENT_INDEX, attributeName = "movement")
    public MOVEMENT getMovement() {

        return movement;
    }
    @DynamoDBAttribute(attributeName = "artists")
    public List<String> getArtists() {
        return artists;
    }

    @DynamoDBAttribute(attributeName = "art")
    public List<String> getArt() {
        return art;
    }
    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }
    @DynamoDBAttribute(attributeName = "imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }
    @DynamoDBAttribute(attributeName = "imageAttribution")
    public String getImageAttribution() {
        return imageAttribution;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setImageAttribution(String imageAttribution) {
        this.imageAttribution = imageAttribution;
    }

    public void setCityCountry(String cityCountry) { this.cityCountry = cityCountry; }
    public void setExhibitionName(String name) { this.exhibitionName = name; }
    public void setAddress(String address) { this.address = address; }
    public void setInstitution(String institution) { this.institution = institution; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public void setMovement(MOVEMENT movement) {
        this.movement = movement;
    }
    public void setMedia(List<MEDIUM> media) { this.media = media; }
    public void setTags(List<String> tags) { this.tags = tags; }
    public void setArtists(List<String> artists) {
        this.artists = artists;
    }
    public void setArt(List<String> art) {
        this.art = art;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exhibition)) return false;
        Exhibition that = (Exhibition) o;
        return cityCountry.equals(that.cityCountry) && exhibitionName.equals(that.exhibitionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityCountry, exhibitionName);
    }
}
