package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Conveyor;
import frc.robot.commands.RunShooterRPM;
import frc.robot.commands.StopShooter;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterConveyor;

public class AutoShoot extends SequentialCommandGroup {

    public AutoShoot(Shooter shooter, ShooterConveyor shooterConveyor, double rpm, double seconds) {

        super.addCommands(new RunShooterRPM(shooter, rpm));
        super.addCommands(new Conveyor(shooterConveyor, true).withTimeout(seconds));
        super.addCommands(new StopShooter(shooter));
    }
}
