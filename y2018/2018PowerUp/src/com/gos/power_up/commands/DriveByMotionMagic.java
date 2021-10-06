package com.gos.power_up.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.RobotMap;
import com.gos.power_up.subsystems.Chassis;
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

    private final Chassis m_chassis;
    private final WPI_TalonSRX m_leftTalon;
    private final WPI_TalonSRX m_rightTalon;

    public DriveByMotionMagic(Chassis chassis, double inches, double degrees) {
        this(chassis, inches, degrees, true);
        //System.out.println("DriveByMotionMagic: constructed");
    }

    public DriveByMotionMagic(Chassis chassis, double inches, double degrees, boolean reset) {
        m_encoderTicks = RobotMap.CODES_PER_WHEEL_REV * (inches / (RobotMap.WHEEL_DIAMETER * Math.PI));
        m_targetHeading = degrees;
        m_resetPigeon = reset;
        m_chassis = chassis;
        m_leftTalon = m_chassis.getLeftTalon();
        m_rightTalon = m_chassis.getRightTalon();
        requires(m_chassis);
        //System.out.println("DriveByMotionMagic: constructed");
    }


    @Override
    protected void initialize() {
        m_time = 0;
        m_chassis.setInverted(true);
        //System.out.println("DriveByMotionMagic: motors inverted");

        m_chassis.configForMotionMagic();
        //System.out.println("DriveByMotionMagic: configured for motion magic");

        if (m_resetPigeon) {
            m_chassis.zeroSensors();
        } else {
            m_chassis.zeroEncoder();
        }
        //System.out.println("DriveByMotionMagic: sensors zeroed");

        double inches = (m_encoderTicks / RobotMap.CODES_PER_WHEEL_REV) * (RobotMap.WHEEL_DIAMETER * Math.PI);
        System.out.println("DriveByMotionMagic inches + heading + reset: " + inches + " " + m_targetHeading + " " + m_resetPigeon);

        m_rightTalon.set(ControlMode.MotionMagic, 2 * m_encoderTicks, DemandType.AuxPID, 10 * m_targetHeading);
        m_leftTalon.follow(m_rightTalon, FollowerType.AuxOutput1);

        System.out.println("DriveByMotionMagic: running...");

        m_timeoutCtr = 0;
    }


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
            double currentHeading = m_chassis.getYaw();
            double error = Math.abs(m_targetHeading - currentHeading);
            //System.out.println("DriveByMotionMagic: turning error = " + error);
            if (error < TURNING_TIMER_THRESHOLD) {
                m_timeoutCtr++;
            }
        }
    }


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
            double currentHeading = m_chassis.getYaw();
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


    @Override
    protected void end() {

        double currentTicks = m_rightTalon.getSensorCollection().getQuadraturePosition();
        double ticksError = Math.abs(m_encoderTicks - currentTicks);
        double inches = (ticksError / RobotMap.CODES_PER_WHEEL_REV) * (RobotMap.WHEEL_DIAMETER * Math.PI);
        double currentHeading = m_chassis.getYaw();
        double degreesError = Math.abs(m_targetHeading - currentHeading);

        System.out.println("DriveByMotionMagic: ended. Error = " + inches / 2 + " inches, " + degreesError + " degrees, " + m_time + " seconds");
        m_chassis.stop();
        m_chassis.setInverted(false);
    }


    @Override
    protected void interrupted() {
        System.out.println("DriveByMotionMagic: interrupted");
        end();
    }
}
