package org.example.models;

public class Bookings {
    private int flightID;
    private double price;




    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(Flights flight) {
        flightID = flight.getFlightId();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }



    public Bookings(Flights flight, double price) {
        flightID = flight.getFlightId();
        this.price = price;

    }

    @Override
    public String toString() {
        return "\n----------------------------------------\n" +
                "Flight ID: " + flightID +
                "\nPrice: " + price +
                "\n----------------------------------------";
    }
}
