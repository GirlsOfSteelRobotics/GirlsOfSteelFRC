package frc.robot.commands;

import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Chassis;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class outershootalign2 extends CommandBase {

  Limelight limelight;
  Chassis chassis;

  public void OuterShootAlign(Chassis chassis, Limelight limelight) {
    this.limelight = limelight;
    this.chassis = chassis;
    addRequirements(limelight);
    addRequirements(chassis);

  }

  private void addRequirements(Limelight limelight2) {
}

private void addRequirements(Chassis chassis2) {
}

@Override
  public void initialize() {
  }
  @Override
  public void execute() {
    chassis.setSpeedAndSteer(limelight.getDriveCommand(), limelight.getSteerCommand());
  }
  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }


}
  

