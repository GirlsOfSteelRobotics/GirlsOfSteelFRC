package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.subsystems.Shooter;

public class AutonomousKey extends SequentialCommandGroup {

    public AutonomousKey(Shooter shooter, OI oi) {
        addCommands(new Shoot(shooter, oi, Shooter.KEY_SPEED));
    }

}
