package edu.wpi.first.wpilibj;

public final class DriverStationLCD {

    private static final DriverStationLCD INSTANCE = new DriverStationLCD();


    public enum Line {
        kUser2,
        kUser3,
        kUser4,
        kUser5,
        kUser6;
    }

    private DriverStationLCD() {

    }

    public static DriverStationLCD getInstance() {
        return INSTANCE;
    }


    public void println(Line line, int lineNumber, String message) {
        System.out.println(message);
    }

    public void updateLCD() {

    }
}
