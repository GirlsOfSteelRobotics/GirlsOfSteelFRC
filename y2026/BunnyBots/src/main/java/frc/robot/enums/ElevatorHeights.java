package frc.robot.enums;

import edu.wpi.first.math.util.Units;

public enum ElevatorHeights {
    OVENHEIGHT(Units.inchesToMeters(12)),
    SHELF1(Units.inchesToMeters(43)),
    SHELF2(Units.inchesToMeters(55)),
    SHELF3(Units.inchesToMeters(67));

    public double m_height;

    ElevatorHeights(double height) {
        m_height = height;
    }

    public String variableName() {
        switch (this) {
        case OVENHEIGHT:
            return "ovenheight";
        case SHELF1:
            return "shelf1";
        case SHELF2:
            return "shelf2";
        case SHELF3:
            return "shelf3";
        default:
            throw new IllegalArgumentException();
        }
    }
}