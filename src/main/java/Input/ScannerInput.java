package Input;

import java.util.Scanner;

public class ScannerInput {
    static Scanner sc = new Scanner(System.in);

    public static int getInt() {
        return sc.nextInt();
    }

    public static String getString() {
        return sc.nextLine();
    }

    public static byte getByte() {
        return sc.nextByte();
    }

    public static short getShort() {
        return sc.nextShort();
    }

    public static char getChar() {
        return sc.next().charAt(0);
    }

    public static long getLong() {
        return sc.nextLong();
    }

    public static float getFloat() {
        return sc.nextFloat();
    }

    public static double getDouble() {
        return sc.nextDouble();
    }
}
