/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.networktables.*;

public class OuterShoot extends CommandBase {
  /**
   * Creates a new OuterShoot.
   */
  double KpAim = -0.1f;
  double KpDistance = -0.1f;
  double min_aim_command = 0.05f;
  double left_command = 0.0;
  double right_command = 0.0;
  boolean m_LimelightHasValidTarget = false;
  double m_LimelightDriveCommand = 0.0;
  double m_LimelightSteerCommand = 0.0;

  public OuterShoot() {
    addRequirements(Robot.chassis);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Update_Limelight_Tracking();
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public void Update_Limelight_Tracking()
  {
    // These numbers must be tuned for your Robot!  Be careful!
    // final double STEER_K = 0.03;                    // how hard to turn toward the target
    // final double DRIVE_K = 0.26;                    // how hard to drive fwd toward the target
    //final double DESIRED_TARGET_AREA = 13.0;        // Area of the target when the robot reaches the wall
    final double MAX_DRIVE = 0.7;                   // Simple speed limit so we don't drive too fast

    double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
    //double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);

    double heading_error = -tx;
    double distance_error = -ty;
    double steering_adjust = 0.0;

        if (tv < 1.0)
        {
          m_LimelightHasValidTarget = false;
          m_LimelightDriveCommand = 0.0;
          m_LimelightSteerCommand = 0.0;
          return;
        }

        m_LimelightHasValidTarget = true;

        if (tx > 1.0)
        {
          steering_adjust = KpAim*heading_error - min_aim_command;
        }
        else if (tx < 1.0)
        {
          steering_adjust = KpAim*heading_error + min_aim_command;
        }
        double distance_adjust = KpDistance * distance_error;

        m_LimelightSteerCommand = steering_adjust;

        // try to drive forward until the target area reaches our desired area
        double drive_cmd = distance_adjust;

        // don't let the robot drive too fast into the goal
        if (drive_cmd > MAX_DRIVE)
        {
          drive_cmd = MAX_DRIVE;
        }
        m_LimelightDriveCommand = drive_cmd;
  }
}
