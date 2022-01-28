package com.gos.rapidreact.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ChassisSubsystem;


public class DriveOffTarmacCommand extends CommandBase {

    private final ChassisSubsystem m_chassis;
    private final double m_distance;
    private final double m_allowableError;
    private double m_initialPosition;
    private double m_error;

    public DriveOffTarmacCommand(ChassisSubsystem chassis) {
        m_chassis = chassis;

        //TODO: distance and allowable error as variables in DriveOffTarmac and AutoModeFactory?
        m_distance = distance;
        m_allowableError = allowableError;

        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(chassis);
    }

    @Override
    public void initialize() {
        m_initialPosition = m_chassis.getAverageEncoderDistance();
    }

    @Override
    public void execute() {
        double currentPosition = m_chassis.getAverageEncoderDistance();
        m_error = m_distance - (currentPosition - m_initialPosition);

        double speed = m_error * AUTO_KP.getValue();
        double steer = 0;
        m_chassis.setArcadeDrive(speed, steer);

    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
