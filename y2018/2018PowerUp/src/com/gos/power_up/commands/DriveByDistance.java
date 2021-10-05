package com.gos.power_up.commands;


import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.power_up.Robot;
import com.gos.power_up.RobotMap;
import com.gos.power_up.subsystems.Shifters;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveByDistance extends Command {

    private final double encoderTicks; //in sensor units
    private int tim;

    private final WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
    private final WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();

    private boolean leftGood;
    private boolean rightGood;

    private final Shifters.Speed speed;

    private static final int ERROR_THRESHOLD = 1000;
    private static final int BIG_ERROR_THRESHOLD = 5000;

    public DriveByDistance(double inches, Shifters.Speed speed) {
        double rotations = inches / (RobotMap.WHEEL_DIAMETER * Math.PI);
        encoderTicks = RobotMap.CODES_PER_WHEEL_REV * rotations;
        this.speed = speed;

        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        ErrorCode err;
        Robot.shifters.shiftGear(speed);

        Robot.chassis.setPositionPIDSlot();

        err = leftTalon.setSelectedSensorPosition(0, 0, 20);
        System.out.printf("Error code on left: %s\n", err);
        err = rightTalon.setSelectedSensorPosition(0, 0, 20);
        System.out.printf("Error code on right: %s\n", err);


        //Robot.chassis.setupFPID(leftTalon);
        //Robot.chassis.setupFPID(rightTalon);


        leftTalon.set(ControlMode.Position, encoderTicks);
        rightTalon.set(ControlMode.Position, -encoderTicks);//!!!

        tim = 0;
        System.out.println("Drive by Distance Started " + encoderTicks);

        leftGood = false;
        rightGood = false;

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        if ((Math.abs(leftTalon.getSelectedSensorPosition(0) - encoderTicks) < BIG_ERROR_THRESHOLD)
            && (Math.abs(rightTalon.getSelectedSensorPosition(0) + encoderTicks) < BIG_ERROR_THRESHOLD)) {
            tim++;
        }

        //tim++;

        leftTalon.set(ControlMode.Position, encoderTicks);
        rightTalon.set(ControlMode.Position, -encoderTicks);

        SmartDashboard.putNumber("Drive Talon Left Goal", encoderTicks);
        SmartDashboard.putNumber("Drive Talon Left Position", leftTalon.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Drive Talon Left Error", leftTalon.getClosedLoopError(0));

        //System.out.println("Left Error: " + (leftTalon.getSelectedSensorPosition(0) - encoderTicks));
        //System.out.println("Right Error: " + (rightTalon.getSelectedSensorPosition(0) + encoderTicks));
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        //return false;

        if (Math.abs(leftTalon.getSelectedSensorPosition(0) - encoderTicks) < ERROR_THRESHOLD) {
            leftGood = true;
        }
        if (Math.abs(rightTalon.getSelectedSensorPosition(0) + encoderTicks) < ERROR_THRESHOLD) {
            rightGood = true;
        }


        return (tim > 30 || (leftGood && rightGood));

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

    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.println("DriveByDistance Finished");
        Robot.chassis.stop();

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
