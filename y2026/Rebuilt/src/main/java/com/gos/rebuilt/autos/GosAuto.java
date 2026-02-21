package com.gos.rebuilt.autos;

import com.gos.rebuilt.enums.AutoActions;
import com.gos.rebuilt.enums.GainBalls;
import com.gos.rebuilt.enums.StartingPositions;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class GosAuto extends SequentialCommandGroup {
    private final StartingPositions m_startingPosition;
    private final GainBalls m_gainBalls;
    private final AutoActions m_autoActions;


    public GosAuto(StartingPositions startingPos, GainBalls gainBalls, AutoActions autoac) {
        m_startingPosition = startingPos;
        m_gainBalls = gainBalls;
        m_autoActions = autoac;

    }

    public StartingPositions getStartingLocation() {
        return m_startingPosition;
    }

    public GainBalls getGainBalls() {
        return m_gainBalls;
    }

    public AutoActions getAutoActions() {
        return m_autoActions;
    }

}
