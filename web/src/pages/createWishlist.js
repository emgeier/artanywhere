import MusicPlaylistClient from '../api/musicPlaylistClient';
import Header from '../components/header';
import Footer from '../components/footer';
import ViewDetails from '../components/viewDetails';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the createWishlist page of the website.
 */
class CreateWishlist extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount','clientLoaded','submit', 'addExhibition', 'removeExhibition',
            'viewWishlist','addViewResultsToPage','viewExhibitionDetails', 'deleteWishlist',
            'recommendExhibitions','viewRecommendedExhibitions'], this);
        this.dataStore = new DataStore();
        this.dataStoreView = new DataStore();
        this.dataStoreRecommendations = new DataStore();
        this.dataStoreView.addChangeListener(this.addViewResultsToPage);
        this.dataStoreRecommendations.addChangeListener(this.viewRecommendedExhibitions);

        this.header = new Header(this.dataStore);
        this.footer = new Footer(this.dataStore);
        this.viewDetails = new ViewDetails(this.dataStoreView);
    }
    /**
     * Add the header and the footer to the page and load the MusicPlaylistClient.
     */
    mount() {
        document.getElementById('create-wishlist').addEventListener('click', this.submit);
        document.getElementById('add-exhibition').addEventListener('click', this.addExhibition);
        document.getElementById('add-exhibition').addEventListener('click', this.recommendExhibitions);
        document.getElementById('remove-exhibition').addEventListener('click', this.removeExhibition);
        document.getElementById('view-wishlist').addEventListener('click', this.viewWishlist);
        document.getElementById('view-exhibition-details').addEventListener('click', this.viewExhibitionDetails);
        document.getElementById('delete-wishlist').addEventListener('click', this.deleteWishlist);
        this.header.addHeaderToPage();
        this.footer.addFooterToPage();
        this.client = new MusicPlaylistClient();
        this.clientLoaded();
    }
    async clientLoaded() {
       const urlParams = new URLSearchParams(window.location.search);
       if (urlParams != null) {
            const exhibitionNameRedirect = urlParams.get('exhibitionName');
            document.getElementById('exhibition-name').value = exhibitionNameRedirect;
            const exhibitionCityRedirect = urlParams.get('exhibitionCity');
            document.getElementById('exhibition-city').value = exhibitionCityRedirect;
        }
     }
    /**
     * Deletes a wishlist using the wishlist name associated with the logged in user (email identification).
     * Method runs when the delete wishlist button is clicked.
     */
    async deleteWishlist(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const listName = document.getElementById('wishlist-name-view').value;
        if(!listName) {return;}
        if(listName === null || listName.length === 0) {return;}
        const button = document.getElementById('delete-wishlist');
        button.innerText = 'Deleting...';

        await this.client.deleteWishlist(listName, description, (error) => {
                errorMessageDisplay.innerText = `${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });
        document.getElementById('view-wishlist-container').classList.add('hidden');
        button.innerText = 'Deleted';

        setTimeout(function() {
             button.innerText = 'Delete Another Wishlist';
             let wishlistInput = document.getElementById('view-wishlist-form');
             wishlistInput.reset();
         }, 500);
    }
        /**
         * Method to run when the create wishlist submit button is pressed. Call the ArtAnywhereService to create the
         * list and store it in the wishlists AWS DynamoDB table.
         */
        async submit(evt) {
            evt.preventDefault();

            const errorMessageDisplay = document.getElementById('error-message');
            errorMessageDisplay.innerText = ``;
            errorMessageDisplay.classList.add('hidden');

            const listName = document.getElementById('wishlist-name').value;
            if(!listName) {return;}

            if(listName === null || listName.length === 0) {return;}
            const description = document.getElementById('description').value;

            const createButton = document.getElementById('create-wishlist');
            const origButtonText = createButton.innerText;
            createButton.innerText = 'Loading...';

            const wishlist = await this.client.createWishlist(listName, description, (error) => {
                createButton.innerText = origButtonText;
                errorMessageDisplay.innerText = `${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });
//            this.dataStore.set('wishlist', wishlist);
            this.dataStoreView.set('wishlist', wishlist);
            createButton.innerText = 'Complete';
            setTimeout(function() {
                createButton.innerText = 'Create New Wishlist';
                let wishlistInput = document.getElementById('create-wishlist-form');
                wishlistInput.reset();
                    }, 800);

        }
    /**
     * Add the requested exhibition (using exhibition name and city as keys to the item in the AWS DynamoDb table) to
     * the wishlist indicated by the user. If any of the fields are blank, the method does nothing. The exhibition is
     * added to a datastore, triggering the recommendExhibitions method.
     */
    async addExhibition(evt) {
        evt.preventDefault();
        //can be consolidated
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
        const exhibitionName = document.getElementById('exhibition-name').value;
        const exhibitionCity = document.getElementById('exhibition-city').value;
        const wishlistToAddTo = document.getElementById('wishlist-name-2').value;
        if( wishlistToAddTo.length === 0) {return;}
        if( exhibitionName.length === 0) {return;}
        if( exhibitionCity.length === 0) {return;}
        const addButton = document.getElementById('add-exhibition');
        addButton.innerText = 'Loading...';

        const exhibitions = await this.client.addExhibitionToWishlist(wishlistToAddTo, exhibitionCity, exhibitionName, (error) => {
                errorMessageDisplay.innerText = `${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('exhibitions', exhibitions);
                const wishlist = await this.client.getWishlist(wishlistToAddTo, (error) => {
                        errorMessageDisplay.innerText = `${error.message}`;
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
    /**
     * Removes the requested exhibition (using exhibition name and city as keys to the item in the AWS DynamoDb table) to
     * the wishlist indicated by the user. If any of the fields are blank, the method does nothing.
     */
    async removeExhibition(evt) {
        evt.preventDefault();
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const exhibitionName = document.getElementById('exhibition-name').value;
        const exhibitionCity = document.getElementById('exhibition-city').value;
        const wishlistName = document.getElementById('wishlist-name-2').value;
        if( wishlistName.length === 0) {return;}
        if( exhibitionName.length === 0) {return;}
        if( exhibitionCity.length === 0) {return;}

        const button = document.getElementById('remove-exhibition');
        button.innerText = 'Loading...';

        const wishlist = await this.client.removeExhibitionFromWishlist(wishlistName, exhibitionCity, exhibitionName,
            (error) => {
            errorMessageDisplay.innerText = `${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });

        document.getElementById('view-wishlist-container').classList.add('hidden');

        setTimeout(function() {
            button.innerText = 'Remove Another Exhibition';
            let wishlistInput = document.getElementById('exhibition-wishlist-form');
            wishlistInput.reset();
        }, 800);
    }
    /**
     * When a wishlist name is entered into the wishlist name field and the view wishlist button is clicked, the wishlist
     * details are gotten from the wishlists AWS DynamoDb table.
     * When a wishlist is created or its associated exhibitions list updated, this method also runs.
     */
     async viewWishlist(evt) {
         evt.preventDefault();
         const errorMessageDisplay = document.getElementById('error-message-view');
         errorMessageDisplay.innerText = ``;
         errorMessageDisplay.classList.add('hidden');

         const listName = document.getElementById('wishlist-name-view').value;
         if(!listName) {return;}

         const button = document.getElementById('view-wishlist');
         const origButtonText = button.innerText;
         button.innerText = 'Loading...';

         const wishlist = await this.client.getWishlist(listName, (error) => {
            button.innerText = origButtonText;
            errorMessageDisplay.innerText = `${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
         });
         this.dataStoreView.set('wishlist', wishlist);
         this.dataStore.set('wishlist', wishlist);

         button.innerText = 'Complete';
         setTimeout(function() {
              button.innerText = 'View Another Wishlist';
              let wishlistInput = document.getElementById('view-wishlist-form');
              wishlistInput.reset();
         }, 800);

    }
    /**
     * When a wishlist name is entered into the wishlist name field and the view wishlist button is clicked, the wishlist
     * details are gotten from the wishlists AWS DynamoDb table, then displayed with this method.
     * When a wishlist is created or its associated exhibitions list updated, this method also runs.
    */
    async addViewResultsToPage() {

        const result = this.dataStoreView.get('wishlist');

        if(result === null) {return;}
        if(!result){return;}

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
    /*
     This method takes the user input in the exhibition name and city fields, retrieves the exhibition from the dynamodb
     table with those keys and displays the exhibition details for the item stored in the exhibitions dyanamodb table.
     The method runs when the "view exhibition details" button is clicked.
    */
    async viewExhibitionDetails(evt) {
        evt.preventDefault();
        //can be consolidated
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const exhibitionName = document.getElementById('exhibition-name').value;
        const exhibitionCity = document.getElementById('exhibition-city').value;
        if (!exhibitionCity || !exhibitionName) {return;}

        const button = document.getElementById('view-exhibition-details');
        button.innerText = 'Loading...';

        this.viewDetails.getExhibitionDetailsForPage(exhibitionName, exhibitionCity);
        button.innerText = "View Exhibition Details";
    }

    /**
     * When an exhibition is added to a wishlist two exhibitions similar to it in various categories is found in the
     * database.
    */
    async recommendExhibitions(evt) {
        evt.preventDefault();
        const exhibitionName = document.getElementById('exhibition-name').value;
        const exhibitionCity = document.getElementById('exhibition-city').value;

        if( exhibitionName.length === 0) {return;}
        if( exhibitionCity.length === 0) {return;}
        const errorMessageDisplay = document.getElementById('error-message');
        const similarExhibitions = await this.client.getRecommendedExhibitions(exhibitionCity, exhibitionName,
        (error) => {
             errorMessageDisplay.innerText = `${error.message}`;
             errorMessageDisplay.classList.remove('hidden');
        });
        if(!similarExhibitions) {return;}

        if (similarExhibitions === null || similarExhibitions.length === 0) {return;}

        this.dataStoreRecommendations.set('similarExhibitions', similarExhibitions);

    }
    /**
     * When recommended exhibitions are found, they are added to the recommendations datastore, which triggers
     * this method to display their names and images.
    */

    async viewRecommendedExhibitions() {

        const recommendations = this.dataStoreRecommendations.get('similarExhibitions');
        const recommendExhibitionsContainer = document.getElementById('recommended-exhibitions-container');
        recommendExhibitionsContainer.classList.remove('hidden');
        const inputExhibitionName = document.getElementById('exhibition-name').value;

        var firstTwoRecs = recommendations.slice(0,2);
        let recommendation;
        let resultHtml = '';
        for(recommendation of firstTwoRecs) {

            const exhibitionName = recommendation.exhibitionName;
            if (exhibitionName === inputExhibitionName) { continue;}
            const exhibitionCity = recommendation.cityCountry;
            const imageUrl = recommendation.imageUrl;
            const imageAttribution = recommendation.imageAttribution;
            const institution = recommendation.institution;
            const spacing = "           ";

            resultHtml += `
            <div class="recommended-exhibition-pair">
               <a href="createWishlist.html?exhibitionName=${exhibitionName}&exhibitionCity=${exhibitionCity}"   <span class="recommendation-name" id="view-name">${exhibitionName}   </span>
               <br>
               <img src= "${imageUrl}" alt="Image description" >
               <br>
               <span>${institution}</span>
               <a href=#  <span class="artist-name-space" id="space">        ${spacing}          </span>
               <br>
               </div>
                `;

        }
         document.getElementById("recommended-exhibitions").innerHTML = resultHtml;

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

