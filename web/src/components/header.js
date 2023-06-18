import MusicPlaylistClient from '../api/musicPlaylistClient';
import BindingClass from "../util/bindingClass";

/**
 * The header component for the website.
 */
export default class Header extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addHeaderToPage', 'createSiteTitle', 'createUserInfoForHeader',
            'createLoginButton', 'createLoginButton', 'createLogoutButton','createLogo'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new MusicPlaylistClient();
    }

    /**
     * Add the header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        const siteTitle = this.createSiteTitle();
        const userInfo = this.createUserInfoForHeader(currentUser);

        const header = document.getElementById('header');
        header.appendChild(siteTitle);
        header.appendChild(userInfo);

    }

    createSiteTitle() {
        const homeButton = document.createElement('a');
        homeButton.classList.add('header_home');
        homeButton.href = 'index.html';
        homeButton.innerText = 'Artanywhere';
        homeButton.innerHTML = `<div class= center-title><img src= "/images/artanywhere-low-resolution-logo-black-on-transparent-background.png" alt="logo" width="230px", height="75px"></div>`;

   // /images/artanywhere-high-resolution-logo-color-on-transparent-background.png
        const logoDisplay = document.createElement('b');
        logoDisplay.classList.add('header_home');
        logoDisplay.href = 'index.html';
   //     logoDisplay.innerHTML = `<div class= center-title><img src= "/images/artanywhere-low-resolution-logo-black-on-transparent-background.png" alt="logo" width="125px", height="35px"></div>`;

        const siteTitle = document.createElement('div');
        siteTitle.classList.add('site-title');
        siteTitle.appendChild(homeButton);

        siteTitle.appendChild(logoDisplay);

        return siteTitle;
    }
    createLogo() {
            const homeButton = document.createElement('a');
            homeButton.classList.add('header_home');
            homeButton.innerText = 'Artanywhere';

            const siteTitle = document.createElement('div');
            siteTitle.classList.add('site-title');
            siteTitle.appendChild(homeButton);

            return siteTitle;
    }

    createUserInfoForHeader(currentUser) {
        const userInfo = document.createElement('div');
        userInfo.classList.add('user');

        const childContent = currentUser
            ? this.createLogoutButton(currentUser)
            : this.createLoginButton();

        userInfo.appendChild(childContent);

        return userInfo;
    }

    createLoginButton() {
        return this.createButton('Login', this.client.login);
    }

    createLogoutButton(currentUser) {
        return this.createButton(`Logout: ${currentUser.name}`, this.client.logout);
    }

    createButton(text, clickHandler) {
        const button = document.createElement('a');
        button.classList.add('button');
        button.href = '#';
        button.innerText = text;

        button.addEventListener('click', async () => {
            await clickHandler();
        });

        return button;
    }
}
