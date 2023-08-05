package com.main.parcialprogra;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
         class Task {
            private String name;
            private LocalDate date;
            private boolean isUniversityTask;

            public Task(String name, LocalDate date, boolean isUniversityTask) {
                this.name = name;
                this.date = date;
                this.isUniversityTask = isUniversityTask;
            }


            public String getName() {
                return name;
            }

            public LocalDate getDate() {
                return date;
            }

            public boolean isUniversityTask() { // Agregamos este método
                return isUniversityTask;
            }

            @Override
            public String toString() {
                return "Tarea: " + name + " - Fecha: " + date + (isUniversityTask ? " - Universidad" : "");
            }
        }

        List<Task> tasks = new ArrayList<>();

        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));


        HBox taskBox = (HBox) root.lookup("#taskBox");
        HBox taskBox1 = (HBox) root.lookup("#taskBox1"); // Buscar el HBox que contiene el CheckBox
        CheckBox universityCheckBox = (CheckBox) taskBox1.lookup("#universidad"); // Buscar el CheckBox dentro del HBox

        TextField taskNameField = (TextField) taskBox.lookup("#taskNameField");
        DatePicker datePicker = (DatePicker) taskBox.lookup("#datePicker");
        Button addButton = (Button) taskBox.lookup("#addButton");


        addButton.setOnAction(event -> {
            String taskName = taskNameField.getText();
            LocalDate selectedDate = datePicker.getValue();
            boolean isUniversityTask = universityCheckBox.isSelected();

            if (selectedDate != null) {
                tasks.add(new Task(taskName, selectedDate, isUniversityTask));
                System.out.println("Tarea añadida: " + taskName + " - Fecha: " + selectedDate +
                        (isUniversityTask ? " - Universidad" : ""));
            } else {
                System.out.println("Tarea añadida: " + taskName + " - Fecha no seleccionada");
            }


            // Limpia los campos después de añadir una tarea
            taskNameField.clear();
            datePicker.setValue(null);
            universityCheckBox.setSelected(false); // Desmarcamos el CheckBox después de agregar la tarea
        });

        BorderPane mainBorderPane = new BorderPane(root);
        Scene scene = new Scene(mainBorderPane, 843, 588);

        ListView<String> taskListView = new ListView<>();
        // Add tasks to the taskListView here

        Button tasksButton = (Button) root.lookup("#tasksButton");
        tasksButton.setOnAction(event -> {
            if (mainBorderPane.getCenter() == root) {
                try {
                    FXMLLoader taskListLoader = new FXMLLoader(getClass().getResource("taskListView.fxml"));
                    Parent taskListRoot = taskListLoader.load();
                    TaskListController taskListController = taskListLoader.getController();

                    ListView<String> lista = taskListController.getTaskListView();
                    lista.getItems().clear();

                    // Loop through tasks and add them to the taskListView
                    for (Task task : tasks) {
                        String taskInfo = "Tarea: " + task.getName() + " - Fecha: " + task.getDate();
                        lista.getItems().add(taskInfo);
                    }

                    mainBorderPane.setCenter(taskListRoot);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                mainBorderPane.setCenter(root);
            }
        });
        Button addButtonMain = (Button) root.lookup("#Añadir");
        addButtonMain.setOnAction(event -> {
            mainBorderPane.setCenter(root);
        });

        mainBorderPane.setCenter(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tu Aplicación");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
