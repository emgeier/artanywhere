package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.WishlistModel;

public class GetWishlistResult {

    private final WishlistModel wishlist;

    public GetWishlistResult(WishlistModel wishlist) {
        this.wishlist = wishlist;
    }

    public WishlistModel getWishlist() {
        return wishlist;
    }

    @Override
    public String toString() {
        return "GetWishlistResult{" +
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

        public GetWishlistResult build() {
            return new GetWishlistResult(wishlist);
        }
    }
}
