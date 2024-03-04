package org.example.personalfinancemanager;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.personalfinancemanager.database.Database;
import org.example.personalfinancemanager.model.BankCard;
import org.example.personalfinancemanager.model.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.example.personalfinancemanager.database.Database.getAllBankCards;

public class BankCardsPageController {

    @FXML
    ListView<BankCard> listViewBankCardList;

    public void initialize() {

        loadBankCards();
    }

    // This is a hypothetical method to reload bank cards from the database
    public void loadBankCards()
    {
        listViewBankCardList.setItems(FXCollections.observableArrayList(getAllBankCards()));
        listViewBankCardList.setCellFactory(param -> new ListCell<BankCard>() {
            @Override
            protected void updateItem(BankCard bankCard, boolean empty) {
                super.updateItem(bankCard, empty);
                if (empty || bankCard == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    double totalIncome = getTotalIncomeForBank(bankCard.getBankName());
                    double totalExpense = getTotalExpenseForBank(bankCard.getBankName());
                    setText(bankCard.getBankName() + "\nIncome: $" + totalIncome + "\nExpense: $" + totalExpense);
                    ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream( bankCard.getImagePath() )));
                    imageView.setFitHeight(180);
                    imageView.setFitWidth(300);
                    setGraphic(imageView);
                }
            }
        });
    }



    // Placeholder methods for total calculations
    public double getTotalIncomeForBank(String bankName) {

        double totalIncome = 0.0;
        List<Transaction> transactionList = Database.getAllTransactions();

        for (Transaction transaction : transactionList) {
            if (transaction.getBankName().equalsIgnoreCase(bankName) && transaction.getType().equalsIgnoreCase("Income")) {
                totalIncome += transaction.getAmount();
            }
        }
        return totalIncome;
    }

    public double getTotalExpenseForBank(String bankName) {

        double totalExpense = 0.0;
        List<Transaction> transactionList = Database.getAllTransactions();

        for (Transaction transaction : transactionList) {
            if (transaction.getBankName().equalsIgnoreCase(bankName) && transaction.getType().equalsIgnoreCase("Expense")) {
                totalExpense += transaction.getAmount();
            }
        }
        return totalExpense;
    }

    @FXML
    private void handleAddAction() throws IOException {
        // Simply change the scene to go back
        PersonalFinanceManager.setRoot("addBankCard.fxml");
    }

    @FXML
    private void handleBackAction() throws IOException {
        // Simply change the scene to go back
        PersonalFinanceManager.setRoot("initialPage.fxml");
    }

    @FXML
    private void onDeleteClick() {
        BankCard selectedBankCard = listViewBankCardList.getSelectionModel().getSelectedItem();
        if (selectedBankCard == null) {
            AlertUtil.showInfo("Information", "Please select a bank card to delete.");
        }
        else
        {
            // Show confirmation dialog
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this bank card?", ButtonType.YES, ButtonType.NO);
            confirmAlert.setTitle("Confirm Deletion");
            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES)
            {
                // Assuming the BankCard class has an ID field and a getter for it
                Database.deleteBankCard(selectedBankCard.getId());
                // Reload or refresh the list view to reflect the deletion
                loadBankCards(); // This is a hypothetical method to reload bank cards from the database
            }
        }

    }


}
