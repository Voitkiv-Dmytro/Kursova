package com.example.buskursova;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.fxml.FXML;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MainController {

    @FXML
    private Button add_bus;

    @FXML
    public TableColumn<Bus, String> bus_number;

    @FXML
    public TableColumn<Bus, Button> change_status;

    @FXML
    private MenuItem closeapp;

    @FXML
    public TableColumn<Bus, Button> delete_bus;

    @FXML
    public TableColumn<Bus, String> driver;

    @FXML
    private MenuBar menubar;

    @FXML
    private MenuItem outpu_bus_ondepot;

    @FXML
    private MenuItem output_all_bus;

    @FXML
    private MenuItem output_bus_on_route;

    @FXML
    public TableColumn<Bus, String> route_number;

    @FXML
    private MenuItem savetxt;

    @FXML
    public TableColumn<Bus, Integer> sequance;

    @FXML
    public TableColumn<Bus, String> status_driver;

    @FXML
    public TableView<Bus> table;

    @FXML
    public TextField search_by_plate;

    @FXML
    public TextField search_by_route;

    public static Stage stage;

    public void openAddBus() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBus.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }
    public void initialize() {

        table.setItems(Application.listBus);

        route_number.setCellValueFactory(new PropertyValueFactory<>("route"));
        bus_number.setCellValueFactory(new PropertyValueFactory<>("busNumber"));
        driver.setCellValueFactory(new PropertyValueFactory<>("name"));
        status_driver.setCellValueFactory(new PropertyValueFactory<>("status"));
        sequance.setCellFactory(column -> {
            TableCell<Bus, Integer> cell = new TableCell<>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        int rowIndex = getIndex() + 1;
                        setText(String.valueOf(rowIndex));
                    }
                }
            };
            return cell;
        });
        sequance.setSortable(false);

        change_status.setCellValueFactory(new PropertyValueFactory<>("changeStatus"));
        delete_bus.setCellValueFactory(new PropertyValueFactory<>("delete"));

        search_by_plate.textProperty().addListener((observable, oldValue, newValue) -> {
            filterBusesByBusNumber(table, newValue);
        });

        search_by_route.textProperty().addListener((observable, oldValue, newValue) -> {
            filterBusesByRoute(table, newValue);
        });
    }

    public void filterBusesByBusNumber(TableView<Bus> tableView, String busNumber) {
        if (busNumber.isEmpty()) {
            tableView.setItems(Application.listBus);
        } else {
            ObservableList<Bus> filteredList = Application.listBus.filtered(order -> order.busNumber.contains(busNumber));
            tableView.setItems(filteredList);
        }
    }

    public void filterBusesByRoute(TableView<Bus> tableView, String route) {
        if (route.isEmpty()) {
            tableView.setItems(Application.listBus);
        } else {
            ObservableList<Bus> filteredList = Application.listBus.filtered(order -> order.route.contains(route));
            tableView.setItems(filteredList);
        }
    }

    public void showAll(){
        table.setItems(Application.listBus);
    }

    public void filterBusesByDepot() {
        ObservableList<Bus> filteredList = Application.listBus.filtered(order -> order.isInDepot);
        table.setItems(filteredList);
    }

    public void filterBusesByNotDepot() {
        ObservableList<Bus> filteredList = Application.listBus.filtered(order -> !order.isInDepot);
        table.setItems(filteredList);
    }

    public void writeBusesToFile() {
        String filename = "buses.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Bus bus : Application.listBus) {
                writer.write(bus.busNumber + ";" + bus.name + ";" + bus.route + ";" + bus.isInDepot + ";" + bus.status);
                writer.newLine();
            }
            System.out.println("Масив автобусів було успішно записано у файл " + filename);
        } catch (IOException e) {
            System.err.println("Помилка при записі масиву автобусів у файл " + filename);
            e.printStackTrace();
        }
    }
}


