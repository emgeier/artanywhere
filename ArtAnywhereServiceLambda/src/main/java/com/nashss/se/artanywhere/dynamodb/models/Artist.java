package com.nashss.se.artanywhere.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import com.nashss.se.artanywhere.converters.MediaListConverter;
import com.nashss.se.artanywhere.converters.MovementEnumConverter;
import com.nashss.se.artanywhere.converters.MovementListConverter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@DynamoDBTable(tableName = "artists")
public class Artist {
    public static final String MEDIUM_INDEX = "ArtisticMediumArtistIndex";
    private String artistName;
    private Integer birthYear;
    private Integer deathYear;
    private List<Exhibition.MEDIUM> media;
    private Exhibition.MEDIUM primaryMedium;
    private List<Exhibition.MOVEMENT> movements;
    private Exhibition.MOVEMENT primaryMovement;
    private List<String> tags;
    private Map<String, String> art;
    private String imageUrl;
    private String imageAttribution;
    private List<String> exhibitions;
    private String birthCountry;


    @DynamoDBHashKey(attributeName = "artistName")
    public String getArtistName() {
        return artistName;
    }

    @DynamoDBRangeKey(attributeName = "birthYear")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = MEDIUM_INDEX)
    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }
    @DynamoDBAttribute(attributeName = "deathYear")
    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }
    @DynamoDBTypeConverted(converter = MediaListConverter.class)
    @DynamoDBAttribute(attributeName = "media")
    public List<Exhibition.MEDIUM> getMedia() {
        return media;
    }
    @DynamoDBTypeConvertedEnum
    @DynamoDBIndexHashKey(globalSecondaryIndexName = MEDIUM_INDEX, attributeName = "primaryMedium")
    public Exhibition.MEDIUM getPrimaryMedium() {
        return primaryMedium;
    }
    @DynamoDBTypeConverted(converter = MovementEnumConverter.class)
    @DynamoDBAttribute(attributeName = "primaryMovement")
    public Exhibition.MOVEMENT getPrimaryMovement() {
        return primaryMovement;
    }

    public void setPrimaryMedium(Exhibition.MEDIUM primaryMedium) {
        this.primaryMedium = primaryMedium;
    }

    public void setPrimaryMovement(Exhibition.MOVEMENT primaryMovement) {
        this.primaryMovement = primaryMovement;
    }

    public void setMedia(List<Exhibition.MEDIUM> media) {
        this.media = media;
    }
    @DynamoDBTypeConverted(converter = MovementListConverter.class)
    @DynamoDBAttribute(attributeName = "movements")
    public List<Exhibition.MOVEMENT> getMovements() {
        return movements;
    }


    public void setMovements(List<Exhibition.MOVEMENT> movements) {
        this.movements = movements;
    }
    @DynamoDBAttribute(attributeName = "tags")
    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    @DynamoDBAttribute(attributeName = "art")
    public Map<String, String> getArt() {
        return art;
    }

    public void setArt(Map<String, String> art) {
        this.art = art;
    }
    @DynamoDBAttribute(attributeName = "imageUrl")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    @DynamoDBAttribute(attributeName = "imageAttribution")
    public String getImageAttribution() {
        return imageAttribution;
    }

    public void setImageAttribution(String imageAttribution) {
        this.imageAttribution = imageAttribution;
    }
    @DynamoDBAttribute(attributeName = "exhibitions")
    public List<String> getExhibitions() {
        return exhibitions;
    }

    public void setExhibitions(List<String> exhibitions) {
        this.exhibitions = exhibitions;
    }
    @DynamoDBAttribute(attributeName = "birthCountry")
    public String getBirthCountry() {
        return birthCountry;
    }
    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return artistName.equals(artist.artistName) && birthYear.equals(artist.birthYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistName, birthYear);
    }
}
