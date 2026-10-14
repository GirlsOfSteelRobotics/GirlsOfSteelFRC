package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class TurnToAngleCommand extends Command {

    public static final double ALLOWABLE_ERROR = 3; // degrees

    private final ChassisSubsystem m_chassis;
    private final double m_goalAngle;

    private double m_error;

    /**
     * Spins the robot in place until it is facing the given direction.
     *
     * @param chassis The chassis subsystem
     * @param goalAngle The direction to face, in degrees. Positive is counter-clockwise (turning left)
     */
    public TurnToAngleCommand(ChassisSubsystem chassis, double goalAngle) {
        m_chassis = chassis;
        m_goalAngle = goalAngle;

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
