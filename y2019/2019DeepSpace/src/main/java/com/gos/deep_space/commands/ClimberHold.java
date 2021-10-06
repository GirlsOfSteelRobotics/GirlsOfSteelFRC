/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//This command means that the entire robot goes UP

package com.gos.deep_space.commands;

import com.gos.deep_space.subsystems.Climber;
import edu.wpi.first.wpilibj.command.Command;

public class ClimberHold extends Command {

    private final Climber m_climber;

    public ClimberHold(Climber climber) {
        m_climber = climber;
        requires(m_climber);
    }


    @Override
    protected void initialize() {
        System.out.println("ClimberHold init");
    }


    @Override
    protected void execute() {
        m_climber.holdClimberPosition(Climber.ClimberType.All);
        //System.out.println("Front Position: " + Robot.climber.getFrontPosition() + " Back Position: " + Robot.climber.getBackPosition());
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
        m_climber.climberStop();
        System.out.println("ClimberHold end");
    }
}
