package com.gos.power_up.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.RobotMap;
import com.gos.power_up.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.Command;

/**
 *
 */
public class DriveByMotionMagicAbsolute extends Command {

    private static final double DISTANCE_FINISH_THRESHOLD = 4000; //TODO tune (in encoder ticks)
    private static final double TURNING_FINISH_THRESHOLD = 1.5; //TODO tune (in degrees)

    private static final double DISTANCE_TIMER_THRESHOLD = 10000; //TODO tune (in encoder ticks)
    private static final double TURNING_TIMER_THRESHOLD = 8.0; //TODO tune (in degrees)

    private static final double TIMER_THRESHOLD = 0.5; //in seconds

    private final double m_encoderTicks;
    private final double m_targetHeading;

    private final boolean m_turning;

    private int m_timeoutCtr;
    private double m_time;

    private final Chassis m_chassis;
    private final WPI_TalonSRX m_leftTalon;
    private final WPI_TalonSRX m_rightTalon;

    public DriveByMotionMagicAbsolute(Chassis chassis, double inches, double absoluteDegrees, boolean isTurnMotion) {
        m_encoderTicks = RobotMap.CODES_PER_WHEEL_REV * (inches / (RobotMap.WHEEL_DIAMETER * Math.PI));
        m_targetHeading = absoluteDegrees;
        m_turning = isTurnMotion;
        m_chassis = chassis;
        m_leftTalon = m_chassis.getLeftTalon();
        m_rightTalon = m_chassis.getRightTalon();
        addRequirements(m_chassis);
        //System.out.println("DriveByMotionMagicAbsolute: constructed");
    }


    @Override
    public void initialize() {
        m_time = 0;
        m_chassis.setInverted(true);
        //System.out.println("DriveByMotionMagicAbsolute: motors inverted");

        m_chassis.configForMotionMagic();
        //System.out.println("DriveByMotionMagicAbsolute: configured for motion magic");

        m_chassis.zeroEncoder();
        //System.out.println("DriveByMotionMagicAbsolute: sensors zeroed");

        double inches = (m_encoderTicks / RobotMap.CODES_PER_WHEEL_REV) * (RobotMap.WHEEL_DIAMETER * Math.PI);
        System.out.println("DriveByMotionMagicAbsolute inches + heading + isTurnMotion: " + inches + " " + m_targetHeading + " " + m_turning);


        m_rightTalon.set(ControlMode.MotionMagic, 2 * m_encoderTicks, DemandType.AuxPID, 10 * m_targetHeading);
        m_leftTalon.follow(m_rightTalon, FollowerType.AuxOutput1);

        System.out.println("DriveByMotionMagicAbsolute: running...");

        m_timeoutCtr = 0;
    }


    @Override
    public void execute() {
        m_time += 0.02;
        if (!m_turning) { //if trying to drive straight
            double currentTicks = m_rightTalon.getSensorCollection().getQuadraturePosition();
            double error = Math.abs(m_encoderTicks - currentTicks);
            if (error < DISTANCE_TIMER_THRESHOLD) {
                m_timeoutCtr++;
            }
        } else { //if trying to turn to an angle
            double currentHeading = m_chassis.getYaw();
            double error = Math.abs(m_targetHeading - currentHeading);
            //System.out.println("DriveByMotionMagicAbsolute: turning error = " + error);
            if (error < TURNING_TIMER_THRESHOLD) {
                m_timeoutCtr++;
            }
        }
    }


    @Override
    public boolean isFinished() {

        if (m_timeoutCtr > (TIMER_THRESHOLD * 50)) {
            System.out.println("DriveByMotionMagicAbsolute: timeout reached");
            return true;
        } else if (!m_turning) { //if trying to drive straight
            double currentTicks = m_rightTalon.getSensorCollection().getQuadraturePosition();
            double error = Math.abs(m_encoderTicks - currentTicks);
            //System.out.println("DriveByMotionMagicAbsolute: distance error = " + error);
            if (error < DISTANCE_FINISH_THRESHOLD) {
                System.out.println("DriveByMotionMagicAbsolute: encoder ticks reached");
                return true;
            } else {
                return false;
            }
        } else { //if trying to turn to an angle
            double currentHeading = m_chassis.getYaw();
            double error = Math.abs(m_targetHeading - currentHeading);
            //System.out.println("DriveByMotionMagicAbsolute: turning error = " + error);
            if (error < TURNING_FINISH_THRESHOLD) {
                System.out.println("DriveByMotionMagicAbsolute: turning degrees reached");
                return true;
            } else {
                return false;
            }
        }

    }


    @Override
    public void end(boolean interrupted) {

        double currentTicks = m_rightTalon.getSensorCollection().getQuadraturePosition();
        double ticksError = Math.abs(m_encoderTicks - currentTicks);
        double inches = (ticksError / RobotMap.CODES_PER_WHEEL_REV) * (RobotMap.WHEEL_DIAMETER * Math.PI);
        double currentHeading = m_chassis.getYaw();
        double degreesError = Math.abs(m_targetHeading - currentHeading);

        System.out.println("DriveByMotionMagicAbsolute: ended. Error = " + inches / 2 + " inches, " + degreesError + " degrees, " + m_time + " seconds");
        m_chassis.stop();
        m_chassis.setInverted(false);
    }
}
