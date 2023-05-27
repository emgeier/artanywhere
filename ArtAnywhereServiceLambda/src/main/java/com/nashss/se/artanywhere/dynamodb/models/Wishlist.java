package com.nashss.se.artanywhere.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.nashss.se.artanywhere.converters.ExhibitionsListConverter;

import java.util.List;
import java.util.Map;
@DynamoDBTable(tableName = "wishlists")
public class Wishlist {
    private String listName;
    private String email;
    private List<String> exhibitions;
    private String description;
    private List<Exhibition> exhibitionsList;


    @DynamoDBHashKey(attributeName = "email")
    public String getEmail() {
        return email;
    }
    @DynamoDBRangeKey(attributeName = "listName")
    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBAttribute(attributeName = "exhibitions")
    public List<String> getExhibitions() {
        return exhibitions;
    }

    public void setExhibitions(List<String> exhibitions) {
        this.exhibitions = exhibitions;
    }
    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //  @DynamoDBTypeConverted(converter = ExhibitionsListConverter.class)
    @DynamoDBIgnore
    public List<Exhibition> getExhibitionsList() {
        return exhibitionsList;
    }

    public void setExhibitionsList(List<Exhibition> exhibitionsList) {
        this.exhibitionsList = exhibitionsList;
    }

}
