package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.objects.Camera;
import com.gos.rebound_rumble.subsystems.Shooter;

public class AutonomousCameraKey extends ParallelCommandGroup {

    public AutonomousCameraKey(OI oi, Shooter shooter) {
        if (Camera.isConnected()) {
            addCommands(new ShootUsingTable(shooter, oi, false));
        } else {
            addCommands(new Shoot(shooter, oi, Shooter.KEY_SPEED));
        }
    }

}
