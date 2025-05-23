package org.example.User;

import org.example.Admin.ImpAdmin;
import org.example.Input.ScannerInput;
import org.example.User.Implementations;
import org.example.models.Accounts;
import org.example.models.Flights;
import org.example.models.Passengers;

import java.sql.*;
import java.util.HashMap;

public class Imp implements Implementations {
    private HashMap<Integer, Flights> flights = new HashMap<>();
    private HashMap<Integer, Accounts> users = new HashMap<>();
    private Accounts user = new Accounts("user");

    public Accounts getUser() {
        return user;
    }

    public void setUser(Accounts user) {
        this.user = user;
    }

    public HashMap<Integer, Flights> getFlights() {
        return flights;
    }

    public void setFlights(HashMap<Integer, Flights> flights) {
        this.flights = flights;
    }

    public HashMap<Integer, Accounts> getUsers() {
        return users;
    }

    public void setUsers(HashMap<Integer, Accounts> users) {
        this.users = users;
    }

    @Override
    public boolean loginUser() {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000")) {


            DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet tables = dbMeta.getTables(null, null, "users", null);
            HashMap<String, Accounts> usersCheck = new HashMap<>();
            if (tables.next()) {


                String selectQueryUser = "SELECT * FROM users";
                String selectQueryPassenger = "SELECT * FROM passengers WHERE id = ?";
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(selectQueryUser)) {

                    while (rs.next()) {
                        user.setUsername(rs.getString("userName"));
                        user.setPassword(rs.getString("password"));
                        Passengers passenger = new Passengers();
                        try (PreparedStatement preparedStatement = conn.prepareStatement(selectQueryPassenger)) {
                            preparedStatement.setInt(1, rs.getInt("passengerId"));
                            ResultSet r = preparedStatement.executeQuery();
                            while (r.next()) {
                                passenger.setId(r.getInt("id"));
                                passenger.setFirstName(r.getString("firstName"));
                                passenger.setLastName(r.getString("lastName"));
                                passenger.setGender(r.getString("gender"));
                                passenger.setNationality(r.getString("nationality"));
                                passenger.setPassport(r.getString("passport"));
                            }
                        }
                        user.setPassenger(passenger);
                        usersCheck.put(user.getUsername(), user);

                    }

                }
                System.out.print("Enter username: ");
                String username = ScannerInput.getString();
                System.out.print("Enter password: ");
                String password = ScannerInput.getString();
                for (String userName : usersCheck.keySet()) {
                    if (userName.equals(username) && usersCheck.get(userName).getPassword().equals(password)) {
                        user = usersCheck.get(userName);
                        System.out.println("LOGIN SUCCESS");
                        return true;
                    }
                }


            } else {
                System.out.print("NO USER FOUND\nWould you like to register a new user?\n1. yes\n2. no\nEnter your choice: ");
                if (ScannerInput.getByte() == 1) {
                    registerUser();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("LOGIN FAILED");
        return false;
    }

    @Override
    public boolean registerUser() {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000")) {


            DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet table1 = dbMeta.getTables(null, null, "passengers", null);
            ResultSet table2 = dbMeta.getTables(null, null, "users", null);
            if (!table1.next()) {
                String createTable = """
                        CREATE TABLE passengers (
                            id SERIAL PRIMARY KEY,
                            firstName VARCHAR(255),
                            lastName VARCHAR(255),
                            gender VARCHAR(255),
                            nationality VARCHAR(255),
                            passport VARCHAR(255)
                        );
                        """;
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(createTable);
                }
            }
            if (!table2.next()) {
                String createTable = """
                        CREATE TABLE users (
                            username VARCHAR(255),
                            password VARCHAR(255),
                            passengerId SERIAL PRIMARY KEY REFERENCES passengers(id),
                            notifications VARCHAR(255)
                        );
                        """;
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(createTable);
                }
            }


            String insertQueryUser = "INSERT INTO users (username,password) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertQueryUser)) {
                System.out.println("Creating a new user...");
                System.out.print("Enter Username: ");
                stmt.setString(1, ScannerInput.getString());
                System.out.print("Enter password: ");
                String password = ScannerInput.getString();
                System.out.print("Confirm Password: ");
                while (!password.equals(ScannerInput.getString())) {
                    System.out.println("Passwords do not match");
                    System.out.print("Enter password: ");
                    password = ScannerInput.getString();
                    System.out.print("Confirm Password: ");
                }
                stmt.setString(2, password);
                String insertQueryPassenger = "INSERT INTO passengers (firstName, lastName, gender, nationality, passport) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement st = conn.prepareStatement(insertQueryPassenger)) {
                    System.out.println("Creating detailed information about the user...");
                    System.out.print("Enter First Name: ");
                    st.setString(1, ScannerInput.getString());
                    System.out.print("Enter Last Name: ");
                    st.setString(2, ScannerInput.getString());
                    System.out.print("Enter gender: ");
                    st.setString(3, ScannerInput.getString());
                    System.out.print("Enter Nationality: ");
                    st.setString(4, ScannerInput.getString());
                    System.out.print("Enter Passport: ");
                    st.setString(5, ScannerInput.getString());
                    st.executeUpdate();
                    stmt.executeUpdate();
                    System.out.println("The new User has been created!");
                    return true;
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void onlineBoard() {


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
                    for (Integer id : flights.keySet()) {
                        System.out.println(flights.get(id).toString());
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Flights showFlight() {
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
    public boolean searchFlight() {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000")) {
            Flights flight = new Flights();

            DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet tables = dbMeta.getTables(null, null, "flights", null);

            if (tables.next()) {


                String selectQuery = "SELECT * FROM flights WHERE destination = ? AND departureTime = ? AND availableSeats >= ? ";
                try (PreparedStatement preparedStatement = conn.prepareStatement(selectQuery)) {
                    System.out.print("Enter the Destination: ");
                    String searchingDestination = ScannerInput.getString();
                    System.out.print("Enter the Departure Time (dd:MM:yyyy): ");
                    Date searchingDepartureTime = ScannerInput.getDate();
                    System.out.print("Enter the Number of People: ");
                    int searchingPeople = ScannerInput.getInt();
                    preparedStatement.setString(1, searchingDestination);
                    preparedStatement.setDate(2, searchingDepartureTime);
                    preparedStatement.setInt(3, searchingPeople);
                    ResultSet rs = preparedStatement.executeQuery();
                    int count = 0;
                    while (rs.next()) {

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
                        System.out.println("AVAILABLE FLIGHT NO:" + (++count) + flight.toString());

                    }
                    if (count != 0) {
                        System.out.print("Would you like to book a new flight?\n1. Yes\n2. No\nEnter your choice: ");

                        switch (ScannerInput.getByte()) {
                            case 1:
                                for (int i = 1; i <= searchingPeople; i++) {
                                    System.out.print("Enter the " + i + " passenger's full name: ");
                                    HashMap<Integer, Accounts> accounts = ImpAdmin.showAllUsers(new Passengers(ScannerInput.getNext(), ScannerInput.getNext()));
                                    for (Integer key : accounts.keySet()) {
                                        System.out.println("ID " + key + ". " + accounts.get(key).getUsername());
                                    }
                                    System.out.println("Which user would you like to book?");
                                    Integer choice = ScannerInput.getInt();
                                    if (accounts.containsKey(choice)) {
                                        bookFlight(flight, accounts.get(choice));
                                    } else {
                                        i--;
                                        System.out.println("Invalid user. Please try again.");
                                    }

                                }

                            case 2:
                                return false;
                        }
                    } else {
                        System.out.println("THERE ARE NO SUCH FLIGHT FOR YOUR CHOICES");
                    }


                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }


    @Override
    public boolean cancelBooking() {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000")) {


            DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet tables = dbMeta.getTables(null, null, "bookings_passengers", null);

            HashMap<Integer,Flights> flights = new HashMap<>();
            String deleteQuery = "DELETE FROM bookings_passengers WHERE passengerId = ? AND bookingId = ?";
            String insertNotification = "UPDATE users SET notifications = ? WHERE passengerId = ?";
            String selectQueryBooking = "SELECT bookingId FROM bookings_passengers WHERE passengerId = ?";
            String selectQueryPassenger = "SELECT passengerId FROM users WHERE username = ?";
            String selectQueryFlight = "SELECT id, departureTime, destination FROM flights WHERE id = ?";
            try (PreparedStatement pstmtDelete = conn.prepareStatement(deleteQuery);
                 PreparedStatement pstmtNotification = conn.prepareStatement(insertNotification);
                 PreparedStatement pstmtPassenger = conn.prepareStatement(selectQueryPassenger);
                 PreparedStatement pstmtBooking = conn.prepareStatement(selectQueryBooking);
                 PreparedStatement pstmtFlight = conn.prepareStatement(selectQueryFlight)) {
                pstmtPassenger.setString(1, user.getUsername());
                ResultSet rsPassenger = pstmtPassenger.executeQuery();
                int counter = 0;
                if (rsPassenger.next()) {
                    pstmtBooking.setInt(1, rsPassenger.getInt("passengerId"));
                    pstmtDelete.setInt(1, rsPassenger.getInt("passengerId"));
                    pstmtNotification.setInt(2, rsPassenger.getInt("passengerId"));
                    ResultSet rsBooking = pstmtBooking.executeQuery();


                    while (rsBooking.next()) {
                        counter++;
                        pstmtFlight.setInt(1, rsBooking.getInt("bookingId"));
                        ResultSet rsFlight = pstmtFlight.executeQuery();
                        if(rsFlight.next()) {
                            Flights flight = new Flights();
                            flight.setFlightId(rsFlight.getInt("id"));
                            flight.setDepartureTime(rsFlight.getDate("departureTime"));
                            flight.setDestination(rsFlight.getString("destination"));
                            flights.put(flight.getFlightId(), flight);
                            System.out.println("\n----------------------------------------");
                            System.out.println("Flight ID: " + rsFlight.getInt("id"));
                            System.out.println("Departure Time: " + rsFlight.getDate("departureTime"));
                            System.out.println("Destination: " + rsFlight.getString("destination"));
                            System.out.print("----------------------------------------");
                        }

                    }
                    pstmtNotification.setInt(2, rsPassenger.getInt("passengerId"));


                }
                if(counter == 0) {
                    System.out.println("There is no flight booked.");
                    return false;
                }
                System.out.print("\nEnter the flight ID  you want to cancel booking: ");
                Integer choice = ScannerInput.getInt();
                if (flights.containsKey(choice)) {
                    pstmtDelete.setInt(2, flights.get(choice).getFlightId());
                    if(pstmtDelete.executeUpdate()>0) {
                        pstmtNotification.setString(1,"Your booking for Flight " + flights.get(choice).getFlightNumber() + " has been cancelled.");
                        pstmtNotification.executeUpdate();
                        seatChange(flights.get(choice), 1);
                        System.out.println("Booking has been cancelled.");
                        return true;
                    }
                }


            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Booking failed to be cancelled.");
        return false;
    }

    @Override
    public void myFlights() {

    }

    @Override
    public boolean bookFlight(Flights flight, Accounts account) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000")) {


            DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet tables = dbMeta.getTables(null, null, "bookings_passengers", null);
            if (!tables.next()) {

                String createTable = """
                        CREATE TABLE bookings_passengers (
                            passengerId INT REFERENCES passengers(id),
                            bookingId INT REFERENCES bookings(flightId),
                            seatNumber INT
                        );
                        """;
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(createTable);
                }
            }

            String insertQuery = "INSERT INTO bookings_passengers (passengerId,seatNumber,bookingId) VALUES (?,?,?)";
            String insertNotification = "UPDATE users SET notifications = ? WHERE passengerId = ?";
            String selectQueryBooking = "SELECT flightId FROM bookings WHERE flightId = ?";
            String selectQueryPassenger = "SELECT passengerId FROM users WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery);
                 PreparedStatement pstmt2 = conn.prepareStatement(insertNotification)) {
                try (PreparedStatement stmt = conn.prepareStatement(selectQueryPassenger)) {
                    stmt.setString(1, account.getUsername());
                    ResultSet rs2 = stmt.executeQuery();
                    if (rs2.next()) {
                        pstmt.setInt(1, rs2.getInt("passengerId"));
                        pstmt2.setInt(2, rs2.getInt("passengerId"));
                        pstmt2.setString(1, "You have been booked! You will be expected to be in " + flight.getFlightNumber() + " Flight!");
                        pstmt2.executeUpdate();
                    }
                    System.out.print("Enter the seat number: ");
                    pstmt.setInt(2, ScannerInput.getInt());
                    try (PreparedStatement tmt = conn.prepareStatement(selectQueryBooking)) {
                        tmt.setInt(1, flight.getFlightId());
                        ResultSet rs1 = tmt.executeQuery();
                        if (rs1.next()) {
                            pstmt.setInt(3, rs1.getInt("flightId"));
                        }
                    }


                }

                pstmt.executeUpdate();
                return seatChange(flight, -1);

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean seatChange(Flights flight, int value) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000")) {
            String updateQuery = "UPDATE flights SET availableSeats = ? WHERE id = ? AND availableSeats -1 >= 0";
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setInt(1, flight.getAvailableSeats() + value);
                pstmt.setInt(2, flight.getFlightId());
                int result = pstmt.executeUpdate();
                return (result > 0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void notifications() {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "0000")) {


            DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet tables = dbMeta.getTables(null, null, "users", null);

            if (tables.next()) {


                String selectQuery = "SELECT notifications FROM users WHERE passengerId = ?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(selectQuery)) {
                    preparedStatement.setInt(1, user.getPassenger().getId());
                    ResultSet rs = preparedStatement.executeQuery();
                    boolean found = false;

                    while (rs.next()) {
                        System.out.println("\n----------------------------------------");
                        System.out.println(rs.getString("notifications"));
                        found = true;
                    }

                    if (!found) {
                        System.out.println("No notifications found.");
                    }


                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
