/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.commands;

import com.gos.deep_space.subsystems.Blinkin;
import com.gos.deep_space.subsystems.Climber;
import com.gos.deep_space.subsystems.Climber.ClimberType;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ClimberToSetPoint extends CommandBase {
    private final Climber m_climber;
    private final Blinkin m_blinkin;

    private final double m_setPoint;
    private final ClimberType m_type;

    public ClimberToSetPoint(Climber climber, Blinkin blinkin, double setPoint, ClimberType climberType) {
        this.m_setPoint = setPoint;
        m_type = climberType;
        m_blinkin = blinkin;
        m_climber = climber;

        addRequirements(m_climber);
        addRequirements(m_blinkin);
    }


    @Override
    public void initialize() {
        m_climber.setGoalClimberPosition(m_setPoint, m_type);
        System.out.println("init Climber To " + m_setPoint);
    }


    @Override
    public void execute() {
        System.out.println("goal front position: " + m_climber.m_goalFrontPosition + "actual front position: "
            + m_climber.getFrontPosition());
        System.out.println("goal back position" + m_climber.m_goalBackPosition + "actual back position: "
            + m_climber.getBackPosition());

        m_climber.holdClimberPosition(m_type);

    }


    @Override
    public boolean isFinished() {
        return m_climber.checkCurrentPosition(m_setPoint, m_type);

    }


    @Override
    public void end(boolean interrupted) {
        m_climber.climberStop();
        System.out.println("end Climber To " + m_setPoint);

    }

}
