package com.snorlax;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
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
        /* 1st way
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL path = new URL("file:src/main/resources/com.snorlax/filename.fxml");
        System.out.println(path.toString());
        fxmlLoader.setLocation(path);
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("OctaFake");
        stage.show();
         */

        /*
         * Second way
         */
        //scene = new Scene(loadFXML("filename"));
        primaryStage.setTitle("Primary stage");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    /**
     *
     * @param fxml
     * @return
     * @throws IOException
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        System.out.println("loading fxml " + fxmlLoader.toString());
        return fxmlLoader.load();
    }
    @Override
    public void stop() {
        System.out.println("Doing what has to be done before closing");
    }

    /**
     *  The main() method is not required for JavaFX applications when the JAR file for the
     *  application is created with the JavaFX Packager tool, which embeds the JavaFX Launcher in
     *  the JAR file. However, it is useful to include the main() method so you can run JAR files that
     *  were created without the JavaFX Launcher, such as when using an IDE in which the JavaFX
     *  tools are not fully integrated. Also, Swing applications that embed JavaFX code require the
     *  main() method.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
