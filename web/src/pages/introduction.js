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
        this.bindClassMethods(['mount'], this);
        this.dataStore = new DataStore();

        this.header = new Header(this.dataStore);
    }
     /**
       * Add the header to the page and load the Client.
       */
    mount() {

//         document.getElementById('category-search').addEventListener('click', this.searchByMovement);
//         document.getElementById('medium-search').addEventListener('click', this.searchByMedium);
//         document.getElementById('city-medium-search').addEventListener('click', this.searchByCityAndMedium);
//         document.getElementById('date-search').addEventListener('click', this.searchByDate);
        this.header.addHeaderToPage();
        this.client = new MusicPlaylistClient();
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