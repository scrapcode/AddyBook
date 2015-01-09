package com.camcecil.address.view;

import com.camcecil.address.MainApp;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import org.controlsfx.dialog.Dialogs;

import java.io.File;

public class RootLayoutController
{

    // Reference to the main application
    private MainApp mainApp;

    /**
     * Called by the main application to give a reference back to itself.
     *
     * @return mainApp
     */
    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }

    /**
     * Creates an empty address book
     */
    @FXML
    private void handleNew()
    {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    /**
     * Opens a FileChooser to let the user select an address book to load
     */
    @FXML
    private void handleOpen()
    {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
                "XML Files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }
    }

    /**
     * Saves the file that is open. If none is open, displays the Save As... dialog.
     */
    @FXML
    private void handleSave()
    {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save the settings to.
     */
    @FXML
    private void handleSaveAs()
    {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
                "XML Files (*.xml)", "(*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure the file has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }

            mainApp.savePersonDataToFile(file);
        }
    }

    // Opens an about dialog
    @FXML
    private void handleAbout()
    {
        Dialogs.create()
                .title("AddressApp")
                .masthead("About")
                .message("Tutorial Student: Cam Cecil\n\nTutorial Author: Marco Jakob\\nWebsite: http://code.makery.ch")
                .showInformation();
    }

    // Closes the application
    @FXML
    private void handleExit()
    {
        System.exit(0);
    }

    // Shows the Birthday Statistics window.
    @FXML
    private void handleBirthdayStatistics()
    {
        mainApp.showBirthdayStatistics();
    }
}
