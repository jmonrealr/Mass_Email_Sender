package com.snorlax;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Start driver of the APP
 * @author Snorlax Squad
 * @version 1.0
 */
public class App extends Application {
    private static Scene scene;

    /**
     * The main class for a JavaFX application extends the javafx.application.Application
     * class. The start() method is the main entry point for all JavaFX applications.
     *
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        scene = new Scene(loadFXML("Login"));
        primaryStage.getIcons().add(AppIcon.getIcon());
        primaryStage.setTitle("Sistema de Envio de Correos Masivos");
        primaryStage.setScene(this.scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(windowEvent -> exit(primaryStage));
    }

    /**
     * Load an fxml file using the Class.getResource
     * @param fxml file name
     * @return fxml file loaded
     * @throws IOException when the file is not founded
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Sets the root Scene at the moment its needed
     * @param fxml file name to be loaded
     * @throws IOException if something goes wrong
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    @Override
    public void stop() {
        //System.out.println("Doing what has to be done before closing");
    }

    /**
     *  The main() method is not required for JavaFX applications when the JAR file for the
     *  application is created with the JavaFX Packager tool, which embeds the JavaFX Launcher in
     *  the JAR file. However, it is useful to include the main() method so you can run JAR files that
     *  were created without the JavaFX Launcher, such as when using an IDE in which the JavaFX
     *  tools are not fully integrated. Also, Swing applications that embed JavaFX code require the
     *  main() method.
     *  @param args Command line parameters are not used
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     *  Exits the application
     * @param stage used
     */
    private void exit(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to exit!");
        alert.setContentText("Do you want to exit?");
        Stage stage2 = (Stage) alert.getDialogPane().getScene().getWindow();
        stage2.getIcons().add(AppIcon.getIcon());
        if (alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }
}
