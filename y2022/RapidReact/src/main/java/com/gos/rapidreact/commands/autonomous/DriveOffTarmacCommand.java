package com.gos.rapidreact.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.lib.properties.PropertyManager;


public class DriveOffTarmacCommand extends CommandBase {

    private static final PropertyManager.IProperty<Double> AUTO_KP = new PropertyManager.DoubleProperty("DriveOffTarmacKP", 0.5);

    private final ChassisSubsystem m_chassis;
    private final double m_distance;
    private final double m_allowableError;
    private double m_initialPosition;
    private double m_error;


    public DriveOffTarmacCommand(ChassisSubsystem chassis, double distance, double allowableError) {

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
        if (Math.abs(m_error) < m_allowableError) {
            System.out.println("Done!");
            return true;
        }
        else {
            System.out.println("drive to distance" + "error:" + m_error + "allowableError" + m_allowableError);
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {

    }
}
