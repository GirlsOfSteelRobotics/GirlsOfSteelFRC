package com.gos.crescendo2024.auton;

import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import java.util.HashSet;
import java.util.List;

public class GosAutoMode extends SequentialCommandGroup {

    private static final GosDoubleProperty AUTON_TIMEOUT = new GosDoubleProperty(false, "autoTimeoutSeconds", 0);

    public enum StartPosition {
        STARTING_LOCATION_AMP_SIDE,
        STARTING_LOCATION_MIDDLE,
        STARTING_LOCATION_SOURCE_SIDE,
        CURRENT_LOCATION,
        STARTING_LOCATION_SOURCE_CORNER,
    }

    private final String m_displayName;
    private final StartPosition m_startingPosition;
    private final List<Integer> m_notesToAcquire;

    public GosAutoMode(String displayName, StartPosition startingPosition, List<Integer> notesToAcquire, Command... commands) {
        super(
            new DeferredCommand(() -> new WaitCommand(AUTON_TIMEOUT.getValue()), new HashSet<>())
                .andThen(commands));

        m_displayName = displayName;
        m_startingPosition = startingPosition;
        m_notesToAcquire = notesToAcquire;
    }

    public String getDisplayName() {
        return m_displayName;
    }


    public StartPosition getStartingLocation() {
        return m_startingPosition;
    }

    public List<Integer> getNotesToAquire() {
        return m_notesToAcquire;
    }
}
