import { Titlebar } from "custom-electron-titlebar";

window.addEventListener('DOMContentLoaded', () => {
    // Title bar implementation
    const titlebar = new Titlebar();
    // get title from html
    const title = document.querySelector('title').innerHTML;
    // set title
    titlebar.updateTitle(title);

});