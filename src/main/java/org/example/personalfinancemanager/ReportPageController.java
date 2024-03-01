package org.example.personalfinancemanager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import org.example.personalfinancemanager.database.Database;
import org.example.personalfinancemanager.model.Transaction;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ReportPageController implements Initializable {

    @FXML
    private PieChart incomeChart;

    @FXML
    private PieChart expenseChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    private void loadData() {
        List<Transaction> transactions = Database.getAllTransactions(); // Assuming this method exists and returns all transactions

        Map<String, List<Transaction>> transactionsByType = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getType));

        transactionsByType.forEach((type, transList) -> {
            ObservableList<PieChart.Data> pieChartData = transList.stream()
                    .collect(Collectors.groupingBy(Transaction::getCategory, Collectors.summingDouble(Transaction::getAmount)))
                    .entrySet()
                    .stream()
                    .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));

            if ("Income".equals(type)) {
                incomeChart.setData(pieChartData);
            } else if ("Expense".equals(type)) {
                expenseChart.setData(pieChartData);
            }
        });
    }

    // Example method to fetch data. Replace this with actual data fetching logic.
    private Map<String, Double> fetchDataForReport() {
        // Your data fetching logic here
        return Map.of(); // Return your fetched data as a map
    }


    @FXML
    private void handleBackAction() throws IOException {
        // Simply change the scene to go back
        PersonalFinanceManager.setRoot("initialPage.fxml");
    }
}
