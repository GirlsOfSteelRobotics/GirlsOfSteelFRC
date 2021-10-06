/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.commands;

import com.gos.deep_space.subsystems.Hatch;
import edu.wpi.first.wpilibj.command.Command;


public class HatchCollect extends Command {
    private final Hatch m_hatch;

    public HatchCollect(Hatch hatch) {
        m_hatch = hatch;
        requires(m_hatch);
    }


    @Override
    protected void initialize() {
        System.out.println("init hatch collect");
    }


    @Override
    protected void execute() {
        m_hatch.collect();
    }


    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void end() {
        m_hatch.slowCollect();
        System.out.println("end hatch collect");
    }
}
