package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chassis;
import frc.robot.subsystems.LimelightSubsystem;


public class AimWithLimelightCommand extends CommandBase {

    private static final double YAW_ALLOWABLE_ERROR = 3;
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
        double currentYaw = m_limelightSubsystem.limelightAngle();
        m_chassis.driveByJoystick(0, Math.signum(currentYaw) * .25);
    }

    @Override
    public boolean isFinished() {
        double currentYaw = m_limelightSubsystem.limelightAngle();
        boolean complete = Math.abs(currentYaw) < YAW_ALLOWABLE_ERROR;
        return complete;


    }

    @Override
    public void end(boolean interrupted) {

    }
}
