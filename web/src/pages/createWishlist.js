import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create playlist page of the website.
 */
class CreateWishlist extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'addExhibition', 'viewWishlist','addViewResultsToPage'], this);
        this.dataStore = new DataStore();
        this.dataStoreView = new DataStore();
      //  this.dataStore.addChangeListener(this.addViewResultsToPage);
        this.dataStoreView.addChangeListener(this.addViewResultsToPage);
        this.header = new Header(this.dataStore);
    }
    /**
     * Add the header to the page and load the MusicPlaylistClient.
     //do we need another client??
     */
    mount() {
        document.getElementById('create-wishlist').addEventListener('click', this.submit);
        document.getElementById('add-exhibition').addEventListener('click', this.addExhibition);
        document.getElementById('view-wishlist').addEventListener('click', this.viewWishlist);
        this.header.addHeaderToPage();

        this.client = new MusicPlaylistClient();
    }
        /**
         * Method to run when the create itinerary submit button is pressed. Call the MusicPlaylistService to create the
         * playlist.
         */
        async submit(evt) {
            evt.preventDefault();

            const errorMessageDisplay = document.getElementById('error-message');
            errorMessageDisplay.innerText = ``;
            errorMessageDisplay.classList.add('hidden');

            const createButton = document.getElementById('create-wishlist');
            const origButtonText = createButton.innerText;
            createButton.innerText = 'Loading...';

            const listName = document.getElementById('wishlist-name').value;
            const description = document.getElementById('description').value;

            const wishlist = await this.client.createWishlist(listName, description, (error) => {
                createButton.innerText = origButtonText;
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });
            this.dataStore.set('wishlist', wishlist);
            this.dataStoreView.set('wishlist', wishlist);
            createButton.innerText = 'Complete';
            setTimeout(function() {
                createButton.innerText = 'Create New Wishlist';
                let wishlistInput = document.getElementById('create-wishlist-form');
                wishlistInput.reset();
                    }, 800);

        }
    async addExhibition(evt) {
        evt.preventDefault();
        //can be consolidated
            const errorMessageDisplay = document.getElementById('error-message');
            errorMessageDisplay.innerText = ``;
            errorMessageDisplay.classList.add('hidden');

        const addButton = document.getElementById('add-exhibition');
        addButton.innerText = 'Loading...';

        const exhibitionName = document.getElementById('exhibition-name').value;
        const exhibitionCity = document.getElementById('exhibition-city').value;
        const wishlistToAddTo = document.getElementById('wishlist-name-2').value;

        const exhibitions = await this.client.addExhibitionToWishlist(wishlistToAddTo, exhibitionCity, exhibitionName, (error) => {
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('exhibitions', exhibitions);
                    addButton.innerText = 'Complete';
                    setTimeout(function() {
                        addButton.innerText = 'Add Another Exhibition';
                        let wishlistInput = document.getElementById('exhibition-wishlist-form');
                        wishlistInput.reset();
                            }, 800);
    }
    async viewWishlist(evt) {
         evt.preventDefault();
         const errorMessageDisplay = document.getElementById('error-message-view');
         errorMessageDisplay.innerText = ``;
         errorMessageDisplay.classList.add('hidden');

         const button = document.getElementById('view-wishlist');
         const origButtonText = button.innerText;
         button.innerText = 'Loading...';

         const listName = document.getElementById('wishlist-name-view').value;
         const wishlist = await this.client.getWishlist(listName, (error) => {
            button.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
         });
         this.dataStoreView.set('wishlist', wishlist);
                  this.dataStore.set('wishlist', wishlist);
                  console.log(wishlist);
         button.innerText = 'Complete';
         setTimeout(function() {
              button.innerText = 'View Another Wishlist';
              let wishlistInput = document.getElementById('view-wishlist-form');
              wishlistInput.reset();
         }, 800);

    }
    async addViewResultsToPage() {
    console.log("addViewResultsToPage");
        const result = this.dataStoreView.get('wishlist');
        const exhibitions = result.exhibitions;
        if(result == null) {return;}
console.log("result not null");
        const resultContainer = document.getElementById('view-wishlist-container');
        resultContainer.classList.remove('hidden');
        document.getElementById('view-wishlist-name').innerText = result.listName;
        if (result.description != null) {
        const descriptionField = document.getElementById('view-wishlist-description');
        descriptionField.classList.remove('hidden');
        document.getElementById('view-wishlist-description').innerText = result.description;
        }
        if (exhibitions != null) {
        let resultHtml = '';
        let exhibition

        for(exhibition of exhibitions) {
            let exhibitionStrings = exhibition.split('*');
            let exhibitionName = exhibitionStrings[0];
            let exhibitionCity = exhibitionStrings[1];

            resultHtml+= `
                <li class = "exhibition">
                    <span class= "exhibition-name">${exhibitionName} :: </span>
                    <span class = "exhibition-city">${exhibitionCity}</span>
                </li>
                <br>
            `;
        }
        document.getElementById('exhibitions').innerHTML = resultHtml;
        }
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createWishlist = new CreateWishlist();
    createWishlist.mount();
};

window.addEventListener('DOMContentLoaded', main);

