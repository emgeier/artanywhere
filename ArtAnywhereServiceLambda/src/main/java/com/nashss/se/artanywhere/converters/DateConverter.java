package com.nashss.se.artanywhere.converters;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.List;

public class DateConverter implements DynamoDBTypeConverter<String, LocalDate> {
    private static final Gson GSON = new Gson();
    @Override
    public String convert(LocalDate date) {

        DateTimeFormatter formatterISO = DateTimeFormatter.ISO_DATE;

        System.out.println("DATEISO String" +date.format(formatterISO));
        return date.format(formatterISO);

    }

    @Override
    public LocalDate unconvert(String object) {

        DateTimeFormatter formatterISO = DateTimeFormatter.ISO_DATE;
        System.out.println("DATEISO LocalDate " +LocalDate.parse(object, formatterISO));
        return LocalDate.parse(object, formatterISO);
    }

    public String convertToWords(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        return date.format(formatter);
    }
    public LocalDate convertFromWords(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        return LocalDate.parse(date, formatter);
    }
    public LocalDate convertFromNumberString(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        return LocalDate.parse(date, formatter);
    }
    public String convertToJson(LocalDate date) {
        return GSON.toJson(date);
    }
    public LocalDate convertFromJson(String date) {
        return GSON.fromJson(date, LocalDate.class);
    }

}
