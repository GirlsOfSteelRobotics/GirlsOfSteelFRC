package com.gos.outreach2021.commands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.outreach2021.subsystems.LimelightSubsystem;
import com.gos.outreach2021.subsystems.Shooter;


public class LimelightHoodControlCommand extends Command {
    private final LimelightSubsystem m_limelightSubsystem;
    private final Shooter m_shooter;
    private static final double HOOD_MAX = 85;
    private static final double HOOD_MIN = 5;
    private static final double ROBOT_DISTANCE_MAX = Units.feetToMeters(27);
    private static final double ROBOT_DISTANCE_MIN = Units.feetToMeters(0);

    public LimelightHoodControlCommand(LimelightSubsystem limelightSubsystem, Shooter shooter) {
        this.m_limelightSubsystem = limelightSubsystem;
        this.m_shooter = shooter;
        // each subsystem used by the command must be passed into the addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_limelightSubsystem, this.m_shooter);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double distanceFromGoal = m_limelightSubsystem.limelightDistance();
        if (distanceFromGoal >= ROBOT_DISTANCE_MAX) {
            m_shooter.setHoodAngle(HOOD_MAX);
        } else if (distanceFromGoal <= ROBOT_DISTANCE_MIN) {
            m_shooter.setHoodAngle(HOOD_MIN);
        } else if (m_limelightSubsystem.targetExists()) {
            double hoodDifference = HOOD_MAX - HOOD_MIN;
            double distanceDifference = ROBOT_DISTANCE_MAX - ROBOT_DISTANCE_MIN;
            m_shooter.setHoodAngle((hoodDifference / distanceDifference) * distanceFromGoal + HOOD_MIN);
        }
        //doesn't do anything if it doesn't see the limelight
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
