package com.nashss.se.artanywhere.activity.results;

import com.nashss.se.artanywhere.models.WishlistModel;

public class DeleteWishlistResult {
    private final WishlistModel wishlist;

    public DeleteWishlistResult(WishlistModel wishlistModel) {
        this.wishlist = wishlistModel;
    }

    public WishlistModel getWishlistModel() {
        return wishlist;
    }

    @Override
    public String toString() {
        return "DeleteWishlistResult{" +
                "wishlistModel=" + wishlist +
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
        public DeleteWishlistResult build() {
            return new DeleteWishlistResult(wishlistModel);
        }
    }
}
