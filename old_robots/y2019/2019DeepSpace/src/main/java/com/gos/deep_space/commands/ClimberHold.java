/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//This command means that the entire robot goes UP

package com.gos.deep_space.commands;

import com.gos.deep_space.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberHold extends CommandBase {

    private final Climber m_climber;

    public ClimberHold(Climber climber) {
        m_climber = climber;
        addRequirements(m_climber);
    }


    @Override
    public void initialize() {
        System.out.println("ClimberHold init");
    }


    @Override
    public void execute() {
        m_climber.holdClimberPosition(Climber.ClimberType.ALL);
        //System.out.println("Front Position: " + Robot.climber.getFrontPosition() + " Back Position: " + Robot.climber.getBackPosition());
    }


    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interrupted) {
        m_climber.climberStop();
        System.out.println("ClimberHold end");
    }
}
