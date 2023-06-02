import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the MusicPlaylistService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class MusicPlaylistClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'getUserEmail', 'getTokenOrThrow',
            'getWishlist','createWishlist', 'deleteWishlist','addExhibitionToWishlist','removeExhibitionFromWishlist',
            'getExhibition','searchExhibitionsByCity','searchExhibitionsByMovement','searchExhibitionsByDate',
            'searchExhibitionsByMedium', 'searchExhibitionsByCityAndMedium'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }


    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }
    async getUserEmail() {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserEmail();
        } catch (error) {
            console.error(error);
        }
    }

    /**
     * Gets the wishlist for the authenticated user.
     * @param listName is the name of the wishlist.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The wishlist's metadata.
     */
    async getWishlist(listName, errorCallback) {
        try {
        const email = await this.getUserEmail();
console.log(email);
console.log("getWishlist client");
            const token = await this.getTokenOrThrow("Only authenticated users can view wishlists.");
console.log(listName);
console.log(token);
            const response = await this.axiosClient.get(`wishlists/${email}/${listName}`, {
            headers:{
            Authorization:`Bearer ${token}`
            }});


console.log("data response");
            return response.data.wishlist;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    /**
     * Gets a requested exhibition for the authenticated user.
     * @param cityCountry is the name of the location.
     * @param exhibitionName is the name of the exhibition.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The exhibition's metadata.
     */
    async getExhibition(cityCountry, exhibitionName, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can view exhibitions.");
console.log(exhibitionName);
console.log(token);
            const response = await this.axiosClient.get(`exhibitions/${cityCountry}/${exhibitionName}`, {
            headers:{
            Authorization:`Bearer ${token}`
            }});
console.log("data response");
            return response.data.exhibition;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    /**
     * Get the songs on a given playlist by the playlist's identifier.
     * @param id Unique identifier for a playlist
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of songs on a playlist.
     */
//    async getPlaylistSongs(id, errorCallback) {
//        try {
//            const response = await this.axiosClient.get(`playlists/${id}/songs`);
//            return response.data.songList;
//        } catch (error) {
//            this.handleError(error, errorCallback)
//        }
//    }

     async createWishlist(listName, description, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create playlists.");

            const response = await this.axiosClient.post(`wishlists`, {
                listName: listName,
                description: description
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.wishlist;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    /**
     * Add an art exhibition to a wishlist.
     * @param list name of the wishlist to add an exhibition to.
     * @param city, country of the exhibition.
     * @param name of the exhibition.
     * @returns The list of exhibitions on a wishlist.
     */
    async addExhibitionToWishlist(listName, cityCountry, exhibitionName, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can add to a wishlist.");

            const email = await this.getUserEmail();
                        console.log(email);
                        console.log("adXWishlist client");
            const response = await this.axiosClient.post(`wishlists/${email}/${listName}/exhibitions`, {
                listName: listName,
                cityCountry: cityCountry,
                exhibitionName: exhibitionName
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });

            return response.data.exhibitions;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    async removeExhibitionFromWishlist(listName, cityCountry, exhibitionName, errorCallback) {
       try {
            const token = await this.getTokenOrThrow("Only authenticated users can change a wishlist.");

            const email = await this.getUserEmail();
console.log(email);
console.log("rmvXWishlist client");
console.log(listName);
console.log(cityCountry);
console.log(exhibitionName);
            const response = await this.axiosClient.put(`wishlists/${email}/${listName}/exhibitions`, {
                email: email,
                listName: listName,
                cityCountry: cityCountry,
                exhibitionName: exhibitionName
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
console.log("response.data received");
console.log(response.data);
            return response.data.wishlist;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    async deleteWishlist(listName, errorCallback) {

    console.log("deleteWishlist client");
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can change a wishlist.");
            const email = await this.getUserEmail();
 console.log(email);
  console.log(token);
  console.log(listName);
            const response = await this.axiosClient.delete(`wishlists/${email}/${listName}`, {
               headers: {
                  Authorization: `Bearer ${token}`
               }});
console.log(response.data.wishlistModel);
            return response.data.wishlistModel
        } catch (error) {
        this.handleError(error, errorCallback)
        }
    }

    /**
     * Search for a song.
     * @param criteria A string containing search criteria to pass to the API.
     * @returns The playlists that match the search criteria.
     */
    async search(criteria, errorCallback) {
        try {
            const queryParams = new URLSearchParams({ q: criteria })
            const queryString = queryParams.toString();

            const response = await this.axiosClient.get(`playlists/search?${queryString}`);

            return response.data.playlists;
        } catch (error) {
            this.handleError(error, errorCallback)
        }

    }
    async searchExhibitionsByCity(cityCountry, errorCallback) {
        try {
            const response = await this.axiosClient.get(`exhibitions/search/city/${cityCountry}`);
            return response.data.exhibitions;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    async searchExhibitionsByMovement(movement, errorCallback) {
        try {
            const response = await this.axiosClient.get(`exhibitions/search/movement/${movement}`);
            return response.data.exhibitions;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    async searchExhibitionsByDate(startDate, endDate, errorCallback) {
        try {
            const response = await this.axiosClient.get(`exhibitions/search/date/${startDate}/${endDate}`);
            return response.data.exhibitions;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    async searchExhibitionsByMedium(medium, errorCallback) {
        try {
            const response = await this.axiosClient.get(`exhibitions/search/medium/${medium}`);
            return response.data.exhibitions;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    async searchExhibitionsByCityAndMedium(cityCountry, medium, errorCallback) {
        try {
            const response = await this.axiosClient.get(`exhibitions/search/city/${cityCountry}/medium/${medium}`);
            return response.data.exhibitions;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}
