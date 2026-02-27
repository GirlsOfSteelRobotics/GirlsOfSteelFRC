package com.gos.rebuilt.autos;

import com.gos.rebuilt.enums.AutoActions;
import com.gos.rebuilt.enums.StartingPositions;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.List;


public class GosAuto extends SequentialCommandGroup {
    private final StartingPositions m_startingPosition;
    private final List<AutoActions> m_autoActions;


    public GosAuto(StartingPositions startingPos, List<AutoActions> autoac) {
        m_startingPosition = startingPos;
        m_autoActions = autoac;

    }

    public StartingPositions getStartingLocation() {
        return m_startingPosition;
    }

    public List<AutoActions> getAutoActions() {
        return m_autoActions;
    }

}
