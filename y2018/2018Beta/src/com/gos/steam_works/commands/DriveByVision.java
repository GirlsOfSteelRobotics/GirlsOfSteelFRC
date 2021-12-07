package com.gos.steam_works.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.steam_works.GripPipelineListener;
import com.gos.steam_works.subsystems.Chassis;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveByVision extends Command {


    private static final int TIMEOUT = 8;
    private static final int SLIPPING_VELOCITY = 850;
    private static final double slowLinearVelocity = 22; // TODO: change (in/s)
    private static final double fastLinearVelocity = 28; // TODO: change (in/s)
    private static final double MAX_ANGULAR_VELOCITY = 1.0; // TODO: adjust

    // distance b/w wheels
    private static final double WHEEL_BASE = 24.25; // (in)

    // radius of wheel
    private static final int WHEEL_RADIUS = 2; // (in)

    private static final int IMAGE_WIDTH = 320;
    private static final double IMAGE_CENTER = IMAGE_WIDTH / 2.0;

    private final Chassis m_chassis;
    private final GripPipelineListener m_listener;
    private final WPI_TalonSRX m_leftTalon;
    private final WPI_TalonSRX m_rightTalon;
    private final Timer m_tim;

    // width of X or Y in pixels when the robot is at the lift
    // private static final double GOAL_WIDTH = 30; //TODO: test and change

    public DriveByVision(Chassis chassis, GripPipelineListener listener) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        m_chassis = chassis;
        m_listener = listener;
        m_leftTalon = m_chassis.getLeftTalon();
        m_rightTalon = m_chassis.getRightTalon();
        requires(m_chassis);
        m_tim = new Timer();
    }


    @Override
    protected void initialize() {

        // not calling setupFPID because other PID values override
        m_leftTalon.setSelectedSensorPosition(0, 0, 0);
        m_rightTalon.setSelectedSensorPosition(0, 0, 0);

        // tuned by janet and ziya on 2/20, overrides PID set in chassis method
        m_leftTalon.config_kF(0, 0.22, 0); //carpet on practice field
        m_leftTalon.config_kP(0, 0.235, 0);
        m_rightTalon.config_kF(0, 0.22, 0); //carpet on practice field
        m_rightTalon.config_kP(0, 0.235, 0);

        System.out.println("DriveByVision Initialized");

        m_tim.start();
    }


    @Override
    protected void execute() {

        /*table = NetworkTable.getTable("GRIP/myContoursReport");

        double[] centerX = new double[2];
        centerX = table.getNumberArray("centerX", defaultValue);*/
        /*
         * double[] centerY = new double[2]; centerY =
         * table.getNumberArray("centerY", defaultValue);

        double[] height = new double[2];
        height = table.getNumberArray("height", defaultValue);*/
        /*
         * double[] width = new double[2]; width = table.getNumberArray("width",
         * defaultValue);
         */
        double targetX;
        double height;
        synchronized (GripPipelineListener.cameraLock) {
            targetX = m_listener.targetX;
            height = m_listener.height;
        }
        // the center of the x and y rectangles (the target)
        double goalAngularVelocity;
        if (targetX < 0) {
            goalAngularVelocity = 0;
            SmartDashboard.putBoolean("Gear In Sight", false);
        } else {
            //double targetX = (centerX[0] + centerX[1]) / 2.0;
            double error = (targetX - IMAGE_CENTER) / IMAGE_CENTER;
            goalAngularVelocity = error * MAX_ANGULAR_VELOCITY;
            SmartDashboard.putBoolean("Gear In Sight", true);
            SmartDashboard.putNumber("TargetX", targetX);
            SmartDashboard.putNumber("Height", height);
        }


        double goalLinearVelocity;
        if (height < 0 && m_tim.get() < 1) {
            goalLinearVelocity = fastLinearVelocity;
        } else if (height < 0) {
            goalLinearVelocity = slowLinearVelocity;
        } else if (height >= 52.0) {
            goalLinearVelocity = slowLinearVelocity;
        } else {
            goalLinearVelocity = fastLinearVelocity;
        }


        // right and left desired wheel speeds in inches per second
        double vRight = goalLinearVelocity - (WHEEL_BASE * goalAngularVelocity) / 2; // (in/s)
        double vLeft = goalLinearVelocity + (WHEEL_BASE * goalAngularVelocity) / 2;
        // right and left desired wheel speeds in RPM
        double angVRight = 75 * vRight / (2 * Math.PI * WHEEL_RADIUS); // (RPM)
        double angVLeft = 75 * vLeft / (2 * Math.PI * WHEEL_RADIUS);
        // send desired wheel speeds to Talon set to velocity control mode
        m_rightTalon.set(ControlMode.Velocity, angVRight);
        m_leftTalon.set(ControlMode.Velocity, -angVLeft);

        if (targetX >= 0) {
            System.out.println("Number of Contours: " + 2/*centerX.length*/ + " Goal Linear Velocity: " + goalLinearVelocity
                + " Goal Angular Velocity: " + goalAngularVelocity + " Timer: " + m_tim.get());
        } else {
            System.out.println("Number of Contours: " + "not 2" /*centerX.length*/ + " Goal Linear Velocity: " + goalLinearVelocity
                + " Goal Angular Velocity: " + goalAngularVelocity + " Timer: " + m_tim.get());
        }
    }


    @Override
    protected boolean isFinished() {
        return (m_tim.get() > 1 && Math.abs(m_leftTalon.getSelectedSensorVelocity(0)) < SLIPPING_VELOCITY
            && Math.abs(m_rightTalon.getSelectedSensorVelocity(0)) < SLIPPING_VELOCITY) || (m_tim.get() > TIMEOUT);
    }


    @Override
    protected void end() {
        System.out.println("DriveByVision Finished");
        m_tim.stop();
    }


}
