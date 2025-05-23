package org.example.Admin;

import org.example.Input.ScannerInput;
import org.example.User.Imp;
import org.example.models.Accounts;
import org.example.models.Flights;
import org.example.models.Passengers;

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
            ResultSet tablesFlight = dbMeta.getTables(null, null, "flights", null);
            ResultSet tablesBooking = dbMeta.getTables(null, null, "bookings", null);
            if (!tablesFlight.next()) {
                System.out.println("No flights found.Creating table and inserting sample data...");

                String createTableFlight = """
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
                    stmt.executeUpdate(createTableFlight);
                }
            }

            if (!tablesBooking.next()) {

                String createTableBooking = """
                        CREATE TABLE bookings (
                            price DOUBLE PRECISION,
                            flightId INT REFERENCES flights(id)
                        );
                        """;
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(createTableBooking);

                }
            }



            String insertQueryFlight = "INSERT INTO flights (arrivalTime, departureTime, flightNumber, availableSeats, totalSeats, airplaneModel, airplaneCompany, origin, destination) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(insertQueryFlight)) {
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
                String insertQueryBooking = "INSERT INTO bookings (price) VALUES (?)";
                try (PreparedStatement pst = conn.prepareStatement(insertQueryBooking)) {
                    System.out.print("Enter the ticket price: ");
                    pst.setDouble(1, ScannerInput.getDouble());
                    pstmt.executeUpdate();
                    pst.executeUpdate();

                    System.out.println("Flight has been inserted.");
                }

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


    public static HashMap<Integer,Accounts> showAllUsers(Passengers passengers) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000")) {


            DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet tables = dbMeta.getTables(null, null, "flights", null);

            if (tables.next()) {


                String selectQuery = "SELECT * FROM passengers WHERE firstName = ? AND lastName = ? ";
                String selectQueryUser = "SELECT * FROM users where passengerId = ?";
                HashMap<Integer,Accounts> users = new HashMap<>();
                try (PreparedStatement stmt = conn.prepareStatement(selectQuery);
                PreparedStatement stmt2 = conn.prepareStatement(selectQueryUser)) {
                    stmt.setString(1, passengers.getFirstName());
                    stmt.setString(2, passengers.getLastName());
                    ResultSet rs1 = stmt.executeQuery();


                    while (rs1.next()) {
                        passengers.setGender(rs1.getString("gender"));
                        passengers.setId(rs1.getInt("id"));
                        passengers.setNationality(rs1.getString("nationality"));
                        passengers.setPassport(rs1.getString("passport"));
                        Accounts accounts = new Accounts(passengers);
                        stmt2.setInt(1, rs1.getInt("id"));
                        ResultSet rs2 = stmt2.executeQuery();
                        if(rs2.next()) {
                            accounts.setPassword(rs2.getString("password"));
                            accounts.setUsername(rs2.getString("username"));
                            accounts.setAccountType("User");
                        }
                        users.put(rs1.getInt("id"), accounts);

                    }

                }
                return users;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

