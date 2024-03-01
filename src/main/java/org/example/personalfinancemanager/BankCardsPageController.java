package org.example.personalfinancemanager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.personalfinancemanager.database.Database;
import org.example.personalfinancemanager.model.Transaction;

import java.io.IOException;
import java.util.List;

public class BankCardsPageController {

    @FXML
    private ImageView imgRBC, imgCIBC, imgBMO, imgTD;
    @FXML
    private Label lblRBCTotalIncome, lblRBCTotalExpense;
    @FXML
    private Label lblCIBCTotalIncome, lblCIBCTotalExpense;
    @FXML
    private Label lblBMOTotalIncome, lblBMOTotalExpense;
    @FXML
    private Label lblTDTotalIncome, lblTDTotalExpense;

    public void initialize() {
        loadCardImages();
        updateCardTotals();
    }

    private void loadCardImages() {
        imgRBC.setImage(new Image(getClass().getResourceAsStream("/org/example/personalfinancemanager/images/RBC.png")));
        imgCIBC.setImage(new Image(getClass().getResourceAsStream("/org/example/personalfinancemanager/images/CIBC.jpg")));
        imgBMO.setImage(new Image(getClass().getResourceAsStream("/org/example/personalfinancemanager/images/BMO.png")));
        imgTD.setImage(new Image(getClass().getResourceAsStream("/org/example/personalfinancemanager/images/TD.png")));
    }

    private void updateCardTotals() {
        // Example of updating labels, replace with actual data retrieval logic
        lblRBCTotalIncome.setText("Income: $" + getTotalIncomeForBank("RBC"));
        lblRBCTotalExpense.setText("Expense: $" + getTotalExpenseForBank("RBC"));

        lblCIBCTotalIncome.setText("Income: $" + getTotalIncomeForBank("CIBC"));
        lblCIBCTotalExpense.setText("Expense: $" + getTotalExpenseForBank("CIBC"));

        lblBMOTotalIncome.setText("Income: $" + getTotalIncomeForBank("BMO"));
        lblBMOTotalExpense.setText("Expense: $" + getTotalExpenseForBank("BMO"));

        lblTDTotalIncome.setText("Income: $" + getTotalIncomeForBank("TD"));
        lblTDTotalExpense.setText("Expense: $" + getTotalExpenseForBank("TD"));
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
    private void handleBackAction() throws IOException {
        // Simply change the scene to go back
        PersonalFinanceManager.setRoot("initialPage.fxml");
    }
}
