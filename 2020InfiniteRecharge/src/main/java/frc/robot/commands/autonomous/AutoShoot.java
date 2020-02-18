package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.Conveyor;
import frc.robot.commands.RunShooterRPM;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterConveyor;

public class AutoShoot extends ParallelCommandGroup {

    public AutoShoot(Shooter shooter, ShooterConveyor shooterConveyor, double rpm, double time) {

        super.addCommands(new RunShooterRPM(shooter, rpm).withTimeout(time));
        super.addCommands(new Conveyor(shooterConveyor, true).withTimeout(time));

    }
}
