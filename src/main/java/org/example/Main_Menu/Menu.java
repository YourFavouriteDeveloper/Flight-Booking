package org.example.Main_Menu;

import org.example.Input.ScannerInput;
import org.example.imp.Imp;

public class Menu {
    @Override
    public String toString() {
        return """
                ----------------------------------------
                1. Online Board
                2. Show the Flight Information
                3. Search and Book a Flight
                4. Cancel the Booking
                5. My Flights
                6. Exit
                ----------------------------------------
                """;
    }
    Imp imp = new Imp();

    public void start() {
        while(true) {

            switch(ScannerInput.getByte()) {
                case '1':
                    imp.onlineBoard();
                    break;
                case '2':
                    imp.showFlight();
                    break;
                case '3':
                    imp.searchFlight();
                    break;
                case '4':
                    imp.cancelBooking();
                    break;
                case '5':
                    imp.myFlights();
                    break;
                case '6':
                    System.exit(1);
                    break;
                default:
                    System.out.println("Invalid Input\n");
                    break;
            }
        }
    }


}
