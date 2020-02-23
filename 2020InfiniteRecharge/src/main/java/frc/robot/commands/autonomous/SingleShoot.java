package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Conveyor;
import frc.robot.commands.RunShooterRPM;
import frc.robot.commands.StopShooter;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterConveyor;

public class SingleShoot extends SequentialCommandGroup {

    public SingleShoot(Shooter shooter, ShooterConveyor shooterConveyor, double rpm) {

        addCommands(
            new RunShooterRPM(shooter, rpm), 
            new Conveyor(shooterConveyor, true).withInterrupt(() -> {

                System.out.println("Part One: " + shooterConveyor.getTop());
                return shooterConveyor.getTop();
            }), 
            new Conveyor(shooterConveyor, true).withInterrupt(() -> {
                System.out.println("Part Two: " + shooterConveyor.getTop());
                return !shooterConveyor.getTop();
            }), 
            new StopShooter(shooter));
    }

    
  public void end(boolean interrupted) {
    super.end(interrupted);
    System.out.println("Command finished");
  }
}
