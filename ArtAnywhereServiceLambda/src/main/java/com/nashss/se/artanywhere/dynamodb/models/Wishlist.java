package com.nashss.se.artanywhere.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.nashss.se.artanywhere.converters.ExhibitionsListConverter;

import java.util.List;
@DynamoDBTable(tableName = "wishlists")
public class Wishlist {
    private String listName;
    private String email;
    private List<Exhibition> exhibitions;
    private String description;


    @DynamoDBRangeKey(attributeName = "listName")
    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
    @DynamoDBHashKey(attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @DynamoDBTypeConverted(converter = ExhibitionsListConverter.class)
    @DynamoDBAttribute(attributeName = "exhibitions")
    public List<Exhibition> getExhibitions() {
        return exhibitions;
    }

    public void setExhibitions(List<Exhibition> exhibitions) {
        this.exhibitions = exhibitions;
    }
    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
