/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.commands;

import com.gos.deep_space.subsystems.Pivot;
import com.gos.deep_space.subsystems.Pivot.PivotDirection;
import edu.wpi.first.wpilibj.command.Command;

public class PivotManual extends Command {
    private final Pivot m_pivot;

    private final PivotDirection m_direction;

    public PivotManual(Pivot pivot, PivotDirection direction) {
        this.m_direction = direction;
        this.m_pivot = pivot;
        requires(m_pivot);
    }


    @Override
    protected void initialize() {
        System.out.println("init PivotManual " + m_direction);

    }


    @Override
    protected void execute() {
        if (m_direction == PivotDirection.Up) {
            m_pivot.incrementPivot();
        } else {
            m_pivot.decrementPivot();
        }
        m_pivot.holdPivotPosition();
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
        m_pivot.pivotStop();
        System.out.println("end PivotManual " + m_direction);
    }
}
