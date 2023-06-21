<h1 align="center" style="display: block; font-size: 2.5em; font-weight: bold; margin-block-start: 1em; margin-block-end: 1e,">
<a name="logo" href="https://d3cb5tx9igdglk.cloudfront.net"><img align="center" src="https://github.com/emgeier/nss-capstone/assets/115035002/7a756e5e-1391-4438-a665-faf9f2771cd8" alt="artanywhere-high-resolution-logo-color-on-transparent-background" width="100%"></a>
    <br /><br />
</h1>

---------
Artanywhere is an application built to display the versatility of AWS Lambda services using AWS DynamoDB tables, Cognito, 
CloudWatch and S3, CloudFront, API Gateway, CloudFormation to enable application development and maintenance of 
REST API services.
---------
Overview
---------
Artanywhere has the purpose and some of the functionality of a _Bandsintown_ app for art. Every art gallery and art 
museum has its own site with information about exhibits and art in their collections, however it is difficult for art 
fans to know _what_ is happening _when_ in the art world without time-consuming searching. This site would make s
earching for interesting art exhibitions more convenient and allow users to create lists of art experiences that they 
find for future reference and planning. Users can search for specific genres or artists whose work they would like to
see, or query by city/date to see what exhibitions will be happening. 

Based on user interactions with the site, recommendations of artists and exhibitions are offered by the application.

---------
Features
---------
Artanywhere enables the following:

Wishlist creation, viewing, deletion, and the updating of the associated exhibitions. Wishlists created by users are 
stored in an AWS DynamoDB table.

Exhibition DynamoDB table searches for a range of types of queries: by artist, by city, by artistic movement, by 
artistic medium, by date, and various combinations.
(Technically, the searches utilize queries, scans, filters and condition expressions.)

Artists and exhibitions are recommended to users.

---------
Administrative functions
---------
The data to populate the tables comes primarily from the Art Institute of Chicago, an open API. The relevant exhibition
and artist data is processed and uploaded to the tables. This administrative feature is a prototype for getting 
data from the many sources that would be required to make the application fully functional.

Metrics for monitoring the use of the various searches and categories are included to guide decisions about further 
iterations of the product to improve its usefulness and efficiency.

---------
Examples
---------
A user in Chicago, Alice,  would like to see art during her four-day-weekend in July. Alice types in the city and dates 
and the application displays the search results on the page, with clickable options to get details for each 
exhibition in the list. Alice finds an interesting one and clicks on the address to get directions to the venue. 

A user in Nashville, Beatriz, is traveling to Madrid for a vacation in August, she would like to see what film related 
exhibitions are happening, so she selects FILM from the drop-down menu on the exhibitions page and enters Madrid, Spain. 
A list of exhibitions that meet those parameters show up. Beatriz finds one she likes and creates a wishlist on the 
wishlist page and adds the interesting exhibition. 

---------

Acknowledgements
---------
A million thanks to Gabe Cziprusz, Jody Alford, and the developers of the Amazon Technical Academy curriculum.



---------