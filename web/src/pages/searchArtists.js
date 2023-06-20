import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import Footer from '../components/footer';
import ViewDetails from '../components/viewDetails';

import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the artists page of the website.
 */
class SearchArtists extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'clientLoaded','getArtist','viewSearchResults','viewExhibitionDetails',
            'recommendArtists','viewRecommendedArtists'], this);
        this.dataStore = new DataStore();
        this.dataStoreView = new DataStore();
        this.dataStoreView.addChangeListener(this.viewSearchResults);
        this.dataStore.addChangeListener(this.recommendArtists);
        this.dataStoreDetails = new DataStore();
        this.dataStoreRecommendedArtists = new DataStore();
        this.dataStoreRecommendedArtists.addChangeListener(this.viewRecommendedArtists);
        this.header = new Header(this.dataStore);
        this.footer = new Footer(this.dataStore);
        this.viewDetails = new ViewDetails(this.dataStoreViewDetails);
    }
     /**
       * Add an event listener to the search button. Add the header and footer to the page and load the Client.
       */
    mount() {
        document.getElementById('artist-search').addEventListener('click', this.getArtist);

        this.header.addHeaderToPage();
        this.footer.addFooterToPage();
        this.client = new MusicPlaylistClient();
        this.clientLoaded();
     }
     /**
       * Loads artist name from home page into the name input field to 'invite' user to search exhibitions by artist.
       */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams != null) {
        const artistNameRedirect = urlParams.get('artistName');
        document.getElementById('artist-name').value = artistNameRedirect;
        }
     }
     /**
       * Searches the exhibitions table for exhibitions featuring the requested artist. Runs due to an event listener on the search button.
       */
    async getArtist(evt) {
        evt.preventDefault();
//Hides error messaging
        const errorMessageDisplay = document.getElementById('error-message-artist');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
//Hides previous search results
        const resultContainer = document.getElementById('view-details-container');
        resultContainer.classList.add('hidden');
        document.getElementById('view-search-results-container').classList.add('hidden');
//Getting artist name from user input
        const artistName = document.getElementById('artist-name').value;
                if(!artistName) { return; }
//        if(artistName === null || artistName === "") { return; }
//Acknowledging search to user
        const button = document.getElementById('artist-search');
             button.innerText = 'Searching...';


        const exhibitions = await this.client.searchExhibitionsByArtist(artistName, (error) => {
              errorMessageDisplay.innerText = `${error.message}`;
              errorMessageDisplay.classList.remove('hidden');
        });
        //puts the exhibitions list into the datastore
        this.dataStoreView.set('exhibitions', exhibitions);

        //User experience confirms activity with messaging
        button.innerText = 'Complete';

        setTimeout(function() {
             button.innerText = 'Search Another Artist';
             let input = document.getElementById('artist-search-form');
             input.reset();
             }, 500);
//        /*
//        This method is to get the artist recommended
//        The scan version will become an admin tool
//        */
//
//        const artistList = await this.client.getArtist(artistName, (error) => {
//              errorMessageDisplay.innerText = ` ${error.message}`;
//              button.innerText = 'Search';
//              });
//
//                /*This assumes that the first hit on the list is the one that the user wants,
//                *eventually would implement a dropdown menu with birth year choices. Application table built for that
//                *contingency. However, there is no current need for the implementation. Design is intended to be
//                *flexible but responsive to existent and changing business needs.
//                */
//        const artist = artistList[0];
//        this.recommendArtists(artistList[0]);
    this.recommendArtists(artistName);

    }
    async recommendArtists(artistName) {

        const similarArtists = await this.client.getRecommendedArtists(artistName, (error) => {
                const errorMessageDisplay = document.getElementById('error-message-artist');
                //message stays hidden, but remains for future debugging
               // errorMessageDisplay.classList.add('hidden');
               // errorMessageDisplay.innerText = `Error: ${error.message}`;
            });
console.log(similarArtists+" SIMILAR ARTISTS!!");
            if (!similarArtists) {
                return;
            } else {
                this.dataStoreRecommendedArtists.set('similarArtists', similarArtists);
            }

    }
    async viewRecommendedArtists() {
        const artists = this.dataStoreRecommendedArtists.get('similarArtists');
console.log(artists);
        if (artists === null) { return;}
        if(!artists) {return;}
        if(artists.length === 0) {return;}

        const recommendArtistsContainer = document.getElementById('recommended-artists-container');
        recommendArtistsContainer.classList.remove('hidden');

        let artist;
        let resultHtml = '';

        var artistsSlice = artists.slice(0,3);

        for(artist of artistsSlice) {
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


        const name = exhibition.exhibitionName;

        const institution = exhibition.institution;
        if(institution == null){institution = "";}

        const address = exhibition.address;

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

        exhibitionList.forEach(ex => {
            ex.addEventListener('click', this.viewExhibitionDetails);
        });

    }
    /*
     Exhibition details to page
    */
    async viewExhibitionDetails(evt) {
        const exName = evt.target.getAttribute('name');

        const result = this.dataStoreDetails.get(exName);
        if(!result) {return;}
        if(result === null) {return;}
        this.viewDetails.addExhibitionDetailsToPage(result);

//        const resultContainer = document.getElementById('view-details-container');
//        resultContainer.classList.remove('hidden');
//        /*
//        Name to html
//        */
//        document.getElementById('exhibition-name').innerText = result.exhibitionName;
//        /*
//        Description to html
//        */
//        if (result.description != null) {
//            const descriptionField = document.getElementById('view-exhibition-description');
//            descriptionField.classList.remove('hidden');
//            document.getElementById('view-exhibition-description').innerText = result.description;
//        } else {
//            document.getElementById('view-exhibition-description').innerText = "";}
//        /*
//        Institution to html
//        */
//        if (result.institution != null) {
//                const attributeField = document.getElementById('view-institution');
//                attributeField.classList.remove('hidden');
//                attributeField.innerText = result.institution;
//        } else {
//            document.getElementById('view-institution').innerText = "";
//            }
//        /*
//        Address to html
//        */
//        let addressHTML='';
//
//        if (result.address != null) {
//                const attributeField = document.getElementById('view-exhibition-address');
//                attributeField.classList.remove('hidden');
//                attributeField.innerText = result.address;
//                const addressString = result.address;
//                addressHTML =`<span class="address"><a href= "https://www.google.com/maps/place/${addressString}"> ${addressString}</a></span>`;
//                attributeField.innerHTML = addressHTML;
//
//        }
//        /**
//         * DATES
//         */
//        try {
//           const startDate = JSON.parse(result.startDate);
//console.log(startDate);
//           const starDateDateObjParsed = new Date(startDate.year, startDate.month-1, startDate.day);
//console.log(starDateDateObjParsed);
//           const formattedDate = starDateDateObjParsed.toLocaleDateString('en-US', { month: 'long', day: 'numeric', year: 'numeric' });
//console.log("formattedDate: " + formattedDate)
//            const attributeField = document.getElementById('view-exhibition-dates');
//            attributeField.classList.remove('hidden');
//            attributeField.innerText = formattedDate;
//            this.dataStore.set('startDate', formattedDate);
//        } catch (error) {
//                    const attributeField = document.getElementById('view-exhibition-dates');
//                    attributeField.classList.remove('hidden');
//                    attributeField.innerText = "TBD";
//        }
//        try {
//            const endDate = JSON.parse(result.endDate);
//
//            const endDateDateObjParsed = new Date(endDate.year, endDate.month-1, endDate.day);
//
//            const formattedEndDate = endDateDateObjParsed.toLocaleDateString('en-US', { month: 'long', day: 'numeric', year: 'numeric' });
//
//            const attributeField = document.getElementById('view-exhibition-dates');
//
//            attributeField.innerText = this.dataStore.get('startDate') + " - " + formattedEndDate;
//
//        } catch (error) {
//        }
//
//        /*
//        Artists
//        */
//        if (result.artists != null) {
//        let resultHtml = '';
//        let artist;
//        for(artist of result.artists) {
//
//            resultHtml+= `
//                <ol class = "artist">
//                    <span class = "artist-name" >${artist}  </span>
//                </ol>
//                <br>
//            `;
//        }
//
//        document.getElementById('artists').innerHTML = resultHtml;
//        } else { document.getElementById('artists').innerHTML = "TBD";}
//        /*
//        Media
//        */
//        if (result.media != null) {
//            const attributeField = document.getElementById('view-institution');
//            attributeField.classList.remove('hidden');
//
//        let resultMediaHtml = '';
//        let medium;
//        for(medium of result.media) {
//
//            resultMediaHtml += `
//                <ol class = "media">
//                    <span class = "medium" >${medium}  </span>
//                </ol>
//                <br>
//            `;
//        }
//
//        document.getElementById('media').innerHTML = resultMediaHtml;
//
//        } else { document.getElementById('media').innerHTML = "";}
//        /*
//        Image and attribution
//        */
//console.log(result.imageUrl);
//console.log(result);
//        if(result.imageUrl != null) {
//            const url = result.imageUrl;
//            const urlAttribution = result.imageAttribution;
//
//            let urlHtml = `<img src=${url} alt="Image description" width="500" height="300"> <br>
//                <span id = "attribution" >${urlAttribution}</span>
//            `;
//
//        document.getElementById("image").innerHTML =
//            urlHtml;
//
//        }

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