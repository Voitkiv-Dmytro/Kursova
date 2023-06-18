package com.example.buskursova;



import com.example.buskursova.db.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.SliderSkin;

public class AddBus {

    @FXML
    private Button done;

    @FXML
    private TextField enter_bus_number;

    @FXML
    private TextField enter_driver;

    @FXML
    private TextField enter_route;

    public TableView<Bus> tableView;

    public void onDoneClick(){
        MainController.stage.close();
        createBus();
    }
    public void createBus(){
        String routeName = enter_route.getText();
        String busNumber = enter_bus_number.getText();
        String driverName = enter_driver.getText();

        Bus bus = new Bus(busNumber, driverName, routeName);
        Application.listBus.add(bus);

        Application.listBus.get(Application.listBus.size() - 1).id = DatabaseManager.addBus(bus);
    }
}
