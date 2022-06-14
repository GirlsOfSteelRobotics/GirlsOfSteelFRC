package com.gos.power_up.commands;

import com.gos.power_up.subsystems.Lift;
import edu.wpi.first.wpilibj2.command.InstantCommand;

/**
 *
 */
public class LiftEnterRecoveryMode extends InstantCommand {
    private final Lift m_lift;

    public LiftEnterRecoveryMode(Lift lift) {
        m_lift = lift;
        addRequirements(m_lift);
    }

    // Called once when the command executes
    @Override
    public void initialize() {
        m_lift.enterRecoveryMode();
    }

}
