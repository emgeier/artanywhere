package com.nashss.se.artanywhere.converters;

import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import com.nashss.se.artanywhere.models.ExhibitionModel;
import com.nashss.se.artanywhere.models.WishlistModel;


import java.util.ArrayList;
import java.util.List;

public class ModelConverter {
    public WishlistModel toWishlistModel(Wishlist wishlist) {

        return WishlistModel.builder()
                .withEmail(wishlist.getEmail())
                .withListName(wishlist.getListName())
                .withDescription(wishlist.getDescription())
                .withExhibitions(wishlist.getExhibitions())
                .build();
    }
    public ExhibitionModel toExhibitionModel(Exhibition exhibition) {
        return ExhibitionModel.builder()
                .withCityCountry(exhibition.getCityCountry())
                .withName(exhibition.getName())
                .build();
    }
    public List<ExhibitionModel> toExhibitionModelList(List<Exhibition> exhibitions) {
        List<ExhibitionModel> exhibitionsModelsList = new ArrayList<>();
        for(Exhibition exhibition: exhibitions) {
            exhibitionsModelsList.add(toExhibitionModel(exhibition));
        }
        return exhibitionsModelsList;
    }
}
