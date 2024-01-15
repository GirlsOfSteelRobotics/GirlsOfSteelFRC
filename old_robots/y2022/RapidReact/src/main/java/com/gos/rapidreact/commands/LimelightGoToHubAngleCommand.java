package com.gos.rapidreact.commands;

import com.gos.rapidreact.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.ShooterLimelightSubsystem;


public class LimelightGoToHubAngleCommand extends Command {
    private final ChassisSubsystem m_chassis;
    private final ShooterLimelightSubsystem m_limelight;
    private final ShooterSubsystem m_shooter;
    private boolean m_atPosition;

    public LimelightGoToHubAngleCommand(ChassisSubsystem chassisSubsystem, ShooterLimelightSubsystem shooterLimelightSubsystem, ShooterSubsystem shooterSubsystem) {
        this.m_chassis = chassisSubsystem;
        this.m_limelight = shooterLimelightSubsystem;
        this.m_shooter = shooterSubsystem;

        addRequirements(this.m_chassis);
    }

    @Override
    public void initialize() {
        m_shooter.setShooterRpmPIDSpeed(ShooterSubsystem.TARMAC_EDGE_RPM_HIGH);
    }

    @Override
    public void execute() {
        if (m_limelight.isVisible()) {
            m_atPosition = m_chassis.turnPID(m_chassis.getOdometryAngle() - m_limelight.getAngle());
            // System.out.println("yaw angle: " + m_chassis.getYawAngle());
            // System.out.println("angle error: " + m_limelight.angleError());
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
