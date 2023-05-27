package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.WishlistModel;

public class CreateWishlistResult {

    private final WishlistModel wishlist;
    private CreateWishlistResult(WishlistModel wishlist) {
        this.wishlist = wishlist;
    }

    public WishlistModel getWishlist() {
        return wishlist;
    }

    @Override
    public String toString() {
        return "CreateWishlistResult{" +
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
        public CreateWishlistResult build() {
            return new CreateWishlistResult(wishlist);
        }
    }
}
