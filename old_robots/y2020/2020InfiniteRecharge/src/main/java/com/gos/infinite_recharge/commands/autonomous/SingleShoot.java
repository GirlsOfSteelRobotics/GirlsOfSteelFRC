package com.gos.infinite_recharge.commands.autonomous;

import com.gos.infinite_recharge.commands.ConveyorWhileHeld;
import com.gos.infinite_recharge.commands.RunShooterRPM;
import com.gos.infinite_recharge.commands.StopShooter;
import com.gos.infinite_recharge.subsystems.Shooter;
import com.gos.infinite_recharge.subsystems.ShooterConveyor;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SingleShoot extends SequentialCommandGroup {

    public SingleShoot(Shooter shooter, ShooterConveyor shooterConveyor, double rpm) {

        addCommands(
            new RunShooterRPM(shooter, rpm),
            new ConveyorWhileHeld(shooterConveyor, true).until(() -> {

                System.out.println("Part One: " + shooterConveyor.getTop());
                return shooterConveyor.getTop();
            }),
            new ConveyorWhileHeld(shooterConveyor, true).until(() -> {
                System.out.println("Part Two: " + shooterConveyor.getTop());
                return !shooterConveyor.getTop();
            }),
            new StopShooter(shooter));
    }
}
