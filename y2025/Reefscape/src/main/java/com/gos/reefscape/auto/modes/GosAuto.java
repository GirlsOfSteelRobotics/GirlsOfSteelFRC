package com.gos.reefscape.auto.modes;

import com.gos.reefscape.enums.AlgaePositions;
import com.gos.reefscape.enums.CoralPositions;
import com.gos.reefscape.enums.StartingPositions;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.util.List;

public class GosAuto extends SequentialCommandGroup {
    private final StartingPositions m_startingPosition;
    private final List<CoralPositions> m_coralPosition;
    private final List<AlgaePositions> m_algaePosition;


    public GosAuto(StartingPositions startingPos, List<CoralPositions> coralPos, List<AlgaePositions> algaePos) {
        m_startingPosition = startingPos;
        m_coralPosition  = coralPos;
        m_algaePosition = algaePos;
    }

    public StartingPositions getStartingLocation() {
        return m_startingPosition;
    }

    public List<CoralPositions> getCoralPositions() {
        return m_coralPosition;
    }

    public List<AlgaePositions> getAlgaePositions() {
        return m_algaePosition;
    }
}
