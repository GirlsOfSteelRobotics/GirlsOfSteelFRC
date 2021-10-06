/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.commands;

import com.gos.deep_space.subsystems.Hatch;
import edu.wpi.first.wpilibj.command.Command;

public class HatchRelease extends Command {
    private final Hatch m_hatch;

    public HatchRelease(Hatch hatch) {
        m_hatch = hatch;
        requires(m_hatch);
    }


    @Override
    protected void initialize() {
        System.out.println("init hatch release");
    }


    @Override
    protected void execute() {
        m_hatch.release();
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
        m_hatch.stop();
        System.out.println("end hatch release");
    }
}
