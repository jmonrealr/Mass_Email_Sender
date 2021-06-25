package com.snorlax;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class DynamicCssController {
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public ColorPicker colorPicker;
    @FXML
    public Label label;
    private String defaultColor = "ffffff80";
    private String newColor;
    private boolean flag = true;

    public void  initialize(){
        this.colorPicker.valueProperty().addListener((observable -> {
            System.out.println(anchorPane.getStyle().toString());
            anchorPane.setStyle(
                    "-fx-background-color: #" + colorPicker.getValue().toString().substring(2,8) + ";"
            );
        }));
    }
}
