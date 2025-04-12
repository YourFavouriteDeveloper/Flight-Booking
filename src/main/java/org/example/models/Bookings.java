package org.example.models;

public class Bookings {
    private int bookingID;
    private int flightID;
    private double price;
    private int seatNumber;

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

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

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Bookings(int bookingID, Flights flight, double price, int seatNumber) {
        this.bookingID = bookingID;
        flightID = flight.getFlightId();
        this.price = price;
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return "\n----------------------------------------\n" +
                "Booking ID: " + bookingID +
                "\nFlight ID: " + flightID +
                "\nPrice: " + price +
                "\nSeat Number: " + seatNumber +
                "\n----------------------------------------";
    }
}
