package com.snorlax;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * This is the MainController for the whole App
 */
public class Controller {
    @FXML
    private Label lbl_sent;
    @FXML
    private TextField emailToField;
    @FXML
    private Label lbl_subject;
    @FXML
    private Label lbl_message;
    @FXML
    private TextField emailSubjectField;
    @FXML
    private TextArea emailMessageField;
    @FXML
    private Button sendEmailButton;
    @FXML
    private Label sentBoolValue;
    @FXML
    private Button emailInsertFileButton;
    @FXML
    private Button emailInsertImageButton;
    @FXML
    private Button toPDF;

    /**
     * Sending email
     * @param actionEvent When the button is clicked
     */
    public void buttonClicked(ActionEvent actionEvent) {
        //Do something
    }
}
