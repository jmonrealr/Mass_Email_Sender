package com.snorlax;
import javafx.scene.control.Alert;

public class Alerts {
    /**
     * Create an alert as needed
     * @param alertType Type of alert {Error, Warning, Info, Confirmation, Warning}
     * @param title of the alert
     * @param message of the alert
     */
    public static void showAlertMessage(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

}
