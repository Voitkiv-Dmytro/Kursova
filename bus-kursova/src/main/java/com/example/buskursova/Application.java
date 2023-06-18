package com.example.buskursova;

import com.example.buskursova.db.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Application extends javafx.application.Application {

    public static ObservableList<Bus> listBus = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 630);
        stage.setTitle("E-BusDepot");
        stage.setScene(scene);
        stage.show();

        MainController mainController = fxmlLoader.getController();
        //mainController.setStage(stage);
        mainController.initialize();

        DatabaseManager.createTicketsTableIfNotExists();
        DatabaseManager.busesFromDB();
    }

    public static void main(String[] args) {
        launch();
    }
}