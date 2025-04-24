package com.ktpm.eventmanagerfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        scene = new Scene(loadFXML("Login"));

        stage.setScene(scene);
        stage.setTitle("Quản lý tổ chức sự kiện");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    static void setScene(String fxml) throws IOException {
        Scene newScene = new Scene(loadFXML(fxml));

        stage.setScene(newScene);
        stage.centerOnScreen();
        stage.setResizable(false);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch();
    }

}
