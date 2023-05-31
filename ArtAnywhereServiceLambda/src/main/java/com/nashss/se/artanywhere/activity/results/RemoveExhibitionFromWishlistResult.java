package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.WishlistModel;

public class RemoveExhibitionFromWishlistResult {
    private final WishlistModel wishlist;

    public RemoveExhibitionFromWishlistResult(WishlistModel wishlistModel) {
        this.wishlist = wishlistModel;
    }

    public WishlistModel getWishlistModel() {
        return wishlist;
    }

    @Override
    public String toString() {
        return "RemoveExhibitionFromWishlistResult{" +
                "wishlistModel=" + wishlist +
                '}';
    }
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private WishlistModel wishlist;
        public Builder withWishlistModel(WishlistModel wishlistModel) {
            this.wishlist = wishlistModel;
            return this;
        }
        public RemoveExhibitionFromWishlistResult build() {
           return new RemoveExhibitionFromWishlistResult(wishlist);
        }
    }
}
