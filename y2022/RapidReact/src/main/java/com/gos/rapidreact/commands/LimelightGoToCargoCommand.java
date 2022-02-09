package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.IntakeLimelightSubsystem;


public class LimelightGoToCargoCommand extends CommandBase {
    private final ChassisSubsystem m_chassis;
    private final IntakeLimelightSubsystem m_limelight;
    private boolean m_atPosition;

    public LimelightGoToCargoCommand(ChassisSubsystem chassisSubsystem, IntakeLimelightSubsystem intakeLimelightSubsystem) {
        this.m_chassis = chassisSubsystem;
        this.m_limelight = intakeLimelightSubsystem;

        addRequirements(this.m_chassis, this.m_limelight);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (m_limelight.isVisible()) {
            m_atPosition = m_chassis.driveAndTurnPID(m_limelight.distanceToCargo(), m_limelight.getAngle());
        }
    }

    @Override
    public boolean isFinished() {
        return m_atPosition;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.setArcadeDrive(0, 0);
    }
}
