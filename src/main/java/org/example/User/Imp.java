package org.example.User;

import org.example.User.Implementations;
import org.example.models.Flights;

import java.sql.*;
import java.util.HashMap;

public class Imp implements Implementations {
    private HashMap<Integer,Flights> flights = new HashMap<>();

    public HashMap<Integer, Flights> getFlights() {
        return flights;
    }

    public void setFlights(HashMap<Integer, Flights> flights) {
        this.flights = flights;
    }

    @Override
    public void onlineBoard() {



        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Ni16022005")) {


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
    public void showFlight() {

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
