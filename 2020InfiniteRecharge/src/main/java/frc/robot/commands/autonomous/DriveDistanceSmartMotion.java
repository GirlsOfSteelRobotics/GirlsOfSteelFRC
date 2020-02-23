package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Chassis;

public class DriveDistanceSmartMotion extends CommandBase {


    private final Chassis m_chassis;
    private final double m_distance;
    private final double m_allowableError;


    public DriveDistanceSmartMotion(Chassis chassis, double distance, double allowableError) {
        this.m_chassis = chassis;

        m_distance = distance;
        m_allowableError = allowableError;

        addRequirements(chassis);
    }

    @Override
    public void initialize(){
        System.out.println("Drive Distance Smart Motion; distnace " + m_distance + ", allowable error " + m_allowableError);
    }

    @Override
    public void execute() { 
        m_chassis.driveDistance(m_distance, m_distance);

        //System.out.println("error:" + m_error + "speed:" + speed);
    }

    @Override
    public boolean isFinished() {
        double error;
        error = m_chassis.getAverageEncoderDistance() - m_distance;
        if (Math.abs(error) < m_allowableError) {
            System.out.println("Done!");
            return true;
        }
        else {
            System.out.println("drive to distance" + "error:" + error + "allowableError" + m_allowableError);
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.setSpeed(0);
        System.out.println("Drive Distance Smart Motion, end");
       
    }
}
