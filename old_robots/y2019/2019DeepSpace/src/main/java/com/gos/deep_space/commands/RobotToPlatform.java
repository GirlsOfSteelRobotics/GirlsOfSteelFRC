/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// package frc.robot.commands;

// import edu.wpi.first.wpilibj.command.TimedCommand;
// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// public class RobotToPlatform extends SequentialCommandGroup {

//   private final double BABYDRIVE_FRONT_TO_PLATFORM = 117.3;
//   private final double BABYDRIVE_BACK_TO_PLATFORM = 62.25;
//   private final double ROBOT_ON_PLATFORM = 56.4;

//   public RobotToPlatform(int platform) {

//     if (platform == 3)
//       addCommands(new ClimberToThirdUp());
//     else
//       addCommands(new ClimberToSecondUp());

//     addCommands(new LidarDriveForward(BABYDRIVE_FRONT_TO_PLATFORM, false)); // make this into a constant
//     addCommands(new ClimberFrontToZero());
//     addCommands(new LidarDriveForward(BABYDRIVE_BACK_TO_PLATFORM, true));
//     addCommands(new ClimberBackToZero());
//     addCommands(new LidarDriveForward(ROBOT_ON_PLATFORM, true));

//     // addCommands(new LidarDriveForward(ROBOT_ON_PLATFORM));
//   }

// }
