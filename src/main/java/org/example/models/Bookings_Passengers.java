package org.example.models;

public class Bookings_Passengers {
    private int passengerId;
    private int bookingId;
    private int seatNumber;

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
        bookingId = booking.getFlightID();
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Bookings_Passengers(Passengers passenger, Bookings booking,int seatNumber) {
        passengerId = passenger.getId();
        bookingId = booking.getFlightID();
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return "\n----------------------------------------\n" +
                "Passenger Id: " + passengerId +
                "\nBooking Id: " + bookingId +
                "\nSeat Number: " + seatNumber +
                "\n----------------------------------------";
    }
}
