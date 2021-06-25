package com.snorlax;

import javafx.scene.image.Image;

/**
 * This class is used to control the Icon Image for the app
 */
public class IconImage {
    /**
     * Get the default icon for the app
     * @return Object Image with the image
     */
    public static Image getIcon(){
        return new Image("file:src/main/resources/images/upv-bis.png");
    }
}
