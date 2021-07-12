package com.snorlax;

import javafx.scene.control.Alert;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.PropertiesConfigurationLayout;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Class Config used to control the Config/Properties of the App
 */
public class Config {
    private static Properties defaultProps = new Properties();
    /**
     * Loads the configuration and made available to all class of the app
     */
    static {
        try (FileInputStream in = new FileInputStream(Config.class.getResource("../../config/configuration.properties").getPath())) {
            defaultProps.load(in);
        } catch (IOException e) {
            Alerts.showAlertMessage(Alert.AlertType.ERROR, "Error in Config", "Something is wrong with the configuration properties, please check! \n" +  e.getMessage());
        }
    }
    /**
     * Used to get one requested property
     * @param key of the property
     * @return String value of the requested property
     */
    public static String getProperty(String key){
        return defaultProps.getProperty(key);
    }
    public static List<String> getPropertiesName(){
        return defaultProps.stringPropertyNames().stream().collect(Collectors.toList());
    }
    /**
     * Sets a new value to one property
     * @param key of the property
     * @param newValue of the property to be saved
     */
    public static void setProperty(String key, String newValue){
        if (defaultProps.containsKey(key)){
            try {
                defaultProps.setProperty(key, newValue);
                PropertiesConfiguration config = new PropertiesConfiguration();
                PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout();
                config.setLayout(layout);
                layout.load(config, new FileReader("../../config/configuration.properties"));
                config.setProperty(key, newValue);
                StringWriter stringWriter = new StringWriter();
                layout.save(config, stringWriter);
            } catch (ConfigurationException | FileNotFoundException e) {
                Alerts.showAlertMessage(Alert.AlertType.ERROR, "Error in Config", "Something is wrong with the configuration properties, please check\n!" +  e.getMessage());
            }
        }else {
            Alerts.showAlertMessage(Alert.AlertType.ERROR, "Missing property", "The property " + key + "is missing");
        }

    }
    /* Can be used?
    public static void loadConfig() throws IOException {
        try {
            Properties properties = new Properties();
            String path = "configuration.properties";
            InputStream inputStream = Config.class.getResourceAsStream(path);
            if (inputStream != null){
                properties.load(inputStream);
            }else {
                Alerts.showAlertMessage(Alert.AlertType.ERROR, "Error in Properties File", "Property file " + path + "not found in classpath");
                throw new FileNotFoundException();
            }
            System.out.println("Properties loaded" + properties.toString());
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeProp(String key, String value) throws ConfigurationException, FileNotFoundException, org.apache.commons.configuration2.ex.ConfigurationException {
        PropertiesConfiguration config = new PropertiesConfiguration();
        PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout();
        config.setLayout(layout);
        layout.load(config, new FileReader("/src/main/java/resources/config/configuration.properties"));
        config.setProperty(key, value);
        StringWriter stringWriter = new StringWriter();
        layout.save(config, stringWriter);
        System.out.println("Properties " + stringWriter.toString());
    }
     */
}
