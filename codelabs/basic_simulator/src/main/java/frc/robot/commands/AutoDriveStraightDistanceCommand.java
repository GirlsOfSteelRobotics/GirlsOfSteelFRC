package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ChassisSubsystem;

public class AutoDriveStraightDistanceCommand extends CommandBase {

    public static final double ALLOWABLE_ERROR = .5;
    private static final double KP = .3;

    private final ChassisSubsystem m_chassis;
    private final double m_goalDistance;

    private double m_error;

    public AutoDriveStraightDistanceCommand(ChassisSubsystem chassis, double goalDistance) {
        m_chassis = chassis;
        m_goalDistance = goalDistance;

        addRequirements(chassis);
    }

    @Override
    public void execute() {
        // TODO implement
    }

    @Override
    public boolean isFinished() {
        // TODO implement
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        // TODO implement
    }
}
