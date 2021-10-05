package com.gos.power_up.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnInPlace extends Command {

    private static final double kP = .005;
    private static final double kI = 0;
    private static final double kD = 0;

    private boolean m_targetReached;

    private final double m_headingTarget;
    private double m_errorLast;
    private double m_iError;

    private final WPI_TalonSRX m_leftTalon = Robot.m_chassis.getLeftTalon();
    private final WPI_TalonSRX m_rightTalon = Robot.m_chassis.getRightTalon();

    public TurnInPlace(double degrees) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.m_chassis);
        m_headingTarget = degrees;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        System.out.println("Trying to initialize");
        Robot.m_chassis.setInverted(false);
        Robot.m_chassis.zeroSensors();
        System.out.println("Turn in place initialized Heading = " + m_headingTarget);

    }


    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        double currentPos = Robot.m_chassis.getYaw();
        double error = m_headingTarget - currentPos;
        double dError = (error - m_errorLast) / .02;


        double tempError = m_iError + (error * .02);
        if (Math.abs(tempError * kI) < .5) {
            m_iError = tempError;
        }
        System.out.println("current position " + currentPos);

        m_leftTalon.set(ControlMode.PercentOutput, (kP * error) + (kD * dError) + (kI * m_iError));
        m_rightTalon.set(ControlMode.PercentOutput, (kP * error) + (kD * dError) + (kI * m_iError));

        if (error < 1 && dError < 10) {
            m_targetReached = true;
        }

        m_errorLast = error;

        //        if (headingTarget > 0) {
        //            leftTalon.set(ControlMode.Position, encoderTicks);
        //            rightTalon.set(ControlMode.Position, encoderTicks);
        //        } else {
        //            leftTalon.set(ControlMode.Position, -encoderTicks);
        //            rightTalon.set(ControlMode.Position, -encoderTicks);
        //        }
        //
        //        System.out.println("Left Error: " + (leftTalon.getSelectedSensorPosition(1) - encoderTicks));
        //        System.out.println("Right Error: " + (rightTalon.getSelectedSensorPosition(1) + encoderTicks));
        //
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return m_targetReached;
    }


    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.println("TurnInPlace Finished");
        Robot.m_chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        System.out.println("TurnInPlace Interrupted");
        end();
    }
}
