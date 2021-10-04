/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.gos.deep_space.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import com.gos.deep_space.RobotMap;

public class Hatch extends Subsystem {

  private WPI_TalonSRX hatchCollector;

  private static final double SLOW_COLLECTOR_SPEED = 0.25;
  private static final double COLLECTOR_INTAKE_SPEED = 0.4;
  private static final double COLLECTOR_RELEASE_SPEED = 0.4;

  public Hatch() {
    hatchCollector = new WPI_TalonSRX(RobotMap.HATCH_TALON);
    hatchCollector.setInverted(true);
    addChild("Collector", hatchCollector);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public void stop() {
    hatchCollector.stopMotor();
  }

  //switched all to opposite sign of what they where bc that's correct on comp bot

  public void collect() {
    hatchCollector.set(-COLLECTOR_INTAKE_SPEED);
  }

  public void release() {
    hatchCollector.set(COLLECTOR_RELEASE_SPEED);
  }

  public void slowCollect() {
    hatchCollector.set(-SLOW_COLLECTOR_SPEED);
  }
}
