package com.gos.infinite_recharge.commands.autonomous;

import com.gos.infinite_recharge.subsystems.Chassis;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.wpilibj2.command.Command;

public class DriveDistance extends Command {

    private static final GosDoubleProperty AUTO_KP = new GosDoubleProperty(false, "DriveDistanceKp", 0.5);

    private final Chassis m_chassis;
    private final double m_distance;
    private final double m_allowableError;
    private double m_initialPosition;
    private double m_error;


    public DriveDistance(Chassis chassis, double distance, double allowableError) {
        this.m_chassis = chassis;

        m_distance = distance;
        m_allowableError = allowableError;

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
        m_chassis.setSpeed(speed);

        //System.out.println("error:" + m_error + "speed:" + speed);
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
        m_chassis.setSpeed(0);
    }
}
