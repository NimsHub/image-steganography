package org.example.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProjectUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Image Steganography");
        stage.setScene(scene);
        stage.show();
    }
}