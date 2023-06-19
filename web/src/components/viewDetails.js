import MusicPlaylistClient from '../api/musicPlaylistClient';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * A view exhibition details component for the website.
 */
export default class ViewDetails extends BindingClass {
    constructor() {
        super();
console.log("viewDetails constructor");
        const methodsToBind = ['getExhibitionDetailsForPage',
            'addExhibitionDetailsToPage'
        ];
        this.bindClassMethods(methodsToBind, this);
        this.dataStoreDates = new DataStore();
        this.client = new MusicPlaylistClient();
    }


    async getExhibitionDetailsForPage(exhibitionName, exhibitionCity) {

        //can be consolidated
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

//        const exhibitionName = document.getElementById('exhibition-name').value;
//        const exhibitionCity = document.getElementById('exhibition-city').value;
        if( exhibitionName === "") {return;}
        if( exhibitionCity === "") {return;}
//
//        const detailsButton = document.getElementById('view-exhibition-details');
//        detailsButton.innerText = 'Loading...';

        const result = await this.client.getExhibition(exhibitionCity, exhibitionName, (error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });

        if(result === null) {return;}
        this.addExhibitionDetailsToPage(result);
        console.log(result.exhibitionName);
        }
//With the result exhibition, add the details to the page

    async addExhibitionDetailsToPage(result) {
        const resultContainer = document.getElementById('view-details-container');
        resultContainer.classList.remove('hidden');
        document.getElementById('view-name').innerText = result.exhibitionName;

//Description
        if (result.description != null) {
            const descriptionField = document.getElementById('view-exhibition-description');
            descriptionField.classList.remove('hidden');
            document.getElementById('view-exhibition-description').innerText = "Description:  \xa0" + result.description;
        } else {
            document.getElementById('view-exhibition-description').innerText = "";}
//Institution
        if (result.institution != null) {
                const attributeField = document.getElementById('view-institution');
                attributeField.classList.remove('hidden');
                attributeField.innerText = "Institution:  \xa0" + result.institution;
        } else {
            document.getElementById('view-institution').innerText = "";
            }
//Address
        if (result.address != null) {
//                const attributeField = document.getElementById('view-exhibition-address');
//                attributeField.classList.remove('hidden');
//                attributeField.innerText = "Address:  \xa0" + result.address;
//
                let addressHTML='';

                if (result.address != null) {
                        const attributeField = document.getElementById('view-exhibition-address');
                        attributeField.classList.remove('hidden');
                        attributeField.innerText = result.address;
                        const addressString = result.address;
                        addressHTML =`Address:  \xa0  <span class="address"><a href= "https://www.google.com/maps/place/${addressString}"> ${addressString}</a></span>`;
                        attributeField.innerHTML = addressHTML;

                }
        }
//Dates
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
            this.dataStoreDates.set('startDate', formattedDate);
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

            attributeField.innerText = "Dates: " +"\xa0  "+ this.dataStoreDates.get('startDate') + "  -  " + formattedEndDate;

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
        document.getElementById('artist-header-list').innerHTML = 'Artists:';
        document.getElementById('artists').innerHTML = resultHtml;
        }
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
        document.getElementById('view-exhibition-media').innerHTML = "Description:  \xa0";

        document.getElementById('media').innerHTML = resultMediaHtml;

        } else { document.getElementById('media').innerHTML = "";}

console.log(result.imageUrl);
console.log(result);
        if(result.imageUrl != null) {
            const url = result.imageUrl;
            const urlAttribution = result.imageAttribution;
            let urlHtml = `<img src=${url} alt="Image description"  height="500"> <br>
                <span id = "attribution" >${urlAttribution}</span>
            `;

        document.getElementById("image").innerHTML =
            urlHtml;

        }
       // detailsButton.innerText = 'View Exhibition Details';
    }
    }