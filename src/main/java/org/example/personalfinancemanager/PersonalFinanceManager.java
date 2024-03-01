package org.example.personalfinancemanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.personalfinancemanager.database.Database;

import java.io.IOException;
import java.util.Locale;


public class PersonalFinanceManager extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {

        // Initialize the database
        Database.initializeDatabase();

        primaryStage = stage;
        setRoot("initialPage.fxml");
        primaryStage.show();
        primaryStage.setTitle("Personal Finance Manager");
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(PersonalFinanceManager.class.getResource(fxml));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {

        //Make sure language is english
        Locale.setDefault(Locale.ENGLISH);

        launch(args);
    }
}