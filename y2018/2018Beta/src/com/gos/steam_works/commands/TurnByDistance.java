package com.gos.steam_works.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.gos.steam_works.Robot;
import com.gos.steam_works.RobotMap;
import com.gos.steam_works.subsystems.Shifters;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnByDistance extends Command {

    private double rotationsRight;
    private double rotationsLeft;

    private WPI_TalonSRX leftTalon = Robot.chassis.getLeftTalon();
    private WPI_TalonSRX rightTalon = Robot.chassis.getRightTalon();

    private double leftInitial;
    private double rightInitial;

    private Shifters.Speed speed;

    public TurnByDistance(double rightInches, double leftInches, Shifters.Speed speed) {
        rotationsRight = rightInches / (RobotMap.WHEEL_DIAMETER * Math.PI);
        rotationsLeft = leftInches / (RobotMap.WHEEL_DIAMETER * Math.PI);
        this.speed = speed;

        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

        Robot.shifters.shiftGear(speed);

        // Robot.chassis.setupFPID(leftTalon);
        // Robot.chassis.setupFPID(rightTalon);

        if (speed == Shifters.Speed.kLow) {
            leftTalon.config_kF(0, 0, 0);
            leftTalon.config_kP(0, 0.17, 0);
            leftTalon.config_kI(0, 0, 0);
            leftTalon.config_kD(0, 0.02, 0);

            rightTalon.config_kF(0, 0, 0);
            rightTalon.config_kP(0, 0.17, 0);
            rightTalon.config_kI(0, 0, 0);
            rightTalon.config_kD(0, 0.02, 0);
        } else if (speed == Shifters.Speed.kHigh) {
            leftTalon.config_kF(0, 0, 0);
            leftTalon.config_kP(0, 0.02, 0);
            leftTalon.config_kI(0, 0, 0);
            leftTalon.config_kD(0, 0.04, 0);

            rightTalon.config_kF(0, 0, 0);
            rightTalon.config_kP(0, 0.02, 0);
            rightTalon.config_kI(0, 0, 0);
            rightTalon.config_kD(0, 0.04, 0);
        }


        // leftTalon.setPosition(0.0);
        // rightTalon.setPosition(0.0);

        System.out.println("TurnByDistance Started " + rotationsRight + rotationsLeft);

        leftInitial = -leftTalon.getSelectedSensorPosition(0);
        rightInitial = rightTalon.getSelectedSensorPosition(0);

        leftTalon.set(ControlMode.Position, -(rotationsLeft + leftInitial));
        rightTalon.set(ControlMode.Position, rotationsRight + rightInitial);

        System.out.println("LeftInitial: " + leftInitial + " RightInitial: " + rightInitial);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        leftTalon.set(ControlMode.Position, -(rotationsLeft + leftInitial));
        rightTalon.set(ControlMode.Position, rotationsRight + rightInitial);

        SmartDashboard.putNumber("Drive Talon Left Goal", -rotationsLeft);
        SmartDashboard.putNumber("Drive Talon Left Position", leftTalon.getSelectedSensorPosition(0));
        //SmartDashboard.putNumber("Drive Talon Left Error", leftTalon.getError());

        //System.out.println("Left Goal " + (-(rotations + leftInitial)) + " Right Goal " + (rotations + rightInitial));
        //System.out.println("Left Position " + leftTalon.getPosition() + " Right Position " + rightTalon.getPosition());
        //System.out.println("Left Error " + ((-(rotations + leftInitial)) + leftTalon.getPosition()));
        //System.out.println("Right Error " + (((rotations + rightInitial)) - rightTalon.getPosition()));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.shifters.shiftGear(Shifters.Speed.kLow);
        System.out.println("TurnByDistance Finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
