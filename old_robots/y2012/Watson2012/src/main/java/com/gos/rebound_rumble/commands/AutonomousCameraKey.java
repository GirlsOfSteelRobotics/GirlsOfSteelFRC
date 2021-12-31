package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.objects.Camera;
import com.gos.rebound_rumble.subsystems.Shooter;

public class AutonomousCameraKey extends CommandGroup {

    public AutonomousCameraKey(OI oi, Shooter shooter) {
        if (Camera.isConnected()) {
            addParallel(new ShootUsingTable(shooter, oi, false));
        } else {
            addParallel(new Shoot(shooter, oi, Shooter.KEY_SPEED));
        }
    }

}
