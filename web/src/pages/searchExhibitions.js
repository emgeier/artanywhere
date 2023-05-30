import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the exhibitions page of the website.
 */
class SearchExhibitions extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount','searchByCity', 'viewSearchResults'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.viewSearchResults);
        this.header = new Header(this.dataStore);
    }
     /**
       * Add the header to the page and load the MusicPlaylistClient.
       */
     mount() {
         document.getElementById('city-search').addEventListener('click', this.searchByCity);
         this.header.addHeaderToPage();
         this.client = new MusicPlaylistClient();
     }


     async searchByCity(evt) {
         evt.preventDefault();

         const errorMessageDisplay = document.getElementById('error-message');
         errorMessageDisplay.innerText = ``;
         errorMessageDisplay.classList.add('hidden');

         const button = document.getElementById('city-search');
         button.innerText = 'Loading...';

         const cityCountry = document.getElementById('city-name').value;

         const exhibitions =  await this.client.searchExhibitionsByCity(cityCountry, (error) => {
              errorMessageDisplay.innerText = `Error: ${error.message}`;
              errorMessageDisplay.classList.remove('hidden');
                                       });



        this.dataStore.set('exhibitions', exhibitions);
console.log(exhibitions);

        button.innerText = 'Complete';
        setTimeout(function() {
            button.innerText = 'Search';
            let wishlistInput = document.getElementById('city-search-form');
            wishlistInput.reset();
        }, 800);
    }
    async viewSearchResults() {

        const exhibitions = this.dataStore.get('exhibitions');
        console.log(exhibitions);
        if(exhibitions == null) {return;}
        const resultContainer = document.getElementById('view-search-results-container');
        resultContainer.classList.remove('hidden');
        let exhibition;
        let resultHtml = '';
        for(exhibition of exhibitions) {
        const name = exhibition.exhibitionName;
        console.log(name);
            resultHtml+= `
        <div class = "card">
            <h4> Name: <span class="exhibition-name" id = "view-exhibition-name">${name}</span></h4>
            <h4> Institution:   <span class="exhibition-details hidden" id = "view-institution"></span></h4>
            <h4> Address: <span class="exhibition-details hidden" id = "view-exhibition-address"></span></h4>
            <h4> Description:  <span class="exhibition-details hidden" id = "view-exhibition-description"></span></h4>
            <h4> Artists: </h4>
            <ol class = "artists" id="artists"></ol>
            <h4> Dates:  <span class="exhibition-details hidden" id = "view-exhibition-dates"></span></h4>
            <h4> Media: <span class="exhibition-details hidden" id = "view-exhibition-media"></span></h4>
            <ol class = "artists" id="media"></ol>
            <h4><span class = "exhibition-details hidden" id = "exhibition-image"></span></h4>
            <br>
        `;
        }
        document.getElementById('exhibitions').innerHTML = resultHtml;
    }
}
/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
console.log("main");
    const searchExhibitions = new SearchExhibitions();
    searchExhibitions.mount();
};

window.addEventListener('DOMContentLoaded', main);