import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import Footer from '../components/footer';

import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the exhibitions page of the website.
 */
class SearchArtists extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount','getArtist','viewSearchResults','viewExhibitionDetails','recommendArtists','viewRecommendedArtists'], this);
        this.dataStore = new DataStore();
        this.dataStoreView = new DataStore();
        this.dataStoreView.addChangeListener(this.viewSearchResults);
        this.dataStore.addChangeListener(this.recommendArtists);
        this.dataStoreDetails = new DataStore();
        this.dataStoreRecommendedArtists = new DataStore();
        this.dataStoreRecommendedArtists.addChangeListener(this.viewRecommendedArtists);
        this.header = new Header(this.dataStore);
        this.footer = new Footer(this.dataStore);
    }
     /**
       * Add the header to the page and load the Client.
       */
    mount() {
        document.getElementById('artist-search').addEventListener('click', this.getArtist);
//         document.getElementById('category-search').addEventListener('click', this.searchByMovement);
//         document.getElementById('medium-search').addEventListener('click', this.searchByMedium);
//         document.getElementById('city-medium-search').addEventListener('click', this.searchByCityAndMedium);
//         document.getElementById('date-search').addEventListener('click', this.searchByDate);
        this.header.addHeaderToPage();
        this.footer.addFooterToPage();
        this.client = new MusicPlaylistClient();
     }
    async getArtist(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message-artist');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
        const artistName = document.getElementById('artist-name').value;
        if(artistName === null || artistName === "") { return; }
        const button = document.getElementById('artist-search');
             button.innerText = 'Searching...';

console.log(artistName);
   //          document.getElementById('view-search-results-container').classList.add('hidden');
        const exhibitions = await this.client.searchExhibitionsByArtist(artistName, (error) => {
              errorMessageDisplay.innerText = `Error: ${error.message}`;
              errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStoreView.set('exhibitions', exhibitions);
             button.innerText = 'Complete';

             setTimeout(function() {
                  button.innerText = 'Search Another Artist';
                  let input = document.getElementById('artist-search-form');
                  input.reset();
              }, 500);
         //to use once the scan version becomes an admin tool
                const artistList = await this.client.getArtist(artistName, (error) => {
                      errorMessageDisplay.innerText = `Error: ${error.message}`;
                      //errorMessageDisplay.classList.remove('hidden');
                      button.innerText = 'Search';


                });
        console.log(artistList);
        console.log(artistList[0]);
                const artist = artistList[0];

this.dataStore.set('artist', artist);
console.log(this.dataStore.get('artist'));

    }
    async recommendArtists() {
    //when you build the search artist functions
        const artist = this.dataStore.get('artist');
console.log("recommend artist method");
console.log(artist);
        const artistName = artist.artistName;
        if(artist.movements != null) {//for loop through the movements?
            const movement = artist.movements[0];
console.log(movement);
            const similarArtists = await this.client.getRecommendedArtists(artistName, (error) => {
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });
console.log(similarArtists);
            this.dataStoreRecommendedArtists.set('similarArtists', similarArtists);
            } else {return;}
//        } else if (artist.tags != null) {
//            const tags = artist.tags;
//            const similarArtists = await this.client.searchArtistsByTags(tags)
//            this.dataStoreRecommendedArtists.set('similarArtists', similarArtists);
//        } else {
//            const homeland = artist.birthCountry;
//            const similarArtists = await this.client.searchArtistsByTags(tags)
//            this.dataStoreRecommendedArtists.set('similarArtists', similarArtists);


    }
    async viewRecommendedArtists() {
        const artists = this.dataStoreRecommendedArtists.get('similarArtists');
console.log(artists);
        if (artists === null) { return;}

        const recommendArtistsContainer = document.getElementById('recommended-artists-container');
        recommendArtistsContainer.classList.remove('hidden');

        let artist;
        let resultHtml = '';
        for(artist of artists) {
            const artistName = artist.artistName;
            const imageUrl = artist.imageUrl;
            const imageAttribution = artist.imageAttribution;
            const spacing = "           ";

            resultHtml += `

                <a href=#  <span class="artist-name" id="view-artist-name">${artistName}</span>
                <br>
                <img src= "${imageUrl}" alt="Image description" width="500" height="300">
                <br>
                <a href=#  <span class="artist-name-space" id="space">        ${spacing}          </span>

                <br>


            `;
            }
            document.getElementById("recommended-artists").innerHTML = resultHtml;

    }

    async viewSearchResults() {
// to use once the scan becomes an admin tool
//        const artist = this.dataStore.get('artist');
//        const exhibitions = artist.exhibitions;
        const exhibitions = this.dataStoreView.get('exhibitions');
        if(exhibitions == null) {return;}
        const resultContainer = document.getElementById('view-search-results-container');
        resultContainer.classList.remove('hidden');

        let exhibition;
        let resultHtml = '';
        for(exhibition of exhibitions) {

console.log(exhibition);
        const name = exhibition.exhibitionName;

        const institution = exhibition.institution;
console.log(exhibition.institution)
        if(institution == null){institution = "";}

console.log(exhibition.address)

        const address = exhibition.address;


console.log(exhibition.description)

        const description = exhibition.description;


        resultHtml+= `
            <a href=# <span class="exhibition-name" id = "view-exhibition-name" name= "${name}">${name}   :::   ${institution}  </span></a>
            <br>
            <br>
        `;
        this.dataStoreDetails.set(name, exhibition);

        }

        document.getElementById('exhibitions').innerHTML = resultHtml;
        const exhibitionList = document.querySelectorAll('#exhibitions a');
console.log(exhibitionList);

        exhibitionList.forEach(ex => {
            ex.addEventListener('click', this.viewExhibitionDetails);
        });

    }
    /*
     Exhibition details to page
    */
    async viewExhibitionDetails(evt) {
         const exTest = evt.target.getAttribute('name');

         const result = this.dataStoreDetails.get(exTest);

        if(result == null) {return;}

        const resultContainer = document.getElementById('view-details-container');
        resultContainer.classList.remove('hidden');
        /*
        Name to html
        */
        document.getElementById('exhibition-name').innerText = result.exhibitionName;
        /*
        Description to html
        */
        if (result.description != null) {
            const descriptionField = document.getElementById('view-exhibition-description');
            descriptionField.classList.remove('hidden');
            document.getElementById('view-exhibition-description').innerText = result.description;
        } else {
            document.getElementById('view-exhibition-description').innerText = "";}
        /*
        Institution to html
        */
        if (result.institution != null) {
                const attributeField = document.getElementById('view-institution');
                attributeField.classList.remove('hidden');
                attributeField.innerText = result.institution;
        } else {
            document.getElementById('view-institution').innerText = "";
            }
        /*
        Address to html
        */
        let addressHTML='';

        if (result.address != null) {
                const attributeField = document.getElementById('view-exhibition-address');
                attributeField.classList.remove('hidden');
                attributeField.innerText = result.address;
                const addressString = result.address;
                addressHTML =`<span class="address"><a href= "https://www.google.com/maps/place/${addressString}"> ${addressString}</a></span>`;
                attributeField.innerHTML = addressHTML;

        }
        /**
         * DATES
         */
        try {
           const startDate = JSON.parse(result.startDate);
console.log(startDate);
           const starDateDateObjParsed = new Date(startDate.year, startDate.month-1, startDate.day);
console.log(starDateDateObjParsed);
           const formattedDate = starDateDateObjParsed.toLocaleDateString('en-US', { month: 'long', day: 'numeric', year: 'numeric' });
console.log("formattedDate: " + formattedDate)
            const attributeField = document.getElementById('view-exhibition-dates');
            attributeField.classList.remove('hidden');
            attributeField.innerText = formattedDate;
            this.dataStore.set('startDate', formattedDate);
        } catch (error) {
                    const attributeField = document.getElementById('view-exhibition-dates');
                    attributeField.classList.remove('hidden');
                    attributeField.innerText = "TBD";
        }
        try {
            const endDate = JSON.parse(result.endDate);

            const endDateDateObjParsed = new Date(endDate.year, endDate.month-1, endDate.day);

            const formattedEndDate = endDateDateObjParsed.toLocaleDateString('en-US', { month: 'long', day: 'numeric', year: 'numeric' });

            const attributeField = document.getElementById('view-exhibition-dates');

            attributeField.innerText = this.dataStore.get('startDate') + " - " + formattedEndDate;

        } catch (error) {
        }

        /*
        Artists
        */
        if (result.artists != null) {
        let resultHtml = '';
        let artist;
        for(artist of result.artists) {

            resultHtml+= `
                <ol class = "artist">
                    <span class = "artist-name" >${artist}  </span>
                </ol>
                <br>
            `;
        }

        document.getElementById('artists').innerHTML = resultHtml;
        } else { document.getElementById('artists').innerHTML = "TBD";}
        /*
        Media
        */
        if (result.media != null) {
            const attributeField = document.getElementById('view-institution');
            attributeField.classList.remove('hidden');

        let resultMediaHtml = '';
        let medium;
        for(medium of result.media) {

            resultMediaHtml += `
                <ol class = "media">
                    <span class = "medium" >${medium}  </span>
                </ol>
                <br>
            `;
        }

        document.getElementById('media').innerHTML = resultMediaHtml;

        } else { document.getElementById('media').innerHTML = "";}
        /*
        Image and attribution
        */
console.log(result.imageUrl);
console.log(result);
        if(result.imageUrl != null) {
            const url = result.imageUrl;
            const urlAttribution = result.imageAttribution;

            let urlHtml = `<img src=${url} alt="Image description" width="500" height="300"> <br>
                <span id = "attribution" >${urlAttribution}</span>
            `;

        document.getElementById("image").innerHTML =
            urlHtml;

        }

    }
}
/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
console.log("main");
    const searchArtists = new SearchArtists();
    searchArtists.mount();
};

window.addEventListener('DOMContentLoaded', main);