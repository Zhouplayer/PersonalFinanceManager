package org.example.personalfinancemanager;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import org.example.personalfinancemanager.database.Database;

import java.io.IOException;
import java.time.LocalDate;

public class AddBankCardController {

    @FXML
    private ComboBox<String> bankNameComboBox;

    @FXML
    private void handleAddAction() {

        String bankName = bankNameComboBox.getValue();

        if (bankName == null || bankName.isEmpty()) {
            // Show error message about category not being selected
            AlertUtil.showInfo("Information", "Please select a bank name.");
        }
        else
        {
            try {

                String imagePath="";
                switch (bankName)
                {
                    case "RBC":
                        imagePath="/org/example/personalfinancemanager/images/RBC.png";
                        break;
                    case "CIBC":
                        imagePath="/org/example/personalfinancemanager/images/CIBC.jpg";
                        break;
                    case "BMO":
                        imagePath="/org/example/personalfinancemanager/images/BMO.png";
                        break;
                    case "TD":
                        imagePath="/org/example/personalfinancemanager/images/TD.png";
                        break;
                    case "ScotiaBank":
                        imagePath="/org/example/personalfinancemanager/images/ScotiaBank.png";
                        break;
                }

                // Insert the transaction into the database
                Database.insertBankCard(bankName, imagePath);

                // Clear fields or close expand view as needed
                PersonalFinanceManager.setRoot("bankCardsPage.fxml");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void handleBackAction() throws IOException {
        // Simply change the scene to go back
        PersonalFinanceManager.setRoot("bankCardsPage.fxml");
    }
}
