package com.gos.reefscape.enums;

import com.gos.reefscape.subsystems.PivotSubsystem;
import edu.wpi.first.math.util.Units;

public enum PIECoral {
    HUMAN_PLAYER_STATION(Units.inchesToMeters(0), PivotSubsystem.DEFAULT_ANGLE),
    L1(0, -90),
    L2(0.30, -51),
    L3(0.72, -51),
    L4(1.27, -54);


    public PIESetpoint m_setpoint;

    PIECoral(double height, double angle) {
        this(new PIESetpoint(height, angle));
    }

    PIECoral(PIESetpoint setpoint) {
        m_setpoint = setpoint;
    }
}
