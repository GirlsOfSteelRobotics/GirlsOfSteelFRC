package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.enums.StartingPositions;


public class GosAuto extends SequentialCommandGroup {
    private final StartingPositions m_startingPosition;

    public GosAuto(StartingPositions startingPositions) {
        m_startingPosition = startingPositions;

    }

    public StartingPositions getStartingLocation() {
        return m_startingPosition;
    }
}
