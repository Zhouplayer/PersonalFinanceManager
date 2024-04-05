package org.example.personalfinancemanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.personalfinancemanager.database.Database;
import org.example.personalfinancemanager.model.Transaction;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class InitialController implements Initializable {

    @FXML private Label lblIncome, lblExpend, lblDate;

    @FXML
    private TreeView<String> treeViewTransactionList;
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    private Map<TreeItem<String>, Transaction> treeItemToTransactionMap = new HashMap<>();

    private static InitialController instance;

    public InitialController() {
        instance = this;
    }

    public static InitialController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblDate.setText(LocalDate.now().toString()); // setting the date to now
        loadTransactionsFromDatabase(); // Load existing transactions
        updateTotals(); // Update total expand or income number
    }

    private void loadTransactionsFromDatabase() {
        TreeItem<String> root = new TreeItem<>("Root");
        treeViewTransactionList.setRoot(root);
        treeViewTransactionList.setShowRoot(false);

        Map<YearMonth, List<Transaction>> transactionsByMonth = Database.getOrderedDescTransactions().stream()
                .collect(Collectors.groupingBy(transaction -> YearMonth.from(transaction.getDate())));

        transactionsByMonth.forEach((yearMonth, transactions) -> {
            TreeItem<String> yearItem = new TreeItem<>(yearMonth.getYear() + "");
            boolean yearExists = false;
            for (TreeItem<String> child : root.getChildren()) {
                if (child.getValue().equals(yearItem.getValue())) {
                    yearItem = child;
                    yearExists = true;
                    break;
                }
            }
            if (!yearExists) {
                root.getChildren().add(yearItem);
            }

            TreeItem<String> monthItem = new TreeItem<>(yearMonth.format(DateTimeFormatter.ofPattern("MMMM")));
            yearItem.getChildren().add(monthItem);

            transactions.forEach(transaction -> {
                String transactionDetails = String.format("Type: %s, Bank: %s, Amount: %.2f, Category: %s, Note: %s, Day: %s",
                        transaction.getType(), transaction.getBankName(), transaction.getAmount(),
                        transaction.getCategory(), transaction.getNote(), transaction.getDate().format(DateTimeFormatter.ofPattern("dd")));
                TreeItem<String> transactionItem = new TreeItem<>(transactionDetails);
                monthItem.getChildren().add(transactionItem);
                // Map each transaction TreeItem to its Transaction object
                treeItemToTransactionMap.put(transactionItem, transaction);
            });

        });
    }



    // Method to find a Transaction by TreeItem
    private Transaction findTransactionByTreeItem(TreeItem<String> treeItem) {
        return treeItemToTransactionMap.get(treeItem); // Direct mapping of TreeItem to Transaction
    }


    public void updateTotals() {
        double totalIncome = 0.0;
        double totalExpense = 0.0;
        List<Transaction> transactions = Database.getAllTransactions();

        for (Transaction transaction : transactions) {
            if ("Income".equals(transaction.getType())) {
                totalIncome += transaction.getAmount();
            } else if ("Expense".equals(transaction.getType())) {
                totalExpense += transaction.getAmount();
            }
        }

        lblIncome.setText(String.format("%.2f$", totalIncome));
        lblExpend.setText(String.format("%.2f$", totalExpense));
    }


    @FXML
    private void onAddButtonClick() {
        try {
            PersonalFinanceManager.setRoot("expend.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onReportButtonClick() {
        try {
            PersonalFinanceManager.setRoot("reportPage.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBankCardsButtonClick() {
        try {
            PersonalFinanceManager.setRoot("bankCardsPage.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onModifyButtonClick() {

        TreeItem<String> selectedItem = treeViewTransactionList.getSelectionModel().getSelectedItem();
        if (selectedItem == null || selectedItem.getParent() == null || selectedItem.getParent().getParent() == null || selectedItem.getParent().getParent() == treeViewTransactionList.getRoot())
        {
            // Either nothing is selected, or the selected item is not a transaction (e.g., a month header)
            AlertUtil.showInfo("Information", "Please select a transaction to modify.");
        }
        else
        {
            Transaction selectedTransaction = findTransactionByTreeItem(selectedItem);
            try {
                // Assuming ModifyController has a static method to set the current transaction ID
                ModifyController.setCurrentTransactionId(selectedTransaction.getId());

                // Change scene to modify.fxml
                PersonalFinanceManager.setRoot("modify.fxml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void onDeleteButtonClick() {

        TreeItem<String> selectedItem = treeViewTransactionList.getSelectionModel().getSelectedItem();
        if (selectedItem == null || selectedItem.getParent() == null || selectedItem.getParent().getParent() == null || selectedItem.getParent().getParent() == treeViewTransactionList.getRoot())
        {
            // Either nothing is selected, or the selected item is not a transaction (e.g., a month header)
            AlertUtil.showInfo("Information", "Please select a transaction to modify.");
        }
        else
        {
            // Confirmation dialog
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this transaction?", ButtonType.YES, ButtonType.NO);
            confirmAlert.setTitle("Confirm Deletion");
            Optional<ButtonType> result = confirmAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES)
            {
                Transaction selectedTransaction = findTransactionByTreeItem(selectedItem);

                // Delete the transaction from the database
                Database.deleteTransaction(selectedTransaction.getId());
                // Remove the transaction from the ListView
                transactions.remove(selectedTransaction);

                loadTransactionsFromDatabase(); // Load existing transactions
                updateTotals(); // Update total expand or income number
            }
        }

    }


}
