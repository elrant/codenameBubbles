const { app, BrowserWindow } = require('electron');
require('update-electron-app')()
const { setupTitlebar, attachTitlebarToWindow } = require("custom-electron-titlebar/main");

const path = require('path');

setupTitlebar();

/**
 * Create the main window
 */
function createWindow() {
    let mainWindow = new BrowserWindow({
        width: 940,
        height: 800,
        frame: true, // needed if process.versions.electron < 14
        titleBarStyle: 'default', 
        /* You can use *titleBarOverlay: true* to use the original Windows controls */
        titleBarOverlay: true,

        webPreferences: {
            sandbox: false,
            preload: path.join(__dirname, 'preload.js'),
        }
    });

    // Load your Vaadin app from the local development server
    mainWindow.loadURL('http://localhost:8080'); // Adjust the URL as needed

    attachTitlebarToWindow(mainWindow);
}

app.whenReady().then(createWindow);

app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') {
        app.quit();
    }
});

app.on('activate', () => {
    if (mainWindow === null) {
        createWindow();
    }
});
