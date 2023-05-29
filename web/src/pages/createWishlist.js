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
        this.bindClassMethods(['mount', 'submit', 'addExhibition', 'removeExhibition',
            'viewWishlist','addViewResultsToPage','viewExhibitionDetails', 'deleteWishlist'], this);
        this.dataStore = new DataStore();
        this.dataStoreView = new DataStore();
        //this.dataStore.addChangeListener(this.addViewResultsToPage);
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
        document.getElementById('remove-exhibition').addEventListener('click', this.removeExhibition);
        document.getElementById('view-wishlist').addEventListener('click', this.viewWishlist);
        document.getElementById('view-exhibition-details').addEventListener('click', this.viewExhibitionDetails);
        document.getElementById('delete-wishlist').addEventListener('click', this.deleteWishlist);
        this.header.addHeaderToPage();

        this.client = new MusicPlaylistClient();
    }
    async deleteWishlist(evt) {
        evt.preventDefault();
        const button = document.getElementById('delete-wishlist');
        button.innerText = 'Deleting...';
        const listName = document.getElementById('wishlist-name-view').value;
 console.log(listName);
        await this.client.deleteWishlist(listName, description, (error) => {
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });
        button.innerText = 'Complete';
        setTimeout(function() {
             button.innerText = 'Delete Another Wishlist';
             let wishlistInput = document.getElementById('view-wishlist-form');
             wishlistInput.reset();
         }, 500);
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
                const wishlist = await this.client.getWishlist(wishlistToAddTo, (error) => {
                        errorMessageDisplay.innerText = `Error: ${error.message}`;
                        errorMessageDisplay.classList.remove('hidden');
                });
        this.dataStoreView.set('wishlist', wishlist);
                    addButton.innerText = 'Complete';

                    setTimeout(function() {
                        addButton.innerText = 'Add Another Exhibition';
                        let wishlistInput = document.getElementById('exhibition-wishlist-form');
                        wishlistInput.reset();
                            }, 800);
    }
    async removeExhibition(evt) {
        evt.preventDefault();
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const button = document.getElementById('remove-exhibition');
        button.innerText = 'Loading...';

        const exhibitionName = document.getElementById('exhibition-name').value;
        const exhibitionCity = document.getElementById('exhibition-city').value;
        const wishlistName = document.getElementById('wishlist-name-2').value;

        const wishlist = await this.client.removeExhibitionFromWishlist(wishlistName, exhibitionCity, exhibitionName,
            (error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });

        this.dataStoreView.set('wishlist', wishlist);
        button.innerText = 'Complete';

        setTimeout(function() {
            button.innerText = 'Remove Another Exhibition';
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

        if(result == null) {return;}
console.log("result not null");
        const resultContainer = document.getElementById('view-wishlist-container');
        resultContainer.classList.remove('hidden');
        document.getElementById('view-wishlist-name').innerText = result.listName;
        if (result.description != null) {
        const descriptionField = document.getElementById('view-wishlist-description');
        descriptionField.classList.remove('hidden');
        document.getElementById('view-wishlist-description').innerText = result.description;
        } else {document.getElementById('view-wishlist-description').innerText = ""}

        if (result.exhibitions != null) {
        const exhibitions = result.exhibitions;
        let resultHtml = '';
        let exhibition

        for(exhibition of exhibitions) {
            let exhibitionStrings = exhibition.split('*');
            let exhibitionName = exhibitionStrings[0];
            let exhibitionCity = exhibitionStrings[1];

            resultHtml+= `
                <ol class = "exhibition">
                    <span class = "exhibition-name" >${exhibitionName} :: </span>
                    <span class = "exhibition-city">${exhibitionCity}</span>
                </ol>
                <br>
            `;
        }
        document.getElementById('exhibitions').innerHTML = resultHtml;
        } else{document.getElementById('exhibitions').innerHTML = " ";}
    }
    async viewExhibitionDetails(evt) {
        evt.preventDefault();
        //can be consolidated
            const errorMessageDisplay = document.getElementById('error-message');
            errorMessageDisplay.innerText = ``;
            errorMessageDisplay.classList.add('hidden');

        const detailsButton = document.getElementById('view-exhibition-details');
        detailsButton.innerText = 'Loading...';

        const exhibitionName = document.getElementById('exhibition-name').value;
        const exhibitionCity = document.getElementById('exhibition-city').value;
        const result = await this.client.getExhibition(exhibitionCity, exhibitionName, (error) => {
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
        });
       // this.dataStore.set('exhibition', exhibition);
        if(result == null) {return;}

        const resultContainer = document.getElementById('view-wishlist-details-container');
        resultContainer.classList.remove('hidden');

        document.getElementById('view-exhibition-name').innerText = result.exhibitionName;
        if (result.description != null) {
            const descriptionField = document.getElementById('view-exhibition-description');
            descriptionField.classList.remove('hidden');
            document.getElementById('view-exhibition-description').innerText = result.description;
        } else {
            document.getElementById('view-exhibition-description').innerText = "";}
//Institution
        if (result.institution != null) {
                const attributeField = document.getElementById('view-institution');
                attributeField.classList.remove('hidden');
                attributeField.innerText = result.institution;
        } else {
            document.getElementById('view-institution').innerText = "";
            }
//Address
        if (result.address != null) {
                const attributeField = document.getElementById('view-exhibition-address');
                attributeField.classList.remove('hidden');
                attributeField.innerText = result.address;
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
            document.getElementById('view-exhibition-dates').innerText = "TBD";
        }
        try {
            const endDate = JSON.parse(result.endDate);

            const endDateDateObjParsed = new Date(endDate.year, endDate.month-1, endDate.day);

            const formattedEndDate = endDateDateObjParsed.toLocaleDateString('en-US', { month: 'long', day: 'numeric', year: 'numeric' });

            const attributeField = document.getElementById('view-exhibition-dates');

            attributeField.innerText = this.dataStore.get('startDate') + " - " + formattedEndDate;

        } catch (error) {
        }

//Artists
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
//Media
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
        detailsButton.innerText = 'View Exhibition Details';
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

