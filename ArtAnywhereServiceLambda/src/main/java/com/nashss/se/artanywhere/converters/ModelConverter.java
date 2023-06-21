package com.nashss.se.artanywhere.converters;

import com.nashss.se.artanywhere.dynamodb.models.Artist;
import com.nashss.se.artanywhere.dynamodb.models.Exhibition;
import com.nashss.se.artanywhere.dynamodb.models.Wishlist;
import com.nashss.se.artanywhere.models.ArtistModel;
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
                .withExhibitionName(exhibition.getExhibitionName())
                .withAddress(exhibition.getAddress())
                .withArt(exhibition.getArt())
                .withArtists(exhibition.getArtists())
                .withDescription(exhibition.getDescription())
                .withInstitution(exhibition.getInstitution())
                .withMedia(exhibition.getMedia())
                .withMovement(exhibition.getMovement())
                .withTags(exhibition.getTags())
                .withStartDate(exhibition.getStartDate())
                .withEndDate(exhibition.getEndDate())
                .withImageUrl(exhibition.getImageUrl())
                .withImageAttribution(exhibition.getImageAttribution())
                .build();
    }
    public List<ExhibitionModel> toExhibitionModelList(List<Exhibition> exhibitions) {
        List<ExhibitionModel> exhibitionsModelsList = new ArrayList<>();
        for(Exhibition exhibition: exhibitions) {
            exhibitionsModelsList.add(toExhibitionModel(exhibition));
        }
        return exhibitionsModelsList;
    }
    public List<ExhibitionModel> toExhibitionModelListOfRecommendations(List<Exhibition> exhibitions, String exhibitionName) {
        List<ExhibitionModel> exhibitionsModelsList = new ArrayList<>();
        for(Exhibition exhibition: exhibitions) {
            if(exhibition.getExhibitionName().equals(exhibitionName)) {continue;}
            exhibitionsModelsList.add(toExhibitionModel(exhibition));
        }
        return exhibitionsModelsList;
    }
    public ArtistModel toArtistModel(Artist artist) {
        return ArtistModel.builder()
                .withArtistName(artist.getArtistName())
                .withBirthYear(artist.getBirthYear())
                .withDeathYear(artist.getDeathYear())
                .withArt(artist.getArt())
                .withMedia(artist.getMedia())
                .withMovements(artist.getMovements())
                .withTags(artist.getTags())
                .withImageUrl(artist.getImageUrl())
                .withImageAttribution(artist.getImageAttribution())
                .build();
    }
    public List<ArtistModel> toArtistModelList(List<Artist> artistList) {
        List<ArtistModel> artistModels = new ArrayList<>();

        for(Artist artist: artistList ) {
            artistModels.add(toArtistModel(artist));
        }

        return artistModels;
    }

}
