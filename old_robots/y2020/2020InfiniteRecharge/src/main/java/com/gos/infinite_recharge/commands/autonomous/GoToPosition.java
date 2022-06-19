package com.gos.infinite_recharge.commands.autonomous;

import com.gos.infinite_recharge.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.gos.lib.properties.PropertyManager;

public class GoToPosition extends CommandBase {

    private static final double MAX_SPEED = .8;

    private final Chassis m_chassis;

    private final double m_allowableError;
    private double m_errorDistance;

    private double m_errorAngle;

    private double m_hyp;

    private final double m_finalPositionX;
    private final double m_finalPositionY;

    private static final PropertyManager.IProperty<Double> AUTO_KP = PropertyManager.createDoubleProperty(false, "DriveToPointDriveKp", 0.04);
    private static final PropertyManager.IProperty<Double> AUTO_KP_ANGLE = PropertyManager.createDoubleProperty(false, "DriveToPointSteerKp",  0.25);

    public GoToPosition(Chassis chassis, double finalPositionX, double finalPositionY, double allowableError) {
        // Use requires() here to declare subsystem dependencies
        //super.addRequirements(Shooter); When a subsystem is written, add the requires line back in.
        this.m_chassis = chassis;
        this.m_allowableError = allowableError;

        m_finalPositionX = finalPositionX;
        m_finalPositionY = finalPositionY;

        System.out.println("finalPositionX" + finalPositionX + "finalPositionY" + finalPositionY);
    }

    @Override
    public void initialize(){
    }


    @Override
    public void execute() {

        double currentAngle;
        double dx;
        double dy;
        double angle;

        dx = m_finalPositionX - m_chassis.getXInches();
        dy = m_finalPositionY - m_chassis.getYInches();
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

        System.out.println("angle " + angle + ", hyp " + m_hyp + ", speed " + speed + ", turnSpeed " + turnSpeed
            + ", currentAngle " + currentAngle + ", errorAngle " + m_errorAngle + ", allowableError" + m_allowableError);

        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        if (speed < -MAX_SPEED) {
            speed = -MAX_SPEED;
        }
        m_chassis.setSpeedAndSteer(speed, -turnSpeed);

    }

    @SuppressWarnings("PMD.AvoidReassigningParameters")
    public double calculateAngleBetweenNegAndPos180(double angle) {
        while (angle > 180) {
            angle -= 360;
        }
        while (angle < -180) {
            angle += 360;
        }
        return angle;
    }


    @Override
    public boolean isFinished() {
        if (m_hyp < m_allowableError) {
            System.out.println("Done!");
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public void end(boolean interrupted) {
        m_chassis.setSpeedAndSteer(0, 0);
    }
}
