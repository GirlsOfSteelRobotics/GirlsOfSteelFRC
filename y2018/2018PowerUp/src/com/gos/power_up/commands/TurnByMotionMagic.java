package com.gos.power_up.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnByMotionMagic extends Command {

    private final double targetHeading; // in degrees
    private final boolean resetPigeon;

    private final WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
    private final WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();

    private final static double TURNING_FINISH_THRESHOLD = 7.0; // TODO tune (in degrees)

    public TurnByMotionMagic(double degrees) {
        targetHeading = degrees;
        resetPigeon = true;
        requires(Robot.chassis);
        // System.out.println("TurnByMotionMagic: constructed");
    }

    public TurnByMotionMagic(double degrees, boolean reset) {
        targetHeading = degrees;
        resetPigeon = reset;
        requires(Robot.chassis);
        // System.out.println("TurnByMotionMagic: constructed");
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.chassis.setInverted(false);

        Robot.chassis.configForTurnByMotionMagic();
        // System.out.println("TurnByMotionMagic: configured for motion magic");

        if (resetPigeon) {
            Robot.chassis.zeroSensors();
        }

        System.out.println("TurnByMotionMagic: heading: " + targetHeading + " reset=" + resetPigeon);


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
            System.out.println("TurnByMotionMagic: turning degrees reached");
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

        System.out.println("TurnByMotionMagic: ended. Error = " + degreesError + " degrees");
        Robot.chassis.stop();
        Robot.chassis.setInverted(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        System.out.println("TurnByMotionMagic: interrupted");
        end();
    }
}
