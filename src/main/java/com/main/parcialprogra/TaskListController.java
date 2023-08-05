package com.main.parcialprogra;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TaskListController {
    @FXML
    private ListView<String> Lista;

    @FXML
    private Button Añadir;

    public void initialize() {
        Añadir.setOnAction(event -> {
            // Cambiar a la vista principal (main.fxml)
            try {
                Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
                BorderPane mainBorderPane = new BorderPane(root);
                Scene scene = new Scene(mainBorderPane, 843, 588);
                Stage stage = (Stage) Añadir.getScene().getWindow(); // Obtener la ventana actual
                stage.setScene(scene);
                stage.setTitle("Tu Aplicación");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public ListView<String> getTaskListView() {
        return Lista;
    }

    public void setTaskList(List<String> tasks) {
        Lista.getItems().clear();
        Lista.getItems().addAll(tasks);
    }
}
