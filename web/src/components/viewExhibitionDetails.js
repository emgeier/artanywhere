//    async viewExhibitionDetails(evt) {
//         const exTest = evt.target.getAttribute('name');
//console.log(exTest);
//         const result = this.dataStoreDetails.get(exTest);
//
//        if(result == null) {return;}
//console.log(result.exhibitionName);
//
//        const resultContainer = document.getElementById('view-details-container');
//        resultContainer.classList.remove('hidden');
////Name
//        document.getElementById('view-exhibition-name').innerText = exTest;
//        document.getElementById('view-name').innerText = result.exhibitionName;
//
////Description
//        if (result.description != null) {
//            const descriptionField = document.getElementById('view-exhibition-description');
//            descriptionField.classList.remove('hidden');
//            document.getElementById('view-exhibition-description').innerText = result.description;
//        } else {
//            document.getElementById('view-exhibition-description').innerText = "";}
////Institution
//        if (result.institution != null) {
//                const attributeField = document.getElementById('view-institution');
//                attributeField.classList.remove('hidden');
//                attributeField.innerText = result.institution;
//        } else {
//            document.getElementById('view-institution').innerText = "";
//            }
////Address
//        if (result.address != null) {
//                const attributeField = document.getElementById('view-exhibition-address');
//                attributeField.classList.remove('hidden');
//                attributeField.innerText = result.address;
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
////Artists
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
////Media
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
//
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
//
//    }