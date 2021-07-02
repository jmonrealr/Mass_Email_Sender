package com.snorlax;

import javafx.scene.control.Alert;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.PropertiesConfigurationLayout;
import org.apache.xml.security.stax.config.ConfigurationProperties;

import javax.naming.ConfigurationException;
import java.io.*;
import java.util.Properties;

/**
 * Class Configuration used to control the Configuration/Properties of the App
 */
public class Configuration {
    private static ConfigurationProperties defaultProps = new ConfigurationProperties("configuration.properties");
    private InputStream inputStream;

    /**
     * Loads the configuration and made available to all class of the app
     */
    static {
        PropertiesConfiguration props = new PropertiesConfiguration();
        props.
        try (FileInputStream in = new FileInputStream("configuration.properties")) {
            defaultProps.load(in);
        } catch (IOException e) {
            Alerts.showAlertMessage(Alert.AlertType.ERROR, "Error in Configuration", "Something is wrong with the configuration properties, please check!");
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

    /**
     * Sets a new value to one property
     * @param key of the property
     * @param value is the new value of the property
     */
    public static void setProperty(String key, String value){
        defaultProps.setProperty(key, value);
        PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout();
        defaultProps.setLayout(layout);
        layout.load(config, new FileReader("/src/main/java/resources/config/configuration.properties"));
        config.setProperty(key, value);
        StringWriter stringWriter = new StringWriter();
        layout.save(config, stringWriter);
        System.out.println("Properties " + stringWriter.toString());
    }
    /* Can be used?
    public static void loadConfig() throws IOException {
        try {
            Properties properties = new Properties();
            String path = "configuration.properties";
            InputStream inputStream = Configuration.class.getResourceAsStream(path);
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
