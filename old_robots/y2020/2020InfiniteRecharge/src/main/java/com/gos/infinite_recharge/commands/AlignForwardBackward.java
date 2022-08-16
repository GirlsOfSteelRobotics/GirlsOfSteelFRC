package com.gos.infinite_recharge.commands;

import com.gos.infinite_recharge.subsystems.Limelight;
import com.gos.infinite_recharge.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AlignForwardBackward extends CommandBase {

    private final Limelight m_limelight;
    private final Chassis m_chassis;

    public AlignForwardBackward(Chassis chassis, Limelight limelight) {
        this.m_limelight = limelight;
        this.m_chassis = chassis;

        addRequirements(limelight, chassis);

    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_chassis.setSpeedAndSteer(m_limelight.getDriveCommand(), m_limelight.getSteerCommand());
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
