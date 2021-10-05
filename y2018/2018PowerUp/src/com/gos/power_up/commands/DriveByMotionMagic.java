package com.gos.power_up.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.Robot;
import com.gos.power_up.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveByMotionMagic extends Command {

    private static final double DISTANCE_FINISH_THRESHOLD = 4000; //TODO tune (in encoder ticks)
    private static final double TURNING_FINISH_THRESHOLD = 1.5; //TODO tune (in degrees)

    private static final double DISTANCE_TIMER_THRESHOLD = 5000; //TODO tune (in encoder ticks)
    private static final double TURNING_TIMER_THRESHOLD = 8.0; //TODO tune (in degrees)

    private static final double TIMER_THRESHOLD = 0.5; //in seconds

    private final double m_encoderTicks; //in sensor units
    private final double m_targetHeading; //in degrees
    private final boolean m_resetPigeon;

    private int m_timeoutCtr;
    private double m_time;

    private final WPI_TalonSRX m_leftTalon = Robot.m_chassis.getLeftTalon();
    private final WPI_TalonSRX m_rightTalon = Robot.m_chassis.getRightTalon();

    public DriveByMotionMagic(double inches, double degrees) {
        m_encoderTicks = RobotMap.CODES_PER_WHEEL_REV * (inches / (RobotMap.WHEEL_DIAMETER * Math.PI));
        m_targetHeading = degrees;
        m_resetPigeon = true;
        requires(Robot.m_chassis);
        //System.out.println("DriveByMotionMagic: constructed");
    }

    public DriveByMotionMagic(double inches, double degrees, boolean reset) {
        m_encoderTicks = RobotMap.CODES_PER_WHEEL_REV * (inches / (RobotMap.WHEEL_DIAMETER * Math.PI));
        m_targetHeading = degrees;
        m_resetPigeon = reset;
        requires(Robot.m_chassis);
        //System.out.println("DriveByMotionMagic: constructed");
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_time = 0;
        Robot.m_chassis.setInverted(true);
        //System.out.println("DriveByMotionMagic: motors inverted");

        Robot.m_chassis.configForMotionMagic();
        //System.out.println("DriveByMotionMagic: configured for motion magic");

        if (m_resetPigeon) {
            Robot.m_chassis.zeroSensors();
        } else {
            Robot.m_chassis.zeroEncoder();
        }
        //System.out.println("DriveByMotionMagic: sensors zeroed");

        double inches = (m_encoderTicks / RobotMap.CODES_PER_WHEEL_REV) * (RobotMap.WHEEL_DIAMETER * Math.PI);
        System.out.println("DriveByMotionMagic inches + heading + reset: " + inches + " " + m_targetHeading + " " + m_resetPigeon);

        m_rightTalon.set(ControlMode.MotionMagic, 2 * m_encoderTicks, DemandType.AuxPID, 10 * m_targetHeading);
        m_leftTalon.follow(m_rightTalon, FollowerType.AuxOutput1);

        System.out.println("DriveByMotionMagic: running...");

        m_timeoutCtr = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        m_time += 0.02;
        if (!m_resetPigeon || m_targetHeading == 0) { //if trying to drive straight

            double currentTicks = m_rightTalon.getSensorCollection().getQuadraturePosition();
            double error = Math.abs(m_encoderTicks - currentTicks);
            if (error < DISTANCE_TIMER_THRESHOLD) {
                m_timeoutCtr++;
            }
        } else { //if trying to turn to an angle
            double currentHeading = Robot.m_chassis.getYaw();
            double error = Math.abs(m_targetHeading - currentHeading);
            //System.out.println("DriveByMotionMagic: turning error = " + error);
            if (error < TURNING_TIMER_THRESHOLD) {
                m_timeoutCtr++;
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {

        if (m_timeoutCtr > (TIMER_THRESHOLD * 50)) {
            System.out.println("DriveByMotionMagic: timeout reached");
            return true;
        } else if (!m_resetPigeon || m_targetHeading == 0) { //if trying to drive straight
            double currentTicks = m_rightTalon.getSensorCollection().getQuadraturePosition();
            double error = Math.abs(m_encoderTicks - currentTicks);
            //System.out.println("DriveByMotionMagic: distance error = " + error);
            if (error < DISTANCE_FINISH_THRESHOLD) {
                System.out.println("DriveByMotionMagic: encoder ticks reached");
                return true;
            } else {
                return false;
            }
        } else { //if trying to turn to an angle
            double currentHeading = Robot.m_chassis.getYaw();
            double error = Math.abs(m_targetHeading - currentHeading);
            //System.out.println("DriveByMotionMagic: turning error = " + error);
            if (error < TURNING_FINISH_THRESHOLD) {
                System.out.println("DriveByMotionMagic: turning degrees reached");
                return true;
            } else {
                return false;
            }
        }

    }

    // Called once after isFinished returns true
    @Override
    protected void end() {

        double currentTicks = m_rightTalon.getSensorCollection().getQuadraturePosition();
        double ticksError = Math.abs(m_encoderTicks - currentTicks);
        double inches = (ticksError / RobotMap.CODES_PER_WHEEL_REV) * (RobotMap.WHEEL_DIAMETER * Math.PI);
        double currentHeading = Robot.m_chassis.getYaw();
        double degreesError = Math.abs(m_targetHeading - currentHeading);

        System.out.println("DriveByMotionMagic: ended. Error = " + inches / 2 + " inches, " + degreesError + " degrees, " + m_time + " seconds");
        Robot.m_chassis.stop();
        Robot.m_chassis.setInverted(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        System.out.println("DriveByMotionMagic: interrupted");
        end();
    }
}
