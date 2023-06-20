import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import Footer from '../components/footer';
import ViewDetails from '../components/viewDetails';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the exhibitions page of the website.
 */
class SearchExhibitions extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount','clientLoaded','searchByCity', 'searchByMovement','searchByMedium',
        'searchByCityAndMedium','searchByDate', 'searchByCityAndDate','viewSearchResults','viewExhibitionDetails',
        'hidePreviousSearchDetails' ], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.viewSearchResults);
        this.dataStoreDetails = new DataStore();
        this.header = new Header(this.dataStore);
        this.footer = new Footer(this.dataStore);
        this.viewDetails = new ViewDetails(this.dataStoreDetails);

    }
     /**
       * Add event listeners for running the search functions when a search button is pressed.
       * Add the header and footer to the page and load the Client.
       */
     mount() {
         document.getElementById('city-search').addEventListener('click', this.searchByCity);
         document.getElementById('category-search').addEventListener('click', this.searchByMovement);
         document.getElementById('medium-search').addEventListener('click', this.searchByMedium);
         document.getElementById('city-medium-search').addEventListener('click', this.searchByCityAndMedium);
         document.getElementById('date-search').addEventListener('click', this.searchByDate);
         document.getElementById('city-date-search').addEventListener('click', this.searchByCityAndDate);

         document.getElementById('city-search').addEventListener('click', this.hidePreviousSearchDetails);
         document.getElementById('category-search').addEventListener('click', this.hidePreviousSearchDetails);
         document.getElementById('medium-search').addEventListener('click', this.hidePreviousSearchDetails);
         document.getElementById('city-medium-search').addEventListener('click', this.hidePreviousSearchDetails);
         document.getElementById('date-search').addEventListener('click', this.hidePreviousSearchDetails);
         document.getElementById('city-date-search').addEventListener('click', this.hidePreviousSearchDetails);
         this.clientLoaded();

         this.header.addHeaderToPage();
         this.footer.addFooterToPage();
         this.client = new MusicPlaylistClient();
     }
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams != null) {
        const redirectInput = urlParams.get('query');
 console.log(redirectInput);
        document.getElementById('city-name').value = redirectInput;

        const redirectName = urlParams.get('exhibitionName');
console.log(redirectName);
            if(redirectName != null) {
                this.viewDetails.getExhibitionDetailsForPage(redirectName, redirectInput);
console.log("***");

            }
        }
     }

     async searchByCity(evt) {
         evt.preventDefault();

         const errorMessageDisplay = document.getElementById('error-message');
         errorMessageDisplay.innerText = ``;
         errorMessageDisplay.classList.add('hidden');


         const cityCountry = encodeURIComponent(document.getElementById('city-name').value);
         if(cityCountry === "") {return;}
         const button = document.getElementById('city-search');
         button.innerText = 'Loading...';
         const exhibitions =  await this.client.searchExhibitionsByCity(cityCountry, (error) => {
              errorMessageDisplay.innerText = `${error.message}`;
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
    async searchByMovement(evt) {
         evt.preventDefault();

         const errorMessageDisplay = document.getElementById('error-message-category');
         errorMessageDisplay.innerText = ``;
         errorMessageDisplay.classList.add('hidden');

         const button = document.getElementById('category-search');
         button.innerText = 'Loading...';

         const category = document.getElementById('category-input').value;

         const exhibitions =  await this.client.searchExhibitionsByMovement(category, (error) => {
              errorMessageDisplay.innerText = `${error.message}`;
              errorMessageDisplay.classList.remove('hidden');
                                       });

        this.dataStore.set('exhibitions', exhibitions);
console.log(exhibitions);

        button.innerText = 'Complete';
        setTimeout(function() {
            button.innerText = 'Search';
        }, 800);
    }
    async searchByMedium(evt) {
         evt.preventDefault();

         const errorMessageDisplay = document.getElementById('error-message-medium');
         errorMessageDisplay.innerText = ``;
         errorMessageDisplay.classList.add('hidden');

         const button = document.getElementById('medium-search');
         button.innerText = 'Loading...';

         const category = document.getElementById('medium-input').value;

         const exhibitions =  await this.client.searchExhibitionsByMedium(category, (error) => {
              errorMessageDisplay.innerText = `${error.message}`;
              errorMessageDisplay.classList.remove('hidden');
                                       });

        this.dataStore.set('exhibitions', exhibitions);
console.log(exhibitions);

        button.innerText = 'Complete';
        setTimeout(function() {
            button.innerText = 'Search';
        }, 800);
    }
    async searchByCityAndMedium(evt) {
         evt.preventDefault();

         const errorMessageDisplay = document.getElementById('error-message-city-medium');
         errorMessageDisplay.innerText = ``;
         errorMessageDisplay.classList.add('hidden');
         const cityCountry = document.getElementById('city-name-medium').value;
         const category = document.getElementById('city-medium-input').value;
         if(cityCountry === "") {return;}

         const button = document.getElementById('city-medium-search');
         button.innerText = 'Loading...';

         const exhibitions =  await this.client.searchExhibitionsByCityAndMedium(cityCountry, category, (error) => {
              errorMessageDisplay.innerText = `${error.message}`;
              errorMessageDisplay.classList.remove('hidden');
                                       });

        this.dataStore.set('exhibitions', exhibitions);
console.log(exhibitions);

        button.innerText = 'Complete';
        setTimeout(function() {
            button.innerText = 'Search';
        }, 800);
    }
    async searchByCityAndDate(evt) {
         evt.preventDefault();

         const errorMessageDisplay = document.getElementById('error-message-city-date');
         errorMessageDisplay.innerText = ``;
         errorMessageDisplay.classList.add('hidden');

         const cityCountry = document.getElementById('city-name-date').value;
         const startDate = document.getElementById('city-startDate-input').value;
         const endDate = document.getElementById('city-endDate-input').value;
         if (cityCountry == null || startDate == null || endDate == null || cityCountry === "") {return;}

         const button = document.getElementById('city-date-search');
         button.innerText = 'Loading...';

         const exhibitions =  await this.client.searchExhibitionsByCityAndDate(cityCountry, startDate, endDate, (error) => {
              errorMessageDisplay.innerText = `${error.message}`;
              errorMessageDisplay.classList.remove('hidden');
                                       });

        this.dataStore.set('exhibitions', exhibitions);

        button.innerText = 'Complete';
        setTimeout(function() {
            button.innerText = 'Search';
        }, 800);
    }
    async searchByDate(evt) {
         evt.preventDefault();

         const errorMessageDisplay = document.getElementById('error-message-date');
         errorMessageDisplay.innerText = ``;
         errorMessageDisplay.classList.add('hidden');

         const startDate = document.getElementById('startDate-input').value;
         const endDate = document.getElementById('endDate-input').value;
         if (startDate === "" || startDate == null || endDate == null || endDate === "") {return;}

         const button = document.getElementById('date-search');
         button.innerText = 'Loading...';


         const exhibitions =  await this.client.searchExhibitionsByDate(startDate, endDate, (error) => {
              errorMessageDisplay.innerText = `${error.message}`;
              errorMessageDisplay.classList.remove('hidden');
                                       });

        this.dataStore.set('exhibitions', exhibitions);
console.log(exhibitions);

        button.innerText = 'Complete';
        setTimeout(function() {
            button.innerText = 'Search';
            let wishlistInput = document.getElementById('date-search-form');
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

        const name = exhibition.exhibitionName;

        var institution = exhibition.institution;
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
console.log(exhibitionList);
        exhibitionList.forEach(ex => {
            ex.addEventListener('click', this.viewExhibitionDetails);
        });
    }

    async viewExhibitionDetails(evt) {
      //  evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
        const exName = evt.target.getAttribute('name');

        const result = this.dataStoreDetails.get(exName);

        if(result == null) {return;}

        this.viewDetails.addExhibitionDetailsToPage(result);
    }

    async hidePreviousSearchDetails(evt) {
                const resultContainer = document.getElementById('view-details-container');
                resultContainer.classList.add('hidden');
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