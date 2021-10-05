package com.gos.power_up.commands;

import com.gos.power_up.Robot;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class LiftEnterRecoveryMode extends InstantCommand {

    public LiftEnterRecoveryMode() {
        super();
        // Use requires() here to declare subsystem dependencies
        requires(Robot.lift);
    }

    // Called once when the command executes
    @Override
    protected void initialize() {
        Robot.lift.enterRecoveryMode();
    }

}
