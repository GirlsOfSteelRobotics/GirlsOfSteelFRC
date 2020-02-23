package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.lib.PropertyManager;
import frc.robot.subsystems.Chassis;

public class GoToPosition extends CommandBase {

    Chassis m_chassis;

    private double m_allowableError;
    private double m_errorDistance;

    private double m_errorAngle;

    private double m_hyp;

    private double m_finalPositionX;
    private double m_finalPositionY;

    private static final PropertyManager.IProperty<Double> AUTO_KP = new PropertyManager.DoubleProperty("DriveToPointDriveKp", 0.03);
    private static final PropertyManager.IProperty<Double> AUTO_KP_ANGLE = new PropertyManager.DoubleProperty("DriveToPointSteerKp",  0.1);

    public GoToPosition(Chassis chassis, double finalPositionX, double finalPositionY, double allowableError) {
        // Use requires() here to declare subsystem dependencies
        //super.addRequirements(Shooter); When a subsystem is written, add the requires line back in.
        this.m_chassis = chassis;

        m_finalPositionX = finalPositionX;
        m_finalPositionY = finalPositionY;

        System.out.println("finalPositionX" + finalPositionX + "finalPositionY" + finalPositionY);
    }

    public void initialize(){
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute() {

        double currentAngle;
        double dx;
        double dy; 
        double angle;

        dx = m_finalPositionX - m_chassis.getX();
        dy = m_finalPositionY - m_chassis.getY();
        m_hyp = Math.sqrt((dx * dx) + (dy * dy));
        angle = Math.toDegrees(Math.atan2(dy, dx));

        m_errorDistance = m_hyp;
        double speed = m_errorDistance * AUTO_KP.getValue();

        currentAngle = m_chassis.getHeading();

        currentAngle = calculateAngleBetweenNegAndPos180(currentAngle);

        m_errorAngle = angle - currentAngle;

        if (!(m_errorAngle > -90 && m_errorAngle < 90)) {
            System.out.println("Go backwards");
            m_errorAngle -= 180;
            speed = -speed;
        }

        m_errorAngle = calculateAngleBetweenNegAndPos180(m_errorAngle);

        double turnSpeed = m_errorAngle * AUTO_KP_ANGLE.getValue();

        System.out.println("angle " + angle + ", hyp " + m_hyp + ", speed " + speed + ", turnSpeed " + turnSpeed + ", currentAngle " + currentAngle + ", errorAngle " + m_errorAngle);

        if (speed > .5) {
            speed = .5;
        }
        if (speed < -.5) {
            speed = -.5;
        }
        m_chassis.setSpeedAndSteer(speed, turnSpeed);

    }
    
    public double calculateAngleBetweenNegAndPos180(double angle) {
        while (angle > 180) {
            angle -= 360;
        }
        while (angle < -180) {
            angle += 360;
        }
        return angle;
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        if (m_hyp < m_allowableError) {
            System.out.println("Done!");
            return true;
        }
        else {
            return false;
        }
    }

    // Called once after isFinished returns true
    public void end(boolean interrupted) {
        m_chassis.setSpeedAndSteer(0, 0);
    }
}
