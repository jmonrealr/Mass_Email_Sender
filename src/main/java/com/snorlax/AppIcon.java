package com.snorlax;

import javafx.scene.image.Image;

/**
 * This class is used to control the Icon Image for the app
 */
public class AppIcon {
    /**
     * Get the default icon for the app
     * The icon name is obtained from the properties
     * @return Object Image with the image
     */
    public static Image getIcon(){
        return new Image("/images/" + Config.getProperty("icon"));
    }
}
