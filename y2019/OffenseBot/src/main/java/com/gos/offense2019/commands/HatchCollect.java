/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.offense2019.commands;

import com.gos.offense2019.subsystems.HatchCollector;
import edu.wpi.first.wpilibj.command.Command;

public class HatchCollect extends Command {
    private final HatchCollector m_hatchCollector;
    private final HatchCollector.HatchState m_hatchState;

    public HatchCollect(HatchCollector hatch, HatchCollector.HatchState hatchState) {
        m_hatchCollector = hatch;
        requires(m_hatchCollector);
        this.m_hatchState = hatchState;
    }


    @Override
    protected void initialize() {
        System.out.println("Shift(" + m_hatchState + ") init");
        m_hatchCollector.driveHatch(m_hatchState);
    }


    @Override
    protected void execute() {
    }


    @Override
    protected boolean isFinished() {
        return true;
    }


    @Override
    protected void end() {
    }
}
