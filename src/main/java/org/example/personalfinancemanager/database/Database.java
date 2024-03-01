package org.example.personalfinancemanager.database;

import org.example.personalfinancemanager.model.Transaction;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String URL = "jdbc:sqlite:finance.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            // SQL statement for creating a new table
            String sql = "CREATE TABLE IF NOT EXISTS transactions ("
                    + "	id integer PRIMARY KEY,"
                    + "	type text NOT NULL,"
                    + "	bankName text,"
                    + "	amount real NOT NULL,"
                    + "	category text NOT NULL,"
                    + "	note text,"
                    + "	paymentDate text NOT NULL"
                    + ");";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to modify a transaction
    public static void modifyTransaction(Transaction transaction) {
        String sql = "UPDATE transactions SET type = ?, bankName = ?, amount = ?, category = ?, note = ?, paymentDate = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, transaction.getType());
            pstmt.setString(2, transaction.getBankName());
            pstmt.setDouble(3, transaction.getAmount());
            pstmt.setString(4, transaction.getCategory());
            pstmt.setString(5, transaction.getNote());
            pstmt.setString(6, transaction.getDate().toString());
            pstmt.setInt(7, transaction.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to delete a transaction
    public static void deleteTransaction(int id) {
        String sql = "DELETE FROM transactions WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to add a transaction
    public static void insertTransaction(String type, String bankName, double amount, String category, String note, LocalDate date) {
        String sql = "INSERT INTO transactions (type, bankName, amount, category, note, paymentDate) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, type);
                pstmt.setString(2, bankName);
                pstmt.setDouble(3, amount);
                pstmt.setString(4, category);
                pstmt.setString(5, note);
                pstmt.setString(6, date.toString());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static List<Transaction> getAllTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        String sql = "SELECT * FROM transactions";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("bankName"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getString("note"),
                        LocalDate.parse(rs.getString("paymentDate"), DateTimeFormatter.ISO_DATE)
                );
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return transactionList;
    }

    public static List<Transaction> getOrderedDescTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY paymentDate DESC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("bankName"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getString("note"),
                        LocalDate.parse(rs.getString("paymentDate"), DateTimeFormatter.ISO_DATE)
                );
                transactionList.add(transaction);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return transactionList;
    }


    public static Transaction getTransactionById(int transactionId) {
        Transaction transaction = null;
        String sql = "SELECT * FROM transactions WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, transactionId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                transaction = new Transaction(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("bankName"),
                        rs.getDouble("amount"),
                        rs.getString("category"),
                        rs.getString("note"),
                        LocalDate.parse(rs.getString("paymentDate"))
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return transaction;
    }

}
