package com.gos.infinite_recharge.commands;

import com.gos.infinite_recharge.subsystems.Limelight;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class OuterShootAlign2 extends CommandBase {

    private final Limelight m_limelight;
    //private final Chassis m_chassis;

    public OuterShootAlign2(Limelight limelight) {
        this.m_limelight = limelight;
        //this.m_chassis = chassis;

        addRequirements(limelight);

    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_limelight.getDriveCommand(m_limelight.estimateDistance());
        //m_limelight.getSteerCommand();
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
