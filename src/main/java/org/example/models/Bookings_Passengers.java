package org.example.models;

public class Bookings_Passengers {
    private int passengerId;
    private int bookingId;

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Passengers passenger) {
        passengerId = passenger.getId();
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(Bookings booking) {
        bookingId = booking.getBookingID();
    }

    public Bookings_Passengers(Passengers passenger, Bookings booking) {
        passengerId = passenger.getId();
        bookingId = booking.getBookingID();
    }

    @Override
    public String toString() {
        return "\n----------------------------------------\n" +
                "Passenger Id: " + passengerId +
                "\nBooking Id: " + bookingId +
                "\n----------------------------------------";
    }
}
