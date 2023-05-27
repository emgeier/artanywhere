package com.nashss.se.artanywhere.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.nashss.se.artanywhere.converters.DateConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Java object corresponding to a record on the exhibitions table.
 */
@DynamoDBTable(tableName = "events")
public class Exhibition {
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

    public enum MEDIUM {
        PAINTING, GRAFFITI, SCULPTURE, FILM, CERAMICS, PHOTOGRAPHY
    }
    public enum MOVEMENT {
        IMPRESSIONISM, SURREALISM, POP_ART, RENAISSANCE
    }
    @DynamoDBHashKey(attributeName = "cityCountry")
    public String getCityCountry() {
        return cityCountry;
    }


    @DynamoDBRangeKey(attributeName = "exhibitionName")
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
    public LocalDate getEndDate() {

        return endDate;
    }
    public void setCityCountry(String cityCountry) {

        this.cityCountry = cityCountry;
    }
    public void setExhibitionName(String name) {
        this.exhibitionName = name;
    }

    public void setInstitution(String institution) {

        this.institution = institution;
    }
    public void setStartDate(LocalDate startDate) {

        this.startDate = startDate;
    }
    public void setEndDate(LocalDate endDate) {

        this.endDate = endDate;
    }
    @DynamoDBAttribute(attributeName = "address")
    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {

        this.address = address;
    }
    @DynamoDBAttribute(attributeName = "tags")
    public List<String> getTags() {
        if (tags == null) {
            return new ArrayList<>();
        }
        return tags;
    }

    public void setTags(List<String> tags) {

        this.tags = tags;
    }
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "media")
    public List<MEDIUM> getMedia() {

        return media;
    }
    public void setMedia(List<MEDIUM> media) {

        this.media = media;
    }
    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "movement")
    public MOVEMENT getMovement() {

        return movement;
    }
    public void setMovement(MOVEMENT movement) {
        this.movement = movement;
    }
    @DynamoDBAttribute(attributeName = "artists")
    public List<String> getArtists() {
        return artists;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }
    @DynamoDBAttribute(attributeName = "art")
    public List<String> getArt() {
        return art;
    }

    public void setArt(List<String> art) {
        this.art = art;
    }
    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
