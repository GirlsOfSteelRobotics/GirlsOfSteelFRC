/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;

public class AutomatedConveyorIntake extends SequentialCommandGroup {

  private final ShooterIntake m_shooterIntake; 
  private final ShooterConveyor m_shooterConveyor; 

  /**
   * Creates a new AutomatedConveyorIntake.
   */
  public AutomatedConveyorIntake(ShooterIntake shooterIntake, ShooterConveyor shooterConveyor) {

    m_shooterIntake = shooterIntake; 
    m_shooterConveyor = shooterConveyor; 

    
    IntakeCells intakeCell = new IntakeCells(m_shooterIntake, true);
    Conveyor runConveyor = new Conveyor(shooterConveyor, true); 

    addCommands(intakeCell.withInterrupt(m_shooterConveyor::getHandoff));
    addCommands(runConveyor.withInterrupt(m_shooterConveyor::getSecondary)); 
  
    // Use addRequirements() here to declare subsystem dependencies.
  }
}
