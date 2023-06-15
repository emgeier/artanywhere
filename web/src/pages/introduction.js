import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';

import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the exhibitions page of the website.
 */
class Introduction extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'getExhibitionsByArtist'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
    }
     /**
       * Add the header to the page and load the Client.
       */
    mount() {

         document.getElementById('Brathwaite').addEventListener('click', this.getExhibitionsByArtist);
//         document.getElementById('medium-search').addEventListener('click', this.searchByMedium);
//         document.getElementById('city-medium-search').addEventListener('click', this.searchByCityAndMedium);
//         document.getElementById('date-search').addEventListener('click', this.searchByDate);
        this.header.addHeaderToPage();
        this.client = new MusicPlaylistClient();
     }
     getExhibitionsByArtist(event) {
     window.location.href = `/artists.html?artistName=${"Kwame Brathwaite"}`;
//   /**
//     * When the playlist is updated in the datastore, redirect to the view playlist page.
//     */
//    redirectToViewPlaylist() {
//        const playlist = this.dataStore.get('playlist');
//        if (playlist != null) {
//            window.location.href = `/playlist.html?id=${playlist.id}`;
//        }
    }
     }

}
/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
console.log("main");
    const introduction = new Introduction();
    introduction.mount();
};

window.addEventListener('DOMContentLoaded', main);