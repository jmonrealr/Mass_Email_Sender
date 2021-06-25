package com.snorlax;

import javafx.scene.control.Alert;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.PropertiesConfigurationLayout;

import javax.naming.ConfigurationException;
import java.io.*;
import java.util.Properties;

/**
 * Class Configuration used to control the Configuration/Properties of the App
 */
public class Configuration {
    private String result;
    private InputStream inputStream;

    public String getPropValues() throws IOException {
        try {
            Properties properties = new Properties();
            String path = "configuration.properties";
            this.inputStream = getClass().getResourceAsStream(path);
            if (inputStream != null){
                properties.load(inputStream);
            }else {
                Alerts.showAlertMessage(Alert.AlertType.ERROR, "Error in Properties File", "Property file " + path + "not found in classpath");
                throw new FileNotFoundException();
            }
            System.out.println("Properties loaded" + properties.toString());
            this.result = properties.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            this.inputStream.close();
        }
        return result;
    }

    public void changeProp(String key, String value) throws ConfigurationException, FileNotFoundException, org.apache.commons.configuration2.ex.ConfigurationException {
        PropertiesConfiguration config = new PropertiesConfiguration();
        PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout();
        config.setLayout(layout);
        layout.load(config, new FileReader("/src/main/java/resources/config/configuration.properties"));
        config.setProperty(key, value);
        StringWriter stringWriter = new StringWriter();
        layout.save(config, stringWriter);
        System.out.println("Properties " + stringWriter.toString());
    }
}
