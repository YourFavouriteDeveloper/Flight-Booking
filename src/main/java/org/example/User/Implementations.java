package org.example.User;

import org.example.models.Flights;

public interface Implementations {
    boolean registerUser();
    void onlineBoard();
    Flights showFlight();
    boolean searchFlight();
    boolean cancelBooking();
    void myFlights();
    boolean bookFlight();
    void notifications();

}
