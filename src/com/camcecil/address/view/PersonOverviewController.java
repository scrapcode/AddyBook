package com.camcecil.address.view;

import com.camcecil.address.MainApp;
import com.camcecil.address.model.Person;
import com.camcecil.address.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * @author Cam Cecil (github.com/scrapcode)
 */

public class PersonOverviewController
{
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    // Reference to the main application
    private MainApp mainApp;

    /**
     * Constructor
     * The constructor is called before initialize()
     */
    public PersonOverviewController()
    {
    }

    /**
     * Initializes the controller class. This method is automagically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize()
    {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        // Clear Person details.
        showPersonDetails(null);

        // Listen for selection changed and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue)
        );

        // Sets a double click handler on row creation. Opens edit dialog on double click.
        personTable.setRowFactory(tv -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    handleEditPerson();
                }
            });
            return row;
        });
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;

        // Add observable list data to the table
        personTable.setItems(mainApp.getPersonData());
    }

    private void showPersonDetails(Person person)
    {
        if (person != null) {
            // Fill the labels with info from the person object
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            // Person is null, remove all the text
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    /**
     * Called when the user clicks on the Delete button.
     */
    @FXML
    private void handleDeletePerson()
    {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            // TODO: Use JavaFX Dialogs with the release of JDK 8u40!
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("No selection");
            warning.setHeaderText("No person is selected.");
            warning.setContentText("Please select a person to delete in the table.");
            warning.showAndWait();
        }
    }

    /**
     * Called when a user clicks the "New" button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson()
    {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);

            // Select the newly added entry in the list.
            personTable.getSelectionModel().selectLast();
        }
    }

    @FXML
    private void handleEditPerson()
    {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }
        } else {
            // Nothing selected
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("No selection");
            warning.setHeaderText("No person is selected.");
            warning.setContentText("Please select a person to edit in the table.");
            warning.showAndWait();
        }
    }
}
