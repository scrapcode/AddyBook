package com.camcecil.address.view;

import com.camcecil.address.model.Person;
import com.camcecil.address.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

import java.util.Date;

/**
 * Dialog to edit the details of a Person
 */
public class PersonEditDialogController
{

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField birthdayField;

    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automagically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize()
    {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param person
     */
    public void setPerson(Person person)
    {
        this.person = person;

        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        streetField.setText(person.getStreet());
        postalCodeField.setText(Integer.toString(person.getPostalCode()));
        cityField.setText(person.getCity());
        birthdayField.setText(DateUtil.format(person.getBirthday()));
        birthdayField.setPromptText(DateUtil.getDatePattern());
    }

    /**
     * Returns true if the user clicked OK, otherwise returns false.
     *
     * @return
     */
    public boolean isOkClicked()
    {
        return okClicked;
    }

    /**
     * Called when the user clicks "OK"
     */
    @FXML
    private void handleOk()
    {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setStreet(streetField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            person.setCity(cityField.getText());
            person.setBirthday(DateUtil.parse(birthdayField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks "Cancel"
     */
    @FXML
    private void handleCancel()
    {
        dialogStage.close();
    }

    /**
     * Validates the input in the text fields.
     *
     * Displays a dialog if input is invalid.
     *
     * @return true if input is valid, otherwise false.
     */
    private boolean isInputValid()
    {
        String errorMessage = "";

        if (isFieldEmpty(firstNameField)) {
            errorMessage += "No valid First Name!\n";
        }
        if (isFieldEmpty(lastNameField)) {
            errorMessage += "No valid Last Name!\n";
        }
        if (isFieldEmpty(streetField)) {
            errorMessage += "No valid Street!\n";
        }

        if (isFieldEmpty(postalCodeField)) {
            errorMessage += "No valid Postal Code!\n";
        } else {
            // Try to parse postal code into an int
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid Postal Code (must be an integer)!\n";
            }
        }

        if (isFieldEmpty(cityField)) {
            errorMessage += "No valid City!\n";
        }

        if (isFieldEmpty(birthdayField)) {
            errorMessage += "No valid Birthday!\n";
        } else {
            if (!DateUtil.isValidDate(birthdayField.getText())) {
                errorMessage += "No valid Birthday. Use the format " + DateUtil.getDatePattern() + "!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message
            Dialogs.create()
                    .title("Invalid Fields")
                    .masthead("Please correct the invalid fields:")
                    .message(errorMessage)
                    .showError();
            return false;
        }
    }

    /**
     * Returns true if given field is empty, otherwise false.
     *
     * @param field Text field to check emptiness.
     * @return
     */
    private boolean isFieldEmpty(TextField field)
    {
        if (field.getText() == null || field.getText().length() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
