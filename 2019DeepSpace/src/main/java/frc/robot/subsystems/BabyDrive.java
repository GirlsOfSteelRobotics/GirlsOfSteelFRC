package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Command;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.DriveByJoystick;


public class BabyDrive extends Command 
{
    
    private WPI_TalonSRX babyDriveTalon;

    public BabyDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        babyDriveTalon = new WPI_TalonSRX(RobotMap.BABY_DRIVE_TALON);

    }

    public void babyDriveForward()
    {
        babyDriveTalon = 
    }


    // Called just before this Command runs the first time
    protected void initialize() {

        // V per sec; 12 = zero to full speed in 1 second
        /*leftTalon.setVoltageRampRate(24.0);
        rightTalon.setVoltageRampRate(24.0);*/
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.chassis.arcadeDrive();
        SmartDashboard.putNumber("Drive by Joystick Y: ", Robot.oi.getDrivingJoystickY());
        SmartDashboard.putNumber("Drive by Joystick X: ", Robot.oi.getDrivingJoystickX());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }
    
    // Called once after isFinished returns true
    protected void end() {
        Robot.chassis.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
   
}