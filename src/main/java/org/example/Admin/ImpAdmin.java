package org.example.Admin;

import org.example.Input.ScannerInput;
import org.example.models.Flights;

import java.sql.*;
import java.util.HashMap;

public class ImpAdmin implements ImplementationsAdmin {


    @Override
    public void createFlight() {



        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Ni16022005")) {


            DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet tables = dbMeta.getTables(null, null, "flights", null);

            if (!tables.next()) {
                System.out.println("No flights found.Creating table and inserting sample data...");

                String createTable = """
                    CREATE TABLE flights (
                        id SERIAL PRIMARY KEY,
                        arrivalTime DATE,
                        departureTime DATE,
                        flightNumber VARCHAR(255) NOT NULL,
                        availableSeats INT,
                        totalSeats INT,
                        airplaneModel VARCHAR(255) NOT NULL,
                        airplaneCompany VARCHAR(255) NOT NULL,
                        origin VARCHAR(255) NOT NULL,
                        destination VARCHAR(255) NOT NULL
                    );
                    """;
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(createTable);
                    System.out.println("Table created.");
                }
            }



            String insertQuery = "INSERT INTO flights (arrivalTime, departureTime, flightNumber, availableSeats, totalSeats, airplaneModel, airplaneCompany, origin, destination) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                System.out.println("Inserting sample data...");
                System.out.print("Enter Arrival Time (dd:MM:yyyy): ");
                pstmt.setDate(1, ScannerInput.getDate());
                System.out.print("Enter Departure Time (dd:MM:yyyy): ");
                pstmt.setDate(2, ScannerInput.getDate());
                System.out.print("Enter Flight Number: ");
                pstmt.setString(3, ScannerInput.getString());
                System.out.print("Enter Available Seats: ");
                pstmt.setInt(4, ScannerInput.getInt());
                System.out.print("Enter Total Seats: ");
                pstmt.setInt(5, ScannerInput.getInt());
                System.out.print("Enter Airplane Model: ");
                pstmt.setString(6, ScannerInput.getString());
                System.out.print("Enter Airplane Company: ");
                pstmt.setString(7, ScannerInput.getString());
                System.out.print("Enter Origin: ");
                pstmt.setString(8, ScannerInput.getString());
                System.out.print("Enter Destination: ");
                pstmt.setString(9, ScannerInput.getString());
                pstmt.executeUpdate();
                System.out.println("Flight has been inserted.");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showAllFlights() {

    }

    @Override
    public boolean searchFlight() {
        return false;
    }

    @Override
    public boolean cancelBooking() {
        return false;
    }

    @Override
    public void myFlights() {

    }
}

