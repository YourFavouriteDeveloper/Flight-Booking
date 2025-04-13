package org.example.models;

import org.example.enums.Gender;

public class Passengers {
    private int passengerId;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String nationality;
    private String passport;
    private boolean isBooked;

    public int getId() {
        return passengerId;
    }

    public void setId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = Gender.valueOf(gender.substring(0, 1).toUpperCase() + gender.substring(1).toLowerCase());
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public Passengers() {}

    public Passengers(int passengerId, String firstName, String lastName, String gender, String nationality, String passport) {
        this.passengerId = passengerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = Gender.valueOf(gender.substring(0, 1).toUpperCase() + gender.substring(1).toLowerCase());
        this.nationality = nationality;
        this.passport = passport;
    }

    @Override
    public String toString() {
        return "\n----------------------------------------\n" +
                "Passenger ID: " + passengerId +
                "\nFull Name: " + firstName + ' ' + lastName +
                "\nGender: " + gender +
                "\nNationality: " + nationality +
                "\nPassport: " + passport +
                "\n----------------------------------------";
    }
}
