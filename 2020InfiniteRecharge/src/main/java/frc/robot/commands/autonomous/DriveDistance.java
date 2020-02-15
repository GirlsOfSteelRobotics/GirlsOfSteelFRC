package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chassis;

public class DriveDistance extends CommandBase {

    private static final double AUTO_KP = 0.1;

    private final Chassis m_chassis;
    private final double m_distance;
    private final double m_allowableError;

    private double m_error;


    public DriveDistance(Chassis chassis, double distance, double allowableError) {
        this.m_chassis = chassis;

        m_distance = distance + chassis.getAverageEncoderDistance();
        m_allowableError = allowableError;

        addRequirements(chassis);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute() { 
        double currentDistance = m_chassis.getAverageEncoderDistance();
        m_error = m_distance - currentDistance;

        double speed = m_error * AUTO_KP;
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
