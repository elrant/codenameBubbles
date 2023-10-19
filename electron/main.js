const { app, BrowserWindow } = require('electron');
require('update-electron-app')()

let mainWindow;

function createWindow() {
    mainWindow = new BrowserWindow({
        width: 800,
        height: 600,
    });

    // Load your Vaadin app from the local development server
    mainWindow.loadURL('http://localhost:8080'); // Adjust the URL as needed
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
