package org.usfirst.frc.team3504.robot.commands;

import org.usfirst.frc.team3504.robot.Robot;

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
    protected void initialize() {
    	Robot.lift.enterRecoveryMode();
    }

}
