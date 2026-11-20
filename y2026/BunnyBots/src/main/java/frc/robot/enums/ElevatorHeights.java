package frc.robot.enums;

import edu.wpi.first.math.util.Units;
import frc.robot.subsystems.ElevatorSubsystem;

public enum ElevatorHeights {
    OVENHEIGHT(Units.inchesToMeters(12)),
    SHELF1(Units.inchesToMeters(43)),
    SHELF2(Units.inchesToMeters(55)),
    SHELF3(Units.inchesToMeters(67)),
    NOGOALPOSITION(ElevatorSubsystem.NO_GOAL_HEIGHT);

    public double m_height;

    ElevatorHeights(double height) {
        m_height = height;
    }

}
