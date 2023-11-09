/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.offense2019.commands;

import com.gos.offense2019.subsystems.HatchCollector;
import edu.wpi.first.wpilibj2.command.Command;

public class HatchCollect extends Command {
    private final HatchCollector m_hatchCollector;
    private final HatchCollector.HatchState m_hatchState;

    public HatchCollect(HatchCollector hatch, HatchCollector.HatchState hatchState) {
        m_hatchCollector = hatch;
        addRequirements(m_hatchCollector);
        this.m_hatchState = hatchState;
    }


    @Override
    public void initialize() {
        System.out.println("Shift(" + m_hatchState + ") init");
        m_hatchCollector.driveHatch(m_hatchState);
    }


    @Override
    public void execute() {
    }


    @Override
    public boolean isFinished() {
        return true;
    }


    @Override
    public void end(boolean interrupted) {
    }
}
