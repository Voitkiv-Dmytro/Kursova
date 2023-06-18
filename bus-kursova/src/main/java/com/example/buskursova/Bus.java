package com.example.buskursova;

import com.example.buskursova.db.DatabaseManager;
import javafx.scene.control.Button;

public class Bus {
    public String busNumber;
    public String name;
    public String route;
    public boolean isInDepot = true;
    public String status = "у парку";
    public int id;
    public Button delete;
    public Button changeStatus;

    public Bus(String busNumber, String name, String route) {
        this.busNumber = busNumber;
        this.name = name;
        this.route = route;

        changeStatus = new Button("Змінити статус");
        changeStatus.setOnAction(event -> {
            isInDepot = isInDepot ? false : true;

            int index = Application.listBus.indexOf(this);

            Application.listBus.remove(this);
            Bus bus = new Bus(this.busNumber, this.name, this.route);
            bus.isInDepot = isInDepot;
            bus.status = isInDepot ? "у парку" : "на маршруті";
            bus.id = id;
            Application.listBus.add(index, bus);

            DatabaseManager.updateBus(this);
        });

        delete = new Button("Видалити");
        delete.setOnAction(event -> {
            Application.listBus.remove(this);
            DatabaseManager.deleteBus(id);
        });
    }

    public Button getChangeStatus() {
        return changeStatus;
    }

    public Button getDelete() {
        return delete;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public boolean isInDepot() {
        return isInDepot;
    }

    public void setInDepot(boolean inDepot) {
        isInDepot = inDepot;
        status = isInDepot ? "у парку" : "на маршруті";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
