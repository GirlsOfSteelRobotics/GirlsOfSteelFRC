/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.commands;

import com.gos.deep_space.Robot;
import com.gos.deep_space.subsystems.Climber.ClimberType;
import edu.wpi.first.wpilibj.command.Command;

public class ClimberToSetPoint extends Command {

    private final double m_setPoint;
    private final ClimberType m_type;

    public ClimberToSetPoint(double setPoint, ClimberType climberType) {
        this.m_setPoint = setPoint;
        m_type = climberType;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.m_climber);
        requires(Robot.m_blinkin);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.m_climber.setGoalClimberPosition(m_setPoint, m_type);
        System.out.println("init Climber To " + m_setPoint);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        System.out.println("goal front position: " + Robot.m_climber.m_goalFrontPosition + "actual front position: "
            + Robot.m_climber.getFrontPosition());
        System.out.println("goal back position" + Robot.m_climber.m_goalBackPosition + "actual back position: "
            + Robot.m_climber.getBackPosition());

        Robot.m_climber.holdClimberPosition(m_type);

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.m_climber.checkCurrentPosition(m_setPoint, m_type);

    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.m_climber.climberStop();
        System.out.println("end Climber To " + m_setPoint);

    }

}
