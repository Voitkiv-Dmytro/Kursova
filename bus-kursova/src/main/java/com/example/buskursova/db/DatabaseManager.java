package com.example.buskursova.db;

import com.example.buskursova.Application;
import com.example.buskursova.Bus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlserver://BusPark.mssql.somee.com:1433;database=BusPark;charset=UTF-8;user=Dima_SQLLogin_1;password=yzo4cyzam3;";
    public static void createTicketsTableIfNotExists() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            if (!isTableExists(statement, "Buses")) {
                createBusesTable(statement);
                System.out.println("Таблицю Buses створено успішно.");
            } else {
                System.out.println("Таблиця Buses вже існує.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void busesFromDB() {
        //ObservableList<Bus> listBus = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM Buses";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String busNumber = resultSet.getString("BusNumber").trim();
                String name = resultSet.getString("Name").trim();
                String route = resultSet.getString("Route").trim();
                boolean isInDepo = resultSet.getBoolean("IsInDepot");

                Bus bus = new Bus(busNumber, name, route);
                bus.setInDepot(isInDepo);
                bus.setId(id);
                Application.listBus.add(bus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int addBus(Bus bus) {

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Buses (BusNumber, Name, Route, IsInDepot) " +
                     "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, bus.busNumber);
            statement.setString(2, bus.name);
            statement.setString(3, bus.route);
            statement.setBoolean(4, bus.isInDepot);

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                System.out.println("Новий автобус успішно додано.");
                return generatedKeys.getInt(1);
            }

            System.out.println("Новий автобус успішно додано.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void deleteBus(int ticketId) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            String query = "DELETE FROM Buses WHERE Id = " + ticketId;
            int rowsAffected = statement.executeUpdate(query);
            System.out.println("Автобус успішно видалено. Кількість рядків, які були змінені: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBus(Bus bus) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            String query = "UPDATE Buses SET IsInDepot = '" + bus.isInDepot +
                    "' WHERE Id = " + bus.id;

            int rowsAffected = statement.executeUpdate(query);
            System.out.println("Автобус успішно оновлено. Кількість рядків, які були змінені: " + rowsAffected);



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isTableExists(Statement statement, String tableName) throws SQLException {
        String query = "SELECT COUNT(*) as count FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = '" + tableName + "'";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        int count = resultSet.getInt("count");
        return count > 0;
    }

    private static void createBusesTable(Statement statement) throws SQLException {
        String query = "CREATE TABLE Buses (" +
                "Id INT PRIMARY KEY IDENTITY(1,1), " +
                "BusNumber NCHAR(100), " +
                "Name NCHAR(100), " +
                "Route NCHAR(20), " +
                "IsInDepot BIT" +
                ")";
        statement.executeUpdate(query);
    }
}