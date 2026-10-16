package com.gos.codelabs.basic_simulator.commands;

import com.gos.codelabs.basic_simulator.subsystems.ChassisSubsystem;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;

public class AutoDriveStraightDistanceCommand extends Command {

    public static final double ALLOWABLE_ERROR = Units.inchesToMeters(3);

    private final ChassisSubsystem m_chassis;
    private final double m_goalDistance;

    private double m_error;

    /**
     * Drives straight until the robot has gone the given distance.
     *
     * @param chassis The chassis subsystem
     * @param goalDistance How far to drive, in meters. Negative drives backwards
     */
    public AutoDriveStraightDistanceCommand(ChassisSubsystem chassis, double goalDistance) {
        m_chassis = chassis;
        m_goalDistance = goalDistance;

        addRequirements(chassis);
    }

    @Override
    public void execute() {
        // TODO implement
        m_error = m_goalDistance - m_chassis.getAverageDistance();
        if (m_error > 0) {
            m_chassis.arcadeDrive(0.5, 0);
        } else if (m_error < 0) {
            m_chassis.arcadeDrive(-0.5, 0);
        }
    }

    @Override
    public boolean isFinished() {
        // TODO implement
        if (Math.abs(m_goalDistance - m_chassis.getAverageDistance()) <= ALLOWABLE_ERROR) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        // TODO implement
        m_chassis.stop();
    }
}
