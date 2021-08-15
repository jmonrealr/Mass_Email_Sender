package com.snorlax;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

/**
 * This is the MainController for the whole App
 */
public class Controller implements Initializable {
    @FXML
    private AnchorPane center;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Label lbl_background;
    @FXML
    private Spinner<Integer> hour, minute;
    @FXML
    private Label lbl_min, lbl_hour;
    @FXML
    private DatePicker datePicker = new DatePicker();
    @FXML
    private HTMLEditor htmlEditor;
    @FXML
    private TitledPane loadedKeys;
    @FXML
    private TitledPane loadedFiles;
    @FXML
    private Accordion accordion;
    @FXML
    private ListView listFiles = new ListView();
    @FXML
    private ListView listKeys = new ListView();
    @FXML
    private TableColumn tableKeys;
    @FXML
    private TableView tableView;
    @FXML
    private AnchorPane anchorData;
    @FXML
    private MenuItem scheduleSend;
    @FXML
    private SplitMenuButton sendMenu;
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
    //@FXML
    //private TextArea emailMessageField;
    @FXML
    private Button sendEmailButton;
    @FXML
    private Label sentBoolValue;
    @FXML
    private Button emailInsertFileButton;
    @FXML
    private Button emailInsertImageButton;

    private Parent root;
    private Scene scene;
    private Stage stage;
    private Session session;
    private ArrayList<File> files = new ArrayList<>();
    private Transport transport;
    private ArrayList<String> send = new ArrayList<>();
    private LinkedHashMap<String, List<String>> dataExcelLHM;
    private boolean hasExcel = false;
    private boolean hasDate = false;
    private List<List<String>> dataExcel;
    private List<String> variables = new ArrayList<>();
    private ObservableList<String> filesNames = FXCollections.observableArrayList();
    private ObservableList<String> currentKeys = FXCollections.observableArrayList();
    private Date date = null;

    /**
     * Initilize some Objects of the Scene
     * @param url current
     * @param resourceBundle current
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorData.setVisible(true);
        this.listFiles.setItems(this.filesNames);
        this.listKeys.setItems(this.currentKeys);
        this.datePicker.setVisible(false);
        SpinnerValueFactory<Integer> hour = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23);
        hour.setValue(8);
        SpinnerValueFactory<Integer> minute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59);
        minute.setValue(8);
        this.hour.setValueFactory(hour);
        this.hour.setVisible(false);
        this.minute.setValueFactory(minute);
        this.minute.setVisible(false);
        this.lbl_hour.setVisible(false);
        this.lbl_min.setVisible(false);
        this.colorPicker.setVisible(false);
        this.lbl_background.setVisible(false);
        this.colorPicker.valueProperty().addListener((observable -> {
            center.setStyle(
                    "-fx-background-color: #" + colorPicker.getValue().toString().substring(2,8) + ";"
            );
        }));
    }

    /**
     * Handled all actions and validations to send an Email
     * @param actionEvent When the button is clicked
     */
    public void send(ActionEvent actionEvent) {
        String subject = this.emailSubjectField.getText();
        String sent = this.emailToField.getText();
        String message = this.htmlEditor.getHtmlText();
        String code = this.htmlCode.getText();
        if(hasDate){
            LocalDate date1 = datePicker.getValue();
            Calendar c = Calendar.getInstance();
            c.set(date1.getYear(), date1.getMonthValue()-1, date1.getDayOfMonth());
            this.date = c.getTime();
        }else
            this.date = new Date();
        if (subject.isBlank() || message.isBlank() || (!hasExcel && sent.isBlank())){
            Alerts.showAlertMessage(Alert.AlertType.WARNING, "Missing information", "Some Fields are empty");
        }else{
            if (!sent.isBlank()){
                String str[];
                if (sent.contains(",")){
                    str = sent.split(",");
                    for (String email: str) {
                        this.send.add(email);
                    }
                }else{
                    this.send.add(sent);
                }
            }
            if(this.hasExcel){ //with some variables to be replaced in excel
                Analyzer analyzer = new Analyzer(message);
                for (int i = 0; i < this.send.size()-1 || i <= 100; i++) {
                    String email = this.send.get(i);
                    if ( i+1 < this.dataExcel.size()){// i+1 to ignore Index 0 because has the column Names
                        //System.out.println("Llamadas Analyzer + " + analyzer.replace(this.variables, this.dataExcel.get(i+1)));
                        if (!code.isBlank()){
                            ConvertToPDF convert = new ConvertToPDF();
                            String pdf = null;
                            Analyzer codeAnalyzer = new Analyzer(code, "<", ">");
                            String newCode = codeAnalyzer.replace(this.variables, this.dataExcel.get(i+1));
                            try {
                                pdf = convert.convert(newCode);
                                File file = new File(pdf);
                                this.files.add(file);
                            } catch (IOException e) {
                                Alerts.showAlertMessage(Alert.AlertType.ERROR, "Error in html code", "Something went wrong in html code to pdf\n" + e.getMessage());
                            }
                        }
                        String newMessage = analyzer.replace(this.variables, this.dataExcel.get(i+1));
                        try {
                            EmailSenderService sender = new EmailSenderService(this.transport,this.session, subject, email, newMessage, files, this.date);
                            sender.sendMessage();
                        } catch (MessagingException e) {
                            Alerts.showAlertMessage(Alert.AlertType.ERROR, "Messaging Exception", "Error sending message to" + email +"\n" + e.getMessage());
                        }
                    }
                }
            }else{// Just a simple email
                if (!code.isBlank()){
                    ConvertToPDF convert = new ConvertToPDF();
                    String pdf = null;
                    try {
                        pdf = convert.convert(code);
                        File file = new File(pdf);
                        this.files.add(file);
                    } catch (IOException e) {
                        Alerts.showAlertMessage(Alert.AlertType.ERROR, "Error in html code", "Something went wrong in html code to pdf\n" + e.getMessage());
                    }
                }
                for (String email: this.send) {
                    try {
                        EmailSenderService sender = new EmailSenderService(this.transport,this.session, subject, email, message, files, this.date);
                        sender.sendMessage();
                    } catch (MessagingException e) {
                        Alerts.showAlertMessage(Alert.AlertType.ERROR, "Messaging Exception", "Error sending message to" + email +"\n" + e.getMessage());
                    }
                }
            }
            Alerts.showAlertMessage(Alert.AlertType.CONFIRMATION, "All Message has been sent", "All message has been sent and received");
            this.emailSubjectField.setText("");
            this.emailToField.setText("");
            this.htmlEditor.setHtmlText("");
            this.files.clear();
            this.send.clear();
            this.htmlCode.clear();
            this.dataExcel = null;
            this.dataExcelLHM = null;
            this.filesNames.clear();
            this.currentKeys.clear();
            this.variables.clear();
            this.hasExcel = false;
            this.hasDate = false;
            this.hour.setVisible(false);
            this.minute.setVisible(false);
            this.lbl_hour.setVisible(false);
            this.lbl_min.setVisible(false);
        }
    }

    /**
     * Attach Files with FileChooser to Email
     * @param actionEvent Button Attach File
     */
    public void attachFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Attach File");
        Stage stage = (Stage) vbox.getScene().getWindow();
        List<File> list = fileChooser.showOpenMultipleDialog(stage);
        if (list != null){
            for (File file: list) {
                this.files.add(file);
            }
            for (File file:this.files) {
                this.filesNames.add(file.getName());
            }
        }
    }

    /**
     * Inserts as Template HTML Code in the HTML EDITOR
     * @param actionEvent from insert HTML button in Scene
     */
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
        stage.getIcons().add(AppIcon.getIcon());
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
        stage.close();
        if (!key.isBlank() || !value.isBlank()){
            TextInputDialog inputDialog = new TextInputDialog(value);
            inputDialog.setTitle("Enter a value");
            inputDialog.setHeaderText("Please fill the input");
            inputDialog.setContentText("Please enter the new value of " + key);
            Stage stage1 = (Stage) inputDialog.getDialogPane().getScene().getWindow();
            stage1.getIcons().add(AppIcon.getIcon());
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

    public void changeColors(ActionEvent actionEvent) throws IOException {
        /*Stage currenStage = (Stage) this.changeColors.getGraphic().getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("exampleDynamicCss.fxml"));
        System.out.println(getClass().getResource("exampleDynamicCss.fxml"));
        currenStage.setScene(new Scene(loader.load()));*/
        this.colorPicker.setVisible(true);
        this.lbl_background.setVisible(true);
    }

    public void changeIcon(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Change Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        Stage stage = (Stage) vbox.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file == null){
            Alerts.showAlertMessage(Alert.AlertType.ERROR,"Error", "Something is wrong with the file\n Please check again!");
            return;
        }else {
            if (!file.isFile()){
                Alerts.showAlertMessage(Alert.AlertType.ERROR,"Error", "Something is wrong with the file\n Please check again!");
                return;
            }else
                Alerts.showAlertMessage(Alert.AlertType.INFORMATION, "Information", "Icon Saved");
        }
    }

    public void loadConfig(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Configuration Files", "*.properties"));
        Stage stage = (Stage) vbox.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file == null){
            Alerts.showAlertMessage(Alert.AlertType.ERROR, "ERROR", "Empty properties file");
            return;
        }else {
            Alerts.showAlertMessage(Alert.AlertType.INFORMATION, "Information", "Properties loaded");
        }
    }

    /**
     *  Close the actual session and switch to Login Scene
     * @param actionEvent when Logout is submitted
     */
    public void logout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you want to logout?");
        Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
        stage2.getIcons().add(AppIcon.getIcon());
        if (alert.showAndWait().get() == ButtonType.OK){
            try {
                this.session = null;
                this.transport = null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                root  = loader.load();
                stage = (Stage) vbox.getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                Alerts.showAlertMessage(Alert.AlertType.ERROR, "An I/O exception has occurred", "Exception produced by failed or interrupted I/O operations" + e.getMessage());
            }
        }
    }

    /**
     * Calls {@link Config} to get all properties
     * @param actionEvent from show buttons menu Item
     */
    public void showProps(ActionEvent actionEvent) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("",Config.getPropertiesName());
        dialog.setTitle("Properties");
        dialog.setHeaderText("You can see the properties");
        dialog.setContentText("Property");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(AppIcon.getIcon());
        Optional<String> result = dialog.showAndWait();
    }

    /**
     * Call {@link ReadExcel} to load and read data from Excel file
     * This file is selected from one fileChooser
     * @param actionEvent Button Load Excel
     */
    public void loadExcel(ActionEvent actionEvent) {
        this.hasExcel = true;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Attach File");
        Stage stage = (Stage) vbox.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if (file == null){
            return;
        }else {
            if (!file.isFile()){
                Alerts.showAlertMessage(Alert.AlertType.ERROR,"Error", "Something is wrong with the file\n Please check again!");
                return;
            }
            ReadExcel readExcel = new ReadExcel(file);
            try {
                this.dataExcelLHM = readExcel.getDataAsLHM();
                this.dataExcel = readExcel.getDataAsList();
                //ObservableList list = (ObservableList) this.dataExcel.keySet();
                //ObservableList list = FXCollections.observableArrayList(new ArrayList<>(this.dataExcelLHM.keySet()));
                //tableView.setItems((ObservableList)  new ArrayList<>(this.dataExcel.keySet()));
                this.variables = this.dataExcel.get(0);
                //System.out.println("Vars in Excel" + variables.toString());
                if (this.dataExcelLHM.containsKey("CORREO")) {
                    List<String> temp = this.dataExcelLHM.get("CORREO");
                    //System.out.println("Correos Excel" + temp.toString());
                    for (String email: temp) {
                        this.send.add(email);
                    }
                }else{
                    Alerts.showAlertMessage(Alert.AlertType.WARNING, "Warning","The Excel loaded don't have a Column called \"CORREO\" \n" + "Removing the data");
                    return;
                }
                for (String var :this.variables) {
                    this.listKeys.getItems().add(var);
                    //this.currentKeys.add(var);
                }
                //System.out.println("variables " + this.variables.toString());
                //System.out.println("Current Keys" + this.currentKeys.toString());
                //System.out.println("List view keys " + listKeys.toString());
                anchorData.setVisible(true);
            } catch (IOException e) {
                this.hasExcel = false;
                Alerts.showAlertMessage(Alert.AlertType.ERROR,"Error", "Something is wrong with the file\n Please check again!");
            }
        }
    }

    /**
     * Schedule Send with datepicker
     * @param actionEvent button clicked
     */
    public void scheduleSend(ActionEvent actionEvent) {
        this.datePicker.setVisible(true);
        this.hour.setVisible(true);
        this.minute.setVisible(true);
        this.lbl_hour.setVisible(true);
        this.lbl_min.setVisible(true);
        this.hasDate = true;
    }


    /** @deprecated
     * When "enter" is pressed, get current CaretPosition from the message and insert text
     * with the html tag <br>
     * @param key pressed from the keyboard
     */
    public void keyPressed(KeyEvent key) {
        /*
        if (key.getCode().equals(KeyCode.ENTER)){
            int cursor = emailMessageField.getCaretPosition();
            emailMessageField.insertText(cursor-1, " <br>\n");
        }*/
    }
}