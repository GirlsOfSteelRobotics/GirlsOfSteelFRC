/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.commands;

import com.gos.deep_space.subsystems.Pivot;
import edu.wpi.first.wpilibj.command.Command;

public class PivotToRocket extends Command {
    private final Pivot m_pivot;

    public PivotToRocket(Pivot pivot) {
        m_pivot = pivot;
        requires(m_pivot);
    }


    @Override
    protected void initialize() {
        System.out.println("init PivotToRocket ");
    }


    @Override
    protected void execute() {
        m_pivot.setGoalPivotPosition(Pivot.PIVOT_ROCKET);
        m_pivot.holdPivotPosition();
    }


    @Override
    protected boolean isFinished() {
        return m_pivot.checkCurrentPivotPosition(Pivot.PIVOT_ROCKET);
    }


    @Override
    protected void end() {
        m_pivot.pivotStop();
        System.out.println("end PivotToRocket");
    }
}
