package org.example.Admin;

import org.example.Input.ScannerInput;
import org.example.User.Imp;
import org.example.models.Accounts;
import org.example.models.Flights;

import java.sql.*;
import java.util.HashMap;

public class ImpAdmin implements ImplementationsAdmin {
    private static Accounts admin = new Accounts("root","root","admin",null);
    Imp imp = new Imp();
    HashMap<Integer,Flights> flights = imp.getFlights();

    public static Accounts getAdmin() {
        return admin;
    }

    public static void setAdmin(Accounts admin) {
        ImpAdmin.admin = admin;
    }

    @Override
    public void createFlight() {



        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000")) {


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
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000")) {


            DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet tables = dbMeta.getTables(null, null, "flights", null);

            if (tables.next()) {

                System.out.println("Table flights exists. Fetching data:");

                String selectQuery = "SELECT * FROM flights";
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(selectQuery)) {

                    while (rs.next()) {
                        Flights flight = new Flights();
                        flight.setFlightId(rs.getInt("id"));
                        flight.setArrivalTime(rs.getDate("arrivalTime"));
                        flight.setDepartureTime(rs.getDate("departureTime"));
                        flight.setFlightNumber(rs.getString("flightNumber"));
                        flight.setAvailableSeats(rs.getInt("availableSeats"));
                        flight.setTotalSeats(rs.getInt("totalSeats"));
                        flight.setAirplaneModel(rs.getString("airplaneModel"));
                        flight.setAirplaneCompany(rs.getString("airplaneCompany"));
                        flight.setOrigin(rs.getString("origin"));
                        flight.setDestination(rs.getString("destination"));
                        flights.put(flight.getFlightId(), flight);
                    }
                    for(Integer id : flights.keySet()) {
                        System.out.println(flights.get(id).toString());
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Flights searchFlight() {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000")) {


            DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet tables = dbMeta.getTables(null, null, "flights", null);

            if (tables.next()) {


                String selectQuery = "SELECT * FROM flights WHERE id = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(selectQuery)) {
                    System.out.print("Enter the flight ID: ");
                    int searchingId = ScannerInput.getInt();
                    preparedStatement.setInt(1, searchingId);
                    ResultSet rs = preparedStatement.executeQuery();
                    while (rs.next()) {
                        Flights flight = new Flights();
                        flight.setFlightId(rs.getInt("id"));
                        flight.setArrivalTime(rs.getDate("arrivalTime"));
                        flight.setDepartureTime(rs.getDate("departureTime"));
                        flight.setFlightNumber(rs.getString("flightNumber"));
                        flight.setAvailableSeats(rs.getInt("availableSeats"));
                        flight.setTotalSeats(rs.getInt("totalSeats"));
                        flight.setAirplaneModel(rs.getString("airplaneModel"));
                        flight.setAirplaneCompany(rs.getString("airplaneCompany"));
                        flight.setOrigin(rs.getString("origin"));
                        flight.setDestination(rs.getString("destination"));
                        System.out.println("FLIGHT FOUND" + flight.toString());
                        return flight;
                    }
                    System.out.println("FLIGHT NOT FOUND");

                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean cancelBooking() {
        return false;
    }

    @Override
    public void myFlights() {

    }
}

