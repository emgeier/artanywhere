# Artanywhere
## Problem Statement

Every art gallery and art museum has its own site with some information about exhibits and art in their collections, however it is difficult for art fans to know _what_ is happening _when_ in the art world without time-consuming searching. This site would make searching for interesting art exhibitions more convenient and allow users to create lists of art experiences that they find for future reference and planning. Users can search for specific genres or artists whose work they would like to see, or query by city/date to see what exhibitions will be happening. It will have the purpose and some of the functionality of a _Bandsintown_ app for art.

## Use Cases
U1. As an end user, I want to be able to search for exhibitions happening in my city.

U2. As an end user, I want to be able to save exhibitions that I find interesting in a list.

U3. As an end user, I want to be able to remove exhibitions that I no longer find interesting or cannot attend from my list.

U4. As an end user, I want to be abe to delete lists of exhibitions that I have created.

U5. As an end user, I want to be able to find exhibitions of works by a specific artist.

U6. As an end user, I want to be able to find exhibitions of works in a certain category, say Impressionists.

U7. As an end user, I want to be able to search for exhibitions within a date range.

U8. As an end user, I want to be able to annotate or label my list of exhibitions.

U9. As an end user, I want to be able to see exemplary works of art by specified artist.

U10. As an end user, I want to be able to see the details about an exhibition.

Extensions:

U10. As an end user, I want recommendations of artists and exhibitions based on my preferences.

U11. As an end user, I want to be able to search by artist to find a list of institutions that have works by that artist.

U12. As an end user, I want to be able to search for artists that have similar characteristics to an artist I like.

U13. As an end user, I want to be able to find out where I can see a specific painting.

U14. As an administrator, I want to be able to add information about exhibitions in the database.

U15. As an administrator, I want to be able to alter information about exhibitions in the database, such as "up-coming" to "past".


## Project Scope

### In Scope
1. Search/View Exhibitions
2. Search/View/Create/Update Personal Lists of Exhibitions
3. Search/View Info about Artists

### Out of Scope
1. A calendar function for exhibitions scheduling
2. Shared information with artists, a way for contemporary artists to connect with fans
3. Data about exhibitions populated by museum/gallery/artist (Museums apis)
4. Influencer curated lists of recommended events
5. Ticket/art sales integration
6. Complete museum collection information
7. User created "tours" of individual works of art
8. Fan connection tools for artists
9. Search/View Info about Works of Art
10. Artist portal for uploading photos of works(merchandizing extension possibilities from this extension) and additional information for fan connections/data

## Proposed Architecture Overview

### API
#### Public Models

Models required will include: ExhibitionModel, ExhibitionListModel/WishlistModel, ArtModel, ArtistModel
Optional model: InstitutionModel

#### Endpoints
1. Create Wishlist
2. View Wishlist
3. Add Exhibitions to Wishlist
4. Remove Exhibitions From Wishlist
5. Update Wishlist
6. Delete Wishlist
7. Search Exhibitions by City
8. Search Exhibitions by Artist
9. Search Exhibitions by Movement
10. Search Exhibitions by Date
11. View Art of Artist

#### **_Create Wishlist_**

User creates a list to put exhibitions that a user is interested in into.

POST

/wishlists

request: json content: listName(string), email(string), optional description(string) and cities(list of strings)

response: wishlist object(200) or InvalidAttributeValueException(400)

#### **_View Wishlist_**

User can view a list of exhibitions that a user has created.

GET

/wishlists/{email}/{listName}

request: path: listName(string), email(string)

response: wishlist object(200) or WishlistNotFoundException(400)

#### **_Add Exhibition to Wishlist_**

User can add an exhibition to a list that a user has created.

POST

/wishlists/{email}/{listName}/exhibitions

request: json content: listName (strubg), exhibition name(string), city(string) (Exhibition object)

response: wishlist object(200) or WishlistNotFoundException(400) or ExhibitionNotFoundException(400)


#### **_Remove Exhibition from Wishlist_**

User can remove an exhibition to a list that a user has created.

PUT (operation is an updating of list of exhibitions attribute of wishlist)

/wishlists/{email}/{listName}/exhibitions

request: json content: name(string), city(string) (Exhibition object)

response: wishlist object(200) or WishlistNotFoundException(400) or ExhibitionNotFoundException(400)

#### **_Update Wishlist_**

User can delete a list that a user has created.

PUT

/wishlists/{email}/{listName}

request: json content: listName(string), email(string), updated attribute information-- description or cities

response: wishlist object(200) or WishlistNotFoundException(400)  or InvalidAttributeValueException(400)


#### **_Delete Wishlist_**
User can delete a list that a user has created.

DELETE

/wishlists/

request: json content: listName(string), email(string)

response: wishlist object(200) or WishlistNotFoundException(400)
#### **_Get Exhibition_**

User can get a specified exhibition with its name and location information.

GET

/exhibitions/search/artist/{name}

request: json content: name(string)

response: list of exhibition objects(200) or ExhibitionNotFoundException(400)

#### **_Search Exhibitions by Artist_**

User can search for a list of exhibitions that feature a specified artist.

GET

Path: /exhibitions/search/artist/{artistName}


request: from path: name(string)

response: list of exhibition objects(200) or ExhibitionNotFoundException(400)

#### **_Search Exhibitions by Movement_**

User can search for a list of exhibitions that are designated by a movement(enum).

GET

/exhibitions/movement/{movement}

request: movement(string) from path

response: list of exhibition objects(200) or ExhibitionNotFoundException(400)
#### **_Search Exhibitions by Medium_**

User can search for a list of exhibitions that feature works in a specified medium. For example, my friend is really 
into ceramics and would like to know when ceramics exhibitions are in her area or to guide vacation planning. 
(scan of all cities (less common use case) or filter of city search query)
GET
/exhibitions/search/city/{cityCountry}/medium/{medium}
GET

/exhibitions/search/medium/{medium}

request: medium(string, convert to enum) from path

response: list of exhibition objects(200) or ExhibitionNotFoundException(400)

#### **_Search Exhibitions by Date Range_**

User can search for a list of exhibitions that are happening at a specified time.

GET

/exhibitions/search/date/{startDate}/{endDate}

response: list of exhibition objects(200) or ExhibitionNotFoundException(400)


#### **_Search Exhibitions by City_**

User can search for a list of exhibitions that feature a specified city.

GET

/exhibitions/search/city/{cityCountry}

request: city-name(string) in path

response: list of exhibition objects(200) or ExhibitionNotFoundException(400)
#### **_View Artist_Details**

User can view details about an artist (Pictures of art displayed. As are artists similar to the one requested)

GET

/artists/{name}

request:  path: name(string)

response: list of Artists with the same name (for this project probably only one artist will be in the list) 
or ArtistNotFoundException

#### **_View Art of Artist_**

User can view a list of works of art that an artist has created. (Pictures of art displayed.)

GET

/artists/{name}/{birthYear}/art

request: json content: name(string), birthYear(string)

response: list of Art objects(200) or ArtNotFoundException(400) or ArtistNotFoundException

### Class Diagrams
![Screen Shot 2023-05-17 at 10 00 24 AM](https://github.com/emgeier/nss-capstone/assets/115035002/48861761-84c6-4b0c-83e4-9c4d7c8e3240)
![Screen Shot 2023-05-17 at 9 57 59 AM](https://github.com/emgeier/nss-capstone/assets/115035002/594a849a-aa36-4c7d-88f3-429712e58fef)

### Sequence Diagram
![Screen Shot 2023-05-17 at 10 30 08 AM](https://github.com/emgeier/nss-capstone/assets/115035002/10613f3b-0c81-49b7-aaf6-99675f85846f)

## DynamoDB Tables
### Exhibitions
##### Required attributes
      AttributeDefinitions:
        - AttributeName: "cityCountry"
          AttributeType: "S"
        - AttributeName: "name"
          AttributeType: "S"
      KeySchema:
        - AttributeName: "cityCountry"
          KeyType: "HASH"
        - AttributeName: "name"
          KeyType: "RANGE"
##### Optional attributes
1. Address, string
2. Media(Enum), string
3. Tags, list of strings
4. Artists, list of strings
5. Art, list of strings
6. Image (key), string
7. Movement(enum), string
8. StartDate, string
9. EndDate, string

##### GSI
      KeySchema:
        - AttributeName: "institution"
          KeyType: "HASH"
        - AttributeName: "name"
          KeyType: "RANGE"
##### GSI
      KeySchema:
        - AttributeName: "movement"
          KeyType: "HASH"
        - AttributeName: "exhibitionName"
          KeyType: "RANGE"          

### User Lists of Exhibitions: Wishlists

##### Required attributes
      AttributeDefinitions:
        - AttributeName: "email"
          AttributeType: "S"
        - AttributeName: "listname"
          AttributeType: "S"
      KeySchema:
        - AttributeName: "email"
          KeyType: "HASH"
        - AttributeName: "listname"
          KeyType: "RANGE"
##### Optional attributes
1. Exhibitions, list of strings (keys to exhibitions)
2. Cities, list of strings
3. Description, string

### Artists
##### Required attributes
      AttributeDefinitions:
        - AttributeName: "name"
          AttributeType: "S"
        - AttributeName: "birthyear"
          AttributeType: "S"
      KeySchema:
        - AttributeName: "name"
          KeyType: "HASH"
        - AttributeName: "birthyear"
          KeyType: "RANGE"
##### Optional attributes
1. Cities, list of strings
2. Category(Enum), string
3. Tags, list of strings
4. Associated artists, list of strings
5. Art, Map of string keys, string values: title and image url 
6. Movements, list of strings
7. Description, string
8. DeathYear, string
9. BirthCity, string
10. BirthCountry, string
##### GSI
      KeySchema:
        - AttributeName: "Movement"
          KeyType: "HASH"
        - AttributeName: "name"
          KeyType: "RANGE"     
##### GSI
      KeySchema:
        - AttributeName: "BirthCountry"
          KeyType: "HASH"
        - AttributeName: "name"
          KeyType: "RANGE"

### Art
##### Required attributes
      AttributeDefinitions:
        - AttributeName: "artistname"
          AttributeType: "S"
        - AttributeName: "title"
          AttributeType: "S"
      KeySchema:
        - AttributeName: "artistname"
          KeyType: "HASH"
        - AttributeName: "title"
          KeyType: "RANGE"
##### Optional attributes
1. City, strings
2. Medium(Enum), string
3. Tags, list of strings
4. Associated works, list of strings
5. Movement, string
6. Description, string
7. Image(key to image stored in S3 bucket), string
8. Year, string

##### GSI
      KeySchema:
        - AttributeName: "artistname"
          KeyType: "HASH"
        - AttributeName: "year"
          KeyType: "RANGE"

### Pages
#### Home Page

![Screen Shot 2023-05-17 at 10 43 05 AM](https://github.com/emgeier/nss-capstone/assets/115035002/07adb4d7-a7ae-4ea8-9514-b021bfe42717)
![Screen Shot 2023-05-17 at 10 43 30 AM](https://github.com/emgeier/nss-capstone/assets/115035002/eaba8ce6-5485-40f1-92f4-bbe13ee0c6a4)
The three buttons: Discover Artists, Discover Exhibitions, and Plan redirect user to Artists, Exhibitions, and Wishlist pages
The Exhibition Wishlist section could be moved to the Wishlist page. The Featured Artists could be moved to the Artists page. The Home page should be simple, but give the user a sampling of what the app can do. The Exhibit Wishlist section could be changed to a subscription section. I would like the user to be able to click on a work of art and see details about it, eventually.

#### Exhibitions Page

![Screen Shot 2023-05-17 at 10 51 22 AM](https://github.com/emgeier/nss-capstone/assets/115035002/27aff7c0-3e2d-4594-87cd-0d9e504d6d98)
![Screen Shot 2023-05-17 at 11 10 49 AM](https://github.com/emgeier/nss-capstone/assets/115035002/920f0692-3332-4f5c-8b20-a2f3616119ab)

![Screen Shot 2023-05-17 at 10 51 30 AM](https://github.com/emgeier/nss-capstone/assets/115035002/f29aa36a-0441-44fe-96d5-7dbee4dfb20e)
The details section will appear in response to a search. Recommendations should be incorporated in that section as well, just below search results.

#### Artists Page
![Screen Shot 2023-05-17 at 10 58 10 AM](https://github.com/emgeier/nss-capstone/assets/115035002/9cf185a8-f8cd-4c67-bf0e-3247f61f7ddd)
This page is primarily for finding more information about known artists and discovering similar artists. Would be connection point for artists.

#### Wishlists Page
![Screen Shot 2023-05-17 at 11 04 53 AM](https://github.com/emgeier/nss-capstone/assets/115035002/30f63ed3-fc6d-4715-b6ae-0b56acab84d9)


This page is to view, create, modify and delete wishlists.
Summary of wishlists automatically pops up when creating wishlist as visual confirmation.
Summary of wishlist exhibits will also pop up with adding and removing exhibits with a list of three recommended exhibitions based on exhibition in wishlist.
Update wishlist tab changed to Update Description-- That will be the only field to update.
Delete button can be next to View.

Notes:
On Artist's page-- the name is the partition key-- if there are multiple artists with the same name, so we need an id for the dynamodb table
Administration tools could include:
1. Artist tools to connect with fans including an album of photos of works, possibly for sale or recently sold
2. Uploading exhibition information once/week/month from various apis
3. updating exhibition and artist tags
4. when new exhibitions are added, artists are added as well, automatically from artist list field
5. find artist at AIChicago api for basic info, exhibition field populated

I think artists page should include recommendations, have show exhibition details be a separate js file to import to any
