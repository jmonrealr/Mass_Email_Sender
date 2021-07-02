package com.snorlax;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.mail.Session;
import javax.mail.Transport;
import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * This is the MainController for the whole App
 */
public class Controller {
    @FXML
    private MenuItem preferences;
    @FXML
    private MenuItem exit;
    @FXML
    private VBox vbox;
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

    private Session session;
    private List<File> files;
    private Transport transport;

    /**
     * Sending email button
     * @param actionEvent When the button is clicked
     */
    public void send(ActionEvent actionEvent) {
        String subject = this.emailSubjectField.getText();
        String sent = this.emailToField.getText();
        String message = this.emailMessageField.getText();
        if (subject.isBlank() || sent.isBlank() || message.isBlank()){
            Alerts.showAlertMessage(Alert.AlertType.WARNING, "Missing information", "Some Fields are empty");
        }else{

        }
    }

    public void htmlToPdf(ActionEvent actionEvent) {
        
    }

    public void attachFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Attach File");
        Stage stage = (Stage) vbox.getScene().getWindow();
        List<File> list = fileChooser.showOpenMultipleDialog(stage);
        if (list != null){
            for (File file : list){
                System.out.println(file.toString());
            }
            this.files = list;
        }
    }

    public void insertHtml(ActionEvent actionEvent) {
        String []template = {
                "<!DOCTYPE html>\n",
                "<html lang=\"es\">\n",
                "<head>",
                "<meta charset=\"UTF-8\">\n",
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n",
                "<title>Template</title>\n",
                "</head>\n",
                "<body>\n",
                "Message \n",
                "</body>\n",
                "</html>\n"
        };
        String message = this.emailMessageField.getText();
        for (String word: template) {
            message += word;
        }
        this.emailMessageField.setText(message);
    }

    /**
     * Exit the app
     * @param actionEvent when MenuItem exit is pressed
     */
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }
    /**
     *  Opens a ChoiceDialog with the properties with the possibility to be changed with one TextInputDialog
     * @param actionEvent when MenuItem Preferences is pressed
     */
    public void configuration(ActionEvent actionEvent) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("",Config.getPropertiesName());
        dialog.setTitle("Choice a property");
        dialog.setHeaderText("Look at the properties");
        dialog.setContentText("Choose your property");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(IconImage.getIcon());
        Optional<String> result = dialog.showAndWait();
        String key = "", value = "";
        if (result.isPresent()){
            key = result.get();
            value = Config.getProperty(key);
            System.out.println("Key " + key);
            System.out.println("Value " + value);
            dialog.hide();
            dialog.close();
        }
        if (!key.isBlank() || !value.isBlank()){
            TextInputDialog inputDialog = new TextInputDialog(value);
            inputDialog.setTitle("Enter a value");
            inputDialog.setHeaderText("Please fill the input");
            inputDialog.setContentText("Please enter the new value of " + key);
            Stage stage1 = (Stage) inputDialog.getDialogPane().getScene().getWindow();
            stage1.getIcons().add(IconImage.getIcon());
            Optional<String> temp = dialog.showAndWait();
            if (temp.isPresent()){
                String newValue = temp.get();
                Config.setProperty(key, newValue);
            }
        }

    }

    public void setTransport(Transport transport){
        this.transport = transport;
    }

    public void setSesion(Session session){
        this.session = session;
    }
}
