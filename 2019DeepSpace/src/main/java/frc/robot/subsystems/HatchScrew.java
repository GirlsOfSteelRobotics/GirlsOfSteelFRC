/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.commands.ClimberHold;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * Add your docs here.
 */

// commented out screwback for testing purposes
public class HatchScrew extends Subsystem {
  // DigitalInput limitSwitch = new DigitalInput(1);
  // SpeedController armMotor = new Victor(1);
  // Counter counter = new Counter(limitSwitch);
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private WPI_TalonSRX hatchScrew;

  public double goalPosition;

  private static final int GOAL_OPEN_POSITION = 500; //these values are so so wrong
  private static final int GOAL_CLOSED_POSITION = 0;
  public static final double HATCH_INCREMENT = 1000;

  public HatchScrew() {
    hatchScrew = new WPI_TalonSRX(RobotMap.HATCH_SCREW_TALON);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new ClimberHold());
  }

  public int getPosition(){
    return hatchScrew.getSelectedSensorPosition(0);
  }

  public void setOpenPosition() {
    hatchScrew.set(ControlMode.Position, goalPosition);
  }

  public void setClosedPosition(double pos) {
    goalPosition = pos;
    hatchScrew.set(ControlMode.Position, goalPosition);
  }






}