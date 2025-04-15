package org.example.User;

import org.example.models.Accounts;
import org.example.models.Flights;
import org.example.models.Passengers;

public interface Implementations {
    boolean loginUser();
    boolean registerUser();
    void onlineBoard();
    Flights showFlight();
    boolean searchFlight();
    boolean cancelBooking();
    void myFlights();
    boolean bookFlight(Flights flights, Accounts account);
    boolean seatChange(Flights flights,int value);
    void notifications();

}
