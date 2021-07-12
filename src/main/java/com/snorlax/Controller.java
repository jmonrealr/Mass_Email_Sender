package com.snorlax;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * This is the MainController for the whole App
 */
public class Controller {
    @FXML
    private MenuItem logout;
    @FXML
    private MenuItem changeIcon;
    @FXML
    private MenuItem changeColors;
    @FXML
    private Button loadExcel;
    @FXML
    private MenuItem loadConfig;
    @FXML
    private MenuItem properties;
    @FXML
    private TextArea htmlCode;
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

    private Session session;
    private List<File> files = new ArrayList<>();
    private Transport transport;
    private ArrayList<String> send = new ArrayList<>();
    private LinkedHashMap<String, List<String>> dataExcel;
    private boolean flag = false;

    /**
     * Sending email button
     * @param actionEvent When the button is clicked
     */
    public void send(ActionEvent actionEvent) {
        String subject = this.emailSubjectField.getText();
        String sent = this.emailToField.getText();
        String message = this.emailMessageField.getText();
        String code = this.htmlCode.getText();
        if (subject.isBlank() || sent.isBlank() || message.isBlank()){
            Alerts.showAlertMessage(Alert.AlertType.WARNING, "Missing information", "Some Fields are empty");
        }else{
            String str[];
            if (sent.contains(",")){
                str = sent.split(",");
                for (String email: str) {
                    this.send.add(email);
                }
            }else{
                this.send.add(sent);
            }
            //EmailSenderService email = new EmailSenderService(this.transport,this.session, subject, sent, message, files);
            //System.out.println(files.toString());
            if (files == null || files.isEmpty()){
                files = null;
                System.out.println("Files empty");
            }
            EmailSenderService email2 = new EmailSenderService(this.transport,this.session, subject, this.send, message, files);
            try {
                email2.sendMessage();
                Thread.sleep(1000);
                this.emailSubjectField.setText("");
                this.emailToField.setText("");
                this.emailMessageField.setText("");
                //System.out.println(files);
                if (this.files != null){
                    this.files.clear();
                }
                this.send.clear();
                this.htmlCode.clear();
                Alerts.showAlertMessage(Alert.AlertType.CONFIRMATION, "Message has been sent", "The message has been sent and received");
            } catch (MessagingException | InterruptedException e) {
                Alerts.showAlertMessage(Alert.AlertType.ERROR, "Messaging Exception", "Error sending message! \n" + e.getMessage());
            }
        }
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
        String message = this.htmlCode.getText();
        for (String word: template) {
            message += word;
        }
        this.htmlCode.setText(message);
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

    /**
     * Sets the transport and session object to be used when sending messages
     * @param transport with the session via smtp protocol
     * @param session with the properties and authenticator
     */
    public void setTransport(Transport transport, Session session){
        this.transport = transport;
        this.session = session;
    }

    public void changeColors(ActionEvent actionEvent) {
    }

    public void changeIcon(ActionEvent actionEvent) {
    }

    public void loadConfig(ActionEvent actionEvent) {
    }

    /**
     *
     * @param actionEvent
     */
    public void logout(ActionEvent actionEvent) {
        try {
            this.session = null;
            this.transport = null;
            App.setRoot("login");
        } catch (IOException e) {
            Alerts.showAlertMessage(Alert.AlertType.ERROR,"Some error", "Error during logout \n" + e.getMessage());
        }
    }

    public void showProps(ActionEvent actionEvent) {
    }

    public void loadExcel(ActionEvent actionEvent) {
        this.flag = true;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Attach File");
        Stage stage = (Stage) vbox.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file == null){
            return;
        }else {
            if (!file.isFile() || (!file.getName().endsWith(".xlsx") || !file.getName().endsWith("xls") )){
                Alerts.showAlertMessage(Alert.AlertType.ERROR,"Error", "Something is wrong with the file\n Please check again!");
                return;
            }
            ReadExcel readExcel = new ReadExcel(file);
            try {
                this.dataExcel = readExcel.getDataAsLHM();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
