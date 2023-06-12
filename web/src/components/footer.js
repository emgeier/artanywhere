import MusicPlaylistClient from '../api/musicPlaylistClient';
import BindingClass from "../util/bindingClass";

/**
 * The footer component for the website.
 */
export default class Footer extends BindingClass {
    constructor() {
        super();
console.log("footer constructor");
        const methodsToBind = [
            'addFooterToPage'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new MusicPlaylistClient();
    }

    /**
     * Add the footer to the page.
     */
    async addFooterToPage() {
console.log("addFooterToPage");

    const footer = document.getElementById('footer');
    footer.innerHTML = `
  <div class="elegant-line"></div>
    <div class="card center">
      <div class="elegant-line"></div>
        <nav>
            <div class = "center" id = "navbar2">
                <ul>
                    <tr>
                        <a href ="index.html"> Home  |  </a>
                    </tr>
                    <tr>
                        <a href = "artists.html"> Artists  |  </a>
                    </tr>
                    <tr>
                        <a href ="exhibitions.html"> Exhibitions  |  </a>
                    </tr>
                    <tr>
                        <a href ="createWishlist.html"> Wishlist    </a>
                    </tr>
                </ul>
            </div>
        </nav>
        <div class= left-logo><img src= "/images/artanywhere-website-favicon-color.png" alt="logo" width="70px", height="70px"></div>
    </div>`
    }
}