package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.WishlistModel;

public class GetWishlistResult {

    private final WishlistModel wishlistModel;

    public GetWishlistResult(WishlistModel wishlistModel) {
        this.wishlistModel = wishlistModel;
    }

    public WishlistModel getWishlistModel() {
        return wishlistModel;
    }

    @Override
    public String toString() {
        return "GetWishlistResult{" +
                "wishlistModel=" + wishlistModel +
                '}';
    }
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private WishlistModel wishlistModel;

        public Builder withWishlistModel(WishlistModel wishlistModel) {
            this.wishlistModel = wishlistModel;
            return this;
        }

        public GetWishlistResult build() {
            return new GetWishlistResult(wishlistModel);
        }
    }
}
