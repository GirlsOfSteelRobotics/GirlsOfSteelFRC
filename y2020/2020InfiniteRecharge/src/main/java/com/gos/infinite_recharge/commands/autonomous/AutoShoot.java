package com.gos.infinite_recharge.commands.autonomous;

import com.gos.infinite_recharge.commands.ConveyorWhileHeld;
import com.gos.infinite_recharge.commands.RunShooterRPM;
import com.gos.infinite_recharge.commands.StopShooter;
import com.gos.infinite_recharge.subsystems.Shooter;
import com.gos.infinite_recharge.subsystems.ShooterConveyor;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoShoot extends SequentialCommandGroup {

    public AutoShoot(Shooter shooter, ShooterConveyor shooterConveyor, double rpm, double seconds) {

        super.addCommands(new RunShooterRPM(shooter, rpm));
        super.addCommands(new ConveyorWhileHeld(shooterConveyor, true).withTimeout(seconds));
        super.addCommands(new StopShooter(shooter));
    }
}
