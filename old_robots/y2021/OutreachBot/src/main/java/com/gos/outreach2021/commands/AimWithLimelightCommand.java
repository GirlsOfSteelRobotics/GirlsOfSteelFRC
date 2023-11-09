package com.gos.outreach2021.commands;

import com.gos.outreach2021.subsystems.Chassis;
import com.gos.outreach2021.subsystems.LimelightSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


public class AimWithLimelightCommand extends Command {

    private final Chassis m_chassis;
    private final LimelightSubsystem m_limelightSubsystem;

    public AimWithLimelightCommand(Chassis chassis, LimelightSubsystem limelightSubsystem) {
        this.m_chassis = chassis;
        this.m_limelightSubsystem = limelightSubsystem;
        // each subsystem used by the command must be passed into the addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_chassis, this.m_limelightSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double errorYaw = m_limelightSubsystem.limelightAngle();
        //negative to account for difference in turning with limelight
        m_chassis.angleAim(-errorYaw);
    }

    @Override
    public boolean isFinished() {
        return m_chassis.allowedAngle();

    }

    @Override
    public void end(boolean interrupted) {

    }
}
