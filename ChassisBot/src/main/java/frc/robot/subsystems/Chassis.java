/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.Constants;

public class Chassis extends SubsystemBase {

  private final WPI_TalonSRX rightA = new WPI_TalonSRX(Constants.DRIVE_RIGHT_A_CAN_ID);
  private final WPI_TalonSRX rightB = new WPI_TalonSRX(Constants.DRIVE_RIGHT_B_CAN_ID);
  private final WPI_TalonSRX leftA = new WPI_TalonSRX(Constants.DRIVE_LEFT_A_CAN_ID);
  private final WPI_TalonSRX leftB = new WPI_TalonSRX(Constants.DRIVE_LEFT_B_CAN_ID);
  
  public final DifferentialDrive drive = new DifferentialDrive(new SpeedControllerGroup(leftA, leftB),
      new SpeedControllerGroup(rightA, rightB));
}
