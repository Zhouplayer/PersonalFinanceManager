package org.example.personalfinancemanager;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.personalfinancemanager.database.Database;
import java.io.IOException;
import java.time.LocalDate;

public class ExpendController {

    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private ComboBox<String> bankNameComboBox;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextArea notesTextArea;
    @FXML
    private RadioButton radioButtonExpense;
    @FXML
    private RadioButton radioButtonIncome;
    @FXML
    private DatePicker transactionDate;

    private ToggleGroup transactionTypeGroup = new ToggleGroup();

    @FXML
    private void initialize() {
        transactionDate.setValue(LocalDate.now()); // setting the date to now

        //Set toggleGroup, only one radio button can selected at time
        radioButtonExpense.setToggleGroup(transactionTypeGroup);
        radioButtonIncome.setToggleGroup(transactionTypeGroup);
    }

    //Change categoryComboBox item when selected
    @FXML
    private void onRadioButtonExpenseAction() {
        categoryComboBox.setItems(FXCollections.observableArrayList("Food", "Utilities", "Rent", "Transportation"));
        categoryComboBox.getSelectionModel().selectFirst(); // Select the first item as default
    }

    //Change categoryComboBox item when selected
    @FXML
    private void onRadioButtonIncomeAction() {
        categoryComboBox.setItems(FXCollections.observableArrayList("Salary", "Gifts", "Other Income"));
        categoryComboBox.getSelectionModel().selectFirst(); // Select the first item as default
    }


    @FXML
    private void handleAddAction() {
        RadioButton selectedRadioButton = (RadioButton) transactionTypeGroup.getSelectedToggle();

        String type = selectedRadioButton.getText();
        String amountText = amountTextField.getText();
        String bankName = bankNameComboBox.getValue() != null ? bankNameComboBox.getValue() : "";
        String category = categoryComboBox.getValue();
        String note = notesTextArea.getText();
        LocalDate date = transactionDate.getValue();

        if (category == null || category.isEmpty()) {
            // Show error message about category not being selected
            AlertUtil.showInfo("Information", "Please select a category.");
        }
        else
        {
            try {
                double amount = Double.parseDouble(amountText);

                // Insert the transaction into the database
                Database.insertTransaction(type, bankName, amount, category, note, date);

                // Clear fields or close expand view as needed
                PersonalFinanceManager.setRoot("initialPage.fxml");
            } catch (NumberFormatException e) {
                // Show error message about invalid amount
                AlertUtil.showError("Error", "The amount entered is not a valid number.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void handleBackAction() throws IOException {
        // Simply change the scene to go back
        PersonalFinanceManager.setRoot("initialPage.fxml");
    }

}
