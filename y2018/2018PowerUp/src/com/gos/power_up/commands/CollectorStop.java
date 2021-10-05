package com.gos.power_up.commands;

import com.gos.power_up.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class CollectorStop extends InstantCommand {

    public CollectorStop() {
        super();
        // Use requires() here to declare subsystem dependencies
        requires(Robot.collector);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.collector.stop();
    }

}
