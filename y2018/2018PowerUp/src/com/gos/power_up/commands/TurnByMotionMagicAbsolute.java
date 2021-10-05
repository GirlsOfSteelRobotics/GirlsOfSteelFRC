package com.gos.power_up.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.Robot;
import com.gos.power_up.subsystems.Shifters;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnByMotionMagicAbsolute extends Command {
    private static final double TURNING_FINISH_THRESHOLD = 7.0; // TODO tune (in degrees)

    private final double m_targetHeading; // in degrees

    private final WPI_TalonSRX m_leftTalon = Robot.m_chassis.getLeftTalon();
    private final WPI_TalonSRX m_rightTalon = Robot.m_chassis.getRightTalon();

    public TurnByMotionMagicAbsolute(double degrees) {
        m_targetHeading = degrees;
        requires(Robot.m_chassis);
        // System.out.println("TurnByMotionMagic: constructed");
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.m_shifters.shiftGear(Shifters.Speed.kLow);
        Robot.m_chassis.setInverted(false);

        Robot.m_chassis.configForTurnByMotionMagic();
        // System.out.println("TurnByMotionMagic: configured for motion magic");

        System.out.println("TurnByMotionMagicAbsolute: heading: " + m_targetHeading);

        m_rightTalon.set(ControlMode.MotionMagic, -10 * m_targetHeading);
        m_leftTalon.follow(m_rightTalon);

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {

        double currentHeading = Robot.m_chassis.getYaw();
        double error = Math.abs(m_targetHeading - currentHeading);
        // System.out.println("DriveByMotionMagic: turning error = " + error);
        if (error < TURNING_FINISH_THRESHOLD) {
            System.out.println("TurnByMotionMagicAbsolute: turning degrees reached");
            return true;
        } else {
            return false;
        }

    }

    // Called once after isFinished returns true
    @Override
    protected void end() {

        double currentHeading = Robot.m_chassis.getYaw();
        double degreesError = m_targetHeading - currentHeading;

        System.out.println("TurnByMotionMagicAbsolute: ended. Error = " + degreesError + " degrees");
        Robot.m_chassis.stop();
        Robot.m_shifters.shiftGear(Shifters.Speed.kHigh);
        Robot.m_chassis.setInverted(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        System.out.println("TurnByMotionMagicAbsolute: interrupted");
        end();
    }
}
