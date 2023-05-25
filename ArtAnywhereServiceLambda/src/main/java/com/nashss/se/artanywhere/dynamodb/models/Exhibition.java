package com.nashss.se.artanywhere.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Java object corresponding to a record on the exhibitions table.
 */
@DynamoDBTable(tableName = "exhibitions")
public class Exhibition {
    private String cityCountry;
    private String name;
    private String institution;
    private Date startDate;
    private Date endDate;
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

    public void setCityCountry(String cityCountry) {

        this.cityCountry = cityCountry;
    }
    @DynamoDBRangeKey(attributeName = "name")
    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @DynamoDBAttribute(attributeName = "institution")
    public String getInstitution() {

        return institution;
    }

    public void setInstitution(String institution) {

        this.institution = institution;
    }
    @DynamoDBTypeConvertedEpochDate()
    @DynamoDBAttribute(attributeName = "startDate")
    public Date getStartDate() {

        return startDate;
    }

    public void setStartDate(Date startDate) {

        this.startDate = startDate;
    }
    @DynamoDBTypeConvertedEpochDate()
    @DynamoDBAttribute(attributeName = "endDate")
    public Date getEndDate() {

        return endDate;
    }

    public void setEndDate(Date endDate) {

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
