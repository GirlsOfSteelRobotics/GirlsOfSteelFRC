package com.gos.power_up.commands;


import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.RobotMap;
import com.gos.power_up.subsystems.Chassis;
import com.gos.power_up.subsystems.Shifters;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveByDistance extends Command {
    private static final int ERROR_THRESHOLD = 1000;
    private static final int BIG_ERROR_THRESHOLD = 5000;

    private final double m_encoderTicks; //in sensor units
    private int m_tim;

    private final Chassis m_chassis;
    private final Shifters m_shifters;
    private final WPI_TalonSRX m_leftTalon;
    private final WPI_TalonSRX m_rightTalon;

    private boolean m_leftGood;
    private boolean m_rightGood;

    private final Shifters.Speed m_speed;


    public DriveByDistance(Chassis chassis, Shifters shifters, double inches, Shifters.Speed speed) {
        double rotations = inches / (RobotMap.WHEEL_DIAMETER * Math.PI);
        m_encoderTicks = RobotMap.CODES_PER_WHEEL_REV * rotations;
        this.m_speed = speed;

        m_chassis = chassis;
        m_shifters = shifters;
        m_leftTalon = m_chassis.getLeftTalon();
        m_rightTalon = m_chassis.getRightTalon();
        requires(m_chassis);
    }


    @Override
    protected void initialize() {
        ErrorCode err;
        m_shifters.shiftGear(m_speed);

        m_chassis.setPositionPIDSlot();

        err = m_leftTalon.setSelectedSensorPosition(0, 0, 20);
        System.out.printf("Error code on left: %s\n", err);
        err = m_rightTalon.setSelectedSensorPosition(0, 0, 20);
        System.out.printf("Error code on right: %s\n", err);


        //Robot.chassis.setupFPID(leftTalon);
        //Robot.chassis.setupFPID(rightTalon);


        m_leftTalon.set(ControlMode.Position, m_encoderTicks);
        m_rightTalon.set(ControlMode.Position, -m_encoderTicks); //!!!

        m_tim = 0;
        System.out.println("Drive by Distance Started " + m_encoderTicks);

        m_leftGood = false;
        m_rightGood = false;

    }


    @Override
    protected void execute() {

        if ((Math.abs(m_leftTalon.getSelectedSensorPosition(0) - m_encoderTicks) < BIG_ERROR_THRESHOLD)
            && (Math.abs(m_rightTalon.getSelectedSensorPosition(0) + m_encoderTicks) < BIG_ERROR_THRESHOLD)) {
            m_tim++;
        }

        //tim++;

        m_leftTalon.set(ControlMode.Position, m_encoderTicks);
        m_rightTalon.set(ControlMode.Position, -m_encoderTicks);

        SmartDashboard.putNumber("Drive Talon Left Goal", m_encoderTicks);
        SmartDashboard.putNumber("Drive Talon Left Position", m_leftTalon.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Drive Talon Left Error", m_leftTalon.getClosedLoopError(0));

        //System.out.println("Left Error: " + (leftTalon.getSelectedSensorPosition(0) - encoderTicks));
        //System.out.println("Right Error: " + (rightTalon.getSelectedSensorPosition(0) + encoderTicks));
    }


    @Override
    protected boolean isFinished() {
        //return false;

        if (Math.abs(m_leftTalon.getSelectedSensorPosition(0) - m_encoderTicks) < ERROR_THRESHOLD) {
            m_leftGood = true;
        }
        if (Math.abs(m_rightTalon.getSelectedSensorPosition(0) + m_encoderTicks) < ERROR_THRESHOLD) {
            m_rightGood = true;
        }


        return m_tim > 30 || (m_leftGood && m_rightGood);

        /*
        if (encoderTicks > 0) {
            if (rightTalon.getSelectedSensorPosition(0) < -encoderTicks
                    && leftTalon.getSelectedSensorPosition(0) > encoderTicks)
            {
                System.out.println("Finish Case #1");
                return true;
            }
            else return false;
        } else if (encoderTicks < 0) {
            if ((rightTalon.getSelectedSensorPosition(0) > -encoderTicks)//!!!
                    && (leftTalon.getSelectedSensorPosition(0) < (encoderTicks)))
            {
                System.out.println("Finish Case #2");
                return true;
            }
            else return false;
        } else {
            System.out.println("Finish Case #3");
            return true;
        }
        */


    }


    @Override
    protected void end() {
        System.out.println("DriveByDistance Finished");
        m_chassis.stop();

    }


}
