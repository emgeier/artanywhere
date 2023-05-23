package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.WishlistModel;

public class CreateWishlistResult {

    private final WishlistModel wishlistModel;
    private CreateWishlistResult(WishlistModel wishlistModel) {
        this.wishlistModel = wishlistModel;
    }

    public WishlistModel getWishlistModel() {
        return wishlistModel;
    }

    @Override
    public String toString() {
        return "CreateWishlistResult{" +
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
        public CreateWishlistResult build() {
            return new CreateWishlistResult(wishlistModel);
        }
    }
}
