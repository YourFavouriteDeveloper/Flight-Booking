package org.example.Admin;

import org.example.models.Flights;

public interface ImplementationsAdmin {
    void createFlight();
    void showAllFlights();
    Flights searchFlight();
    boolean cancelBooking();
    void myFlights();

}
