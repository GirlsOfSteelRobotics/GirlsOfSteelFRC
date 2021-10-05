/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.offense2019.commands;

import com.gos.offense2019.Robot;
import com.gos.offense2019.subsystems.HatchCollector;
import edu.wpi.first.wpilibj.command.Command;

public class HatchCollect extends Command {
    private final HatchCollector.HatchState m_hatchState;

    public HatchCollect(HatchCollector.HatchState hatchState) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.m_hatchCollector);
        this.m_hatchState = hatchState;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("Shift(" + m_hatchState + ") init");
        Robot.m_hatchCollector.driveHatch(m_hatchState);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }
}
