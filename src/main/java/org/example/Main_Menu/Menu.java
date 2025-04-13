package org.example.Main_Menu;

import org.example.Input.ScannerInput;
import org.example.User.Imp;
import org.example.Admin.ImpAdmin;

import java.util.Objects;

public class Menu {

    public String select() {
        return """
                Welcome to the Flight Booking System!
                ----------------------------------------
                1. Login as Administrator
                2. Login as User
                3. Register a new User
                4. Exit Program
                ----------------------------------------
                """;
    }

    public String forUser() {
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

    public String forAdmin() {
        return """
                ----------------------------------------
                1. Create a Flight
                2. Show all Flights
                3. Search and Book a Flight
                4. Cancel the Booking
                5. My Flights
                6. Exit
                ----------------------------------------
                """;
    }

    public boolean loginAdmin() {
        System.out.print("Enter Username: ");
        String username = ScannerInput.getString();
        System.out.print("Enter Password: ");
        String password = ScannerInput.getString();
        return (Objects.equals(username, ImpAdmin.getAdmin().getUsername()) && Objects.equals(password, ImpAdmin.getAdmin().getPassword()));
    }

    public static void start() {
        Imp imp = new Imp();
        ImpAdmin admin = new ImpAdmin();
        Menu menu = new Menu();
        while (true) {
            System.out.print(menu.select() + "\nEnter your choice: ");
            switch (ScannerInput.getByte()) {
                case 1:
                    if (menu.loginAdmin()) {
                        System.out.println("LOGIN SUCCESSFULLY");
                        while (true) {
                            boolean breaker = false;
                            System.out.print(menu.forAdmin() + "\nEnter your choice: ");
                            switch (ScannerInput.getByte()) {
                                case 1:
                                    try {
                                        admin.createFlight();
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                    break;
                                case 2:
                                    admin.showAllFlights();
                                    break;
                                case 3:
                                    admin.searchFlight();
                                    break;
                                case 4:
                                    admin.cancelBooking();
                                    break;
                                case 5:
                                    admin.myFlights();
                                    break;
                                case 6:
                                    breaker = true;
                                    break;
                                default:
                                    System.out.println("Invalid Input\n");
                                    break;
                            }
                            if (breaker) {
                                break;
                            }
                        }
                    }
                    else {
                        System.out.println("LOGIN FAILED");
                    }

                    break;
                case 2:
                    while (true) {
                        boolean breaker = false;
                        System.out.print(menu.forUser() + "\nEnter your choice: ");
                        switch (ScannerInput.getByte()) {
                            case 1:
                                try {
                                    imp.onlineBoard();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                break;
                            case 2:
                                imp.showFlight();
                                break;
                            case 3:
                                imp.searchFlight();
                                break;
                            case 4:
                                imp.cancelBooking();
                                break;
                            case 5:
                                imp.myFlights();
                                break;
                            case 6:
                                breaker = true;
                                break;
                            default:
                                System.out.println("Invalid Input\n");
                                break;
                        }
                        if (breaker) {
                            break;
                        }
                    }
                    break;
                case 3:
                    imp.registerUser();
                    break;
                case 4:
                    System.exit(1);
                    break;
                default:
                    System.out.println("Invalid Input\n");
                    break;
            }

        }
    }


}
