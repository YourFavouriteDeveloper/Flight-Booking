package org.example.Admin;

import org.example.models.Accounts;
import org.example.models.Flights;
import org.example.models.Passengers;

import java.util.HashMap;

public interface ImplementationsAdmin {
    void createFlight();
    void showAllFlights();
    Flights searchFlight();
    boolean cancelBooking();
    void myFlights();


}
