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

        if(exhibitions == null) {return;}
        const resultContainer = document.getElementById('view-search-results-container');
        resultContainer.classList.remove('hidden');

        let exhibition;
        let resultHtml = '';
        for(exhibition of exhibitions) {

console.log(exhibition);
        const name = exhibition.exhibitionName;
//        const institution = ' ';
//        const address = " ";
//        const description = " ";


        const institution = exhibition.institution;
console.log(exhibition.institution)
        if(institution == null){institution = "";}

console.log(exhibition.address)

        const address = exhibition.address;


console.log(exhibition.description)

        const description = exhibition.description;


        resultHtml+= `


            <a href=# <span class="exhibition-name" id = "view-exhibition-name">${name}   :::   ${institution}  </span></a>
            <br>
            <br>
        `;
        }
        document.getElementById('exhibitions').innerHTML = resultHtml;

    }
    async createExhibitionDetailsCard(exhibition) {
//                <h4> Institution:   <span id = "view-institution">${institution}</span></h4>
//                <h4> Address: <span class="exhibition-details" id = "view-exhibition-address">${address}</span></h4>
//                <h4> Description:  <span class="exhibition-details " id = "view-exhibition-description">${description}</span></h4>
//                <h4> Dates:  <span class="exhibition-details " id = "view-exhibition-dates"></span></h4>
//                <h4> Media: <span class="exhibition-details " id = "view-exhibition-media"></span></h4>
//          <ol class = "artists" id="media"></ol>
//                <h4><span class = "exhibition-details " id = "exhibition-image"></span></h4>
//                if(exhibition.imageUrl != null ){
//                    const imageUrl = exhibition.imageUrl;
//                    const urlAttribution = exhibition.imageAttribution;
//                    let urlHtml = `<img src=${imageUrl} alt="Image description" width="500" height="300"> <br>
//                <span id = "attribution" >${urlAttribution}</span>
//            `;
//
//        document.getElementById("exhibition-image").innerHTML =
//            urlHtml;
//
//                }
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