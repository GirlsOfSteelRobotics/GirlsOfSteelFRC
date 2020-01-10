/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.SparkMax;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveByJoystick;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Chassis extends Subsystem {

  private CANSparkMax frontLeft;
  private CANSparkMax frontRight;
  private CANSparkMax rearLeft;
  private CANSparkMax rearRight;

  private MecanumDrive drive;

  public Chassis() {
   frontLeft = new CANSparkMax(2, MotorType.kBrushless);
   frontRight = new CANSparkMax(3, MotorType.kBrushless);
   rearLeft = new CANSparkMax(1, MotorType.kBrushless);
   rearRight = new CANSparkMax(4, MotorType.kBrushless);

   frontLeft.setIdleMode(IdleMode.kBrake);
   frontRight.setIdleMode(IdleMode.kBrake);
   rearLeft.setIdleMode(IdleMode.kBrake);
   rearRight.setIdleMode(IdleMode.kBrake);

   frontLeft.setInverted(false);
   frontRight.setInverted(false);
   rearLeft.setInverted(false);
   rearRight.setInverted(false);

   drive = new MecanumDrive(frontLeft, frontRight, rearLeft, rearRight);
  }
  

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveByJoystick());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void driveByJoystick(double ySpeed, double xSpeed, double zRotation){
    drive.driveCartesian(ySpeed, xSpeed, zRotation);
    SmartDashboard.putNumber("EncoderCPR", frontLeft.getEncoder().getCPR());
    SmartDashboard.putNumber("Velocity", frontLeft.getEncoder().getVelocity());
    SmartDashboard.putNumber("Position", frontLeft.getEncoder().getPosition());
  }

  public void setSpeed(double speed) {
    drive.drivePolar(speed, 0, 0);
  }

  public void stop() {
    drive.stopMotor();
  }
}
