package org.example.models;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

public class Flights {
    private int flightId;
    private Date arrivalTime;
    private Date departureTime;
    private String flightNumber;
    private int availableSeats;
    private int totalSeats;
    private String airplaneModel;
    private String airplaneCompany;
    private String origin;
    private String destination;

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getAirplaneModel() {
        return airplaneModel;
    }

    public void setAirplaneModel(String airplaneModel) {
        this.airplaneModel = airplaneModel;
    }

    public String getAirplaneCompany() {
        return airplaneCompany;
    }

    public void setAirplaneCompany(String airplaneCompany) {
        this.airplaneCompany = airplaneCompany;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Flights() {}

    public Flights(int flightId, Date arrivalTime, Date departureTime, String flightNumber, int availableSeats, int totalSeats, String airplaneModel, String airplaneCompany, String origin, String destination) {
        this.flightId = flightId;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.flightNumber = flightNumber;
        this.availableSeats = availableSeats;
        this.totalSeats = totalSeats;
        this.airplaneModel = airplaneModel;
        this.airplaneCompany = airplaneCompany;
        this.origin = origin;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "\n----------------------------------------\n" +
                "Flight ID: " + flightId +
                "\nArrival Time: " + arrivalTime +
                "\nDeparture Time: " + departureTime +
                "\nFlight Number: " + flightNumber +
                "\nAvailable Seats: " + availableSeats +
                "\nTotal Seats: " + totalSeats +
                "\nAirplane Model: " + airplaneModel +
                "\nAirplane Company: " + airplaneCompany +
                "\nOrigin Country: " + origin +
                "\nDestination Country: " + destination +
                "\n----------------------------------------";
    }
}
