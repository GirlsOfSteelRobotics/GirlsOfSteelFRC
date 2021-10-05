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

    private final double targetHeading; // in degrees

    private final WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
    private final WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();

    private final static double TURNING_FINISH_THRESHOLD = 7.0; // TODO tune (in degrees)

    public TurnByMotionMagicAbsolute(double degrees) {
        targetHeading = degrees;
        requires(Robot.chassis);
        // System.out.println("TurnByMotionMagic: constructed");
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.shifters.shiftGear(Shifters.Speed.kLow);
        Robot.chassis.setInverted(false);

        Robot.chassis.configForTurnByMotionMagic();
        // System.out.println("TurnByMotionMagic: configured for motion magic");

        System.out.println("TurnByMotionMagicAbsolute: heading: " + targetHeading);

        rightTalon.set(ControlMode.MotionMagic, -10 * targetHeading);
        leftTalon.follow(rightTalon);

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {

        double currentHeading = Robot.chassis.getYaw();
        double error = Math.abs(targetHeading - currentHeading);
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

        double currentHeading = Robot.chassis.getYaw();
        double degreesError = targetHeading - currentHeading;

        System.out.println("TurnByMotionMagicAbsolute: ended. Error = " + degreesError + " degrees");
        Robot.chassis.stop();
        Robot.shifters.shiftGear(Shifters.Speed.kHigh);
        Robot.chassis.setInverted(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        System.out.println("TurnByMotionMagicAbsolute: interrupted");
        end();
    }
}
