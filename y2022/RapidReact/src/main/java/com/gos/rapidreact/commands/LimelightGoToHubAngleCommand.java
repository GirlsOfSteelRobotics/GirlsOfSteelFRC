package com.gos.rapidreact.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.ShooterLimelightSubsystem;


public class LimelightGoToHubAngleCommand extends CommandBase {
    private final ChassisSubsystem m_chassis;
    private final ShooterLimelightSubsystem m_limelight;
    private boolean m_atPosition;

    public LimelightGoToHubAngleCommand(ChassisSubsystem chassisSubsystem, ShooterLimelightSubsystem shooterLimelightSubsystem) {
        this.m_chassis = chassisSubsystem;
        this.m_limelight = shooterLimelightSubsystem;

        addRequirements(this.m_chassis);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (m_limelight.isVisible()) {
            m_atPosition = m_chassis.turnPID(m_chassis.getYawAngle() - m_limelight.angleError());
            System.out.println("yaw angle: " + m_chassis.getYawAngle());
            System.out.println("angle error: " + m_limelight.angleError());
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
