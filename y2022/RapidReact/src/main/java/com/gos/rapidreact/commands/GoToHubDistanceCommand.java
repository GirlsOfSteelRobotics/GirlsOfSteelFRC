package com.gos.rapidreact.commands;

import com.gos.rapidreact.subsystems.ShooterLimelightSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ChassisSubsystem;

public class GoToHubDistanceCommand extends CommandBase {
    private final ChassisSubsystem m_chassis;
    private final ShooterLimelightSubsystem m_limelight;
    private final double m_distanceGoal;
    private boolean m_atPosition;

    public GoToHubDistanceCommand(ChassisSubsystem chassisSubsystem, ShooterLimelightSubsystem shooterLimelightSubsystem, double distanceGoal) {
        this.m_chassis = chassisSubsystem;
        this.m_limelight = new ShooterLimelightSubsystem();
        m_distanceGoal = distanceGoal;

        addRequirements(this.m_chassis, this.m_limelight);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_atPosition = m_chassis.distancePID(m_limelight.getDistanceToHub(), m_distanceGoal);
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
