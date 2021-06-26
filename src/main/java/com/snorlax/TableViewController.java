package com.snorlax;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class TableViewController {
    @FXML
    private TableView tableView;

    public void load(){
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        final List<List<String>> excelData = new ReadExcel("filePath").getDataAsList();
        //Add excel data to an observable list
        for(int i = 0; i < excelData.size(); i++)
        {
            data.add(FXCollections.observableArrayList(excelData.get(i)));
        }

        TableView<ObservableList<String>> tableView = new TableView();
        tableView.setItems(data);

        //Create the table columns, set the cell value factory and add the column to the tableview.
        for (int i = 0; i < excelData.get(0).size(); i++) {
            final int curCol = i;
            final TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    "Col " + (curCol + 1)
            );
            column.setCellValueFactory(
                    param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol))
            );
            tableView.getColumns().add(column);
        }
    }
}
