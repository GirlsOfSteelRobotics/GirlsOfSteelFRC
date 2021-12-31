package org.usfirst.frc.team3504.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3504.robot.subsystems.Camera;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;

/**
 *
 */
public class DriveByVision extends Command {


    private static final double MAX_ANGULAR_VELOCITY = 1.0; // TODO: adjust
    private static final double SLOW_LINEAR_VELOCITY = 22; // TODO: change (in/s)
    private static final double FAST_LINEAR_VELOCITY = 28; // TODO: change (in/s)
    private static final int TIMEOUT = 8;
    private static final int SLIPPING_VELOCITY = 850;
    // (rad/s) current
    // value works
    private static final int IMAGE_WIDTH = 320;
    private static final double IMAGE_CENTER = IMAGE_WIDTH / 2.0;

    // distance b/w wheels
    private static final double WHEEL_BASE = 24.25; // (in)

    // radius of wheel
    private static final int WHEEL_RADIUS = 2; // (in)

    private final Timer m_tim;
    private final Camera m_camera;
    private final Chassis m_chassis;

    // width of X or Y in pixels when the robot is at the lift
    // private static final double GOAL_WIDTH = 30; //TODO: test and change

    public DriveByVision(Chassis chassis, Camera camera) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        m_chassis = chassis;
        m_camera = camera;
        requires(m_chassis);
        m_tim = new Timer();
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {

        // not calling setupFPID because other PID values override
        m_chassis.setEncoderPositions(0, 0);

        // tuned by janet and ziya on 2/20, overrides PID set in chassis method
        m_chassis.setPid(0.235, 0, 0, 0.22);

        System.out.println("DriveByVision Initialized");

        m_tim.start();
    }

    // Called repeatedly when this Command is scheduled to run
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
        targetX = m_camera.getTargetX();
        height = m_camera.getHeight();
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
            goalLinearVelocity = FAST_LINEAR_VELOCITY;
        } else if (height < 0) {
            goalLinearVelocity = SLOW_LINEAR_VELOCITY;
        } else if (height >= 52.0) {
            goalLinearVelocity = SLOW_LINEAR_VELOCITY;
        } else {
            goalLinearVelocity = FAST_LINEAR_VELOCITY;
        }


        // right and left desired wheel speeds in inches per second
        double vRight = goalLinearVelocity - (WHEEL_BASE * goalAngularVelocity) / 2; // (in/s)
        double vLeft = goalLinearVelocity + (WHEEL_BASE * goalAngularVelocity) / 2;
        // right and left desired wheel speeds in RPM
        double angVRight = 75 * vRight / (2 * Math.PI * WHEEL_RADIUS); // (RPM)
        double angVLeft = 75 * vLeft / (2 * Math.PI * WHEEL_RADIUS);
        // send desired wheel speeds to Talon set to velocity control mode
        m_chassis.setVelocityGoal(angVLeft, -angVRight);

        if (targetX >= 0) {
            System.out.println("Number of Contours: " + 2/*centerX.length*/ + " Goal Linear Velocity: " + goalLinearVelocity
                + " Goal Angular Velocity: " + goalAngularVelocity + " Timer: " + m_tim.get());
        } else {
            System.out.println("Number of Contours: " + "not 2" /*centerX.length*/ + " Goal Linear Velocity: " + goalLinearVelocity
                + " Goal Angular Velocity: " + goalAngularVelocity + " Timer: " + m_tim.get());
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {

        return ((m_tim.get() > 1 && Math.abs(m_chassis.getLeftVelocity()) < SLIPPING_VELOCITY
            && Math.abs(m_chassis.getRightVelocity()) < SLIPPING_VELOCITY) || (m_tim.get() > TIMEOUT));
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.println("DriveByVision Finished");
        m_tim.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
