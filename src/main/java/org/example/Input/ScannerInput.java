package org.example.Input;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class ScannerInput {
    static Scanner sc = new Scanner(System.in);

    public static int getInt() {
        while (!sc.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            sc.next();
        }
        int value = sc.nextInt();
        sc.nextLine();
        return value;
    }

    public static String getString() {
        return sc.nextLine();
    }

    public static String getNext() {
        return sc.next();
    }

    public static byte getByte() {
        while (!sc.hasNextByte()) {
            System.out.println("Invalid input. Please enter a valid byte.");
            sc.next();
        }
        byte value = sc.nextByte();
        sc.nextLine();
        return value;
    }

    public static short getShort() {
        while (!sc.hasNextShort()) {
            System.out.println("Invalid input. Please enter a valid short.");
            sc.next();
        }
        short value = sc.nextShort();
        sc.nextLine();
        return value;
    }

    public static char getChar() {
        return sc.next().charAt(0);
    }

    public static long getLong() {
        while (!sc.hasNextLong()) {
            System.out.println("Invalid input. Please enter a valid long.");
            sc.next();
        }
        long value = sc.nextLong();
        sc.nextLine();
        return value;
    }

    public static float getFloat() {
        while (!sc.hasNextFloat()) {
            System.out.println("Invalid input. Please enter a valid float.");
            sc.next();
        }
        float value = sc.nextFloat();
        sc.nextLine();
        return value;
    }

    public static double getDouble() {
        while (!sc.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid double.");
            sc.next();
        }
        double value = sc.nextDouble();
        sc.nextLine();
        return value;
    }

    public static Date getDate() {

        String dateInput = sc.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy");
        sdf.setLenient(false);
        try {
            java.util.Date utilDate = sdf.parse(dateInput);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use dd:MM:yyyy.");
            return getDate();
        }
    }
}
