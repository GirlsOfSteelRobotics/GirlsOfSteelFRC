package frc.robot.autos;

import frc.robot.enums.StartingPositions;
import org.wpilib.command2.SequentialCommandGroup;


public class GosAuto extends SequentialCommandGroup {
    private final StartingPositions m_startingPosition;

    public GosAuto(StartingPositions startingPositions) {
        m_startingPosition = startingPositions;

    }

    public StartingPositions getStartingLocation() {
        return m_startingPosition;
    }
}
