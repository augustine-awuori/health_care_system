package com.example.health_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        var loader = new FXMLLoader(getClass().getResource("/com/example/health_system/main_layout.fxml"));
        var scene = new Scene(loader.load(), 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Health Information Management System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
