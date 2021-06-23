package com.snorlax;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

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
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL path = new URL("file:src/main/resources/com.snorlax/Login.fxml");
        System.out.println(path.toString());
        fxmlLoader.setLocation(path);
        scene = new Scene(fxmlLoader.load());
        //Icon
        Image image = new Image("file:src/main/resources/images/upv-bis.png");
        primaryStage.getIcons().add(image);
        primaryStage.setTitle("Sistema de Envio de Correos Masivos");
        primaryStage.setScene(this.scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * It's supposed to load an fxml file but App.class.getResource don't works
     * @param fxml file name
     * @return fxml file loaded
     * @throws IOException when the file is not founded
     */
    private static Parent loadFXML(String fxml) throws IOException {
        System.out.println(App.class.getResource( fxml + ".fxml"));
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
