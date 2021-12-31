package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.subsystems.Shooter;

public class AutonomousKey extends CommandGroup {

    public AutonomousKey(Shooter shooter, OI oi) {
        addSequential(new Shoot(shooter, oi, Shooter.KEY_SPEED));
    }

}
