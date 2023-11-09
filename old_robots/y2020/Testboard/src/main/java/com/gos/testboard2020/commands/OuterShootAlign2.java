package com.gos.testboard2020.commands;

import com.gos.testboard2020.subsystems.Limelight;
import edu.wpi.first.wpilibj2.command.Command;

public class OuterShootAlign2 extends Command {

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
