/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.commands;

import com.gos.deep_space.subsystems.Climber;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberManual extends CommandBase {
    private final Climber m_climber;

    private final boolean m_directionExtend;
    private final Climber.ClimberType m_type;

    public ClimberManual(Climber climber, boolean directionExtend, Climber.ClimberType climberType) {
        this.m_directionExtend = directionExtend;
        m_type = climberType;
        m_climber = climber;

        addRequirements(m_climber);
    }


    @Override
    public void initialize() {
    }


    @Override
    public void execute() {
        m_climber.holdClimberPosition(m_type);
        m_climber.extendClimber(m_directionExtend, m_type);

    }


    @Override
    public boolean isFinished() {
        return false;
    }


    @Override
    public void end(boolean interrupted) {
        m_climber.climberStop();
    }

}
