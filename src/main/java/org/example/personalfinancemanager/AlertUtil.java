package org.example.personalfinancemanager;

import javafx.scene.control.Alert;

public class AlertUtil {

    public static void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showError(String title, String content) {
        showAlert(Alert.AlertType.ERROR, title, null, content);
    }

    public static void showInfo(String title, String content) {
        showAlert(Alert.AlertType.INFORMATION, title, null, content);
    }
}

