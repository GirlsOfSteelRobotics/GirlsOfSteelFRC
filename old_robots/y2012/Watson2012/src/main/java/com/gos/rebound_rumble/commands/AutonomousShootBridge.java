package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.objects.Camera;
import com.gos.rebound_rumble.subsystems.Bridge;
import com.gos.rebound_rumble.subsystems.Chassis;
import com.gos.rebound_rumble.subsystems.Shooter;

public class AutonomousShootBridge extends CommandGroup {

    public AutonomousShootBridge(OI oi, Chassis chassis, Shooter shooter, Bridge bridge) {
        if (Camera.foundTarget()) {
            addParallel(new ShootUsingTable(shooter, oi, false));
        } else {
            addParallel(new Shoot(shooter, oi, Shooter.KEY_SPEED));
        }
        addSequential(new WaitCommand(4.0));
        if (Camera.foundTarget()) {
            double distance = Chassis.DISTANCE_BACKBOARD_TO_BRIDGE
                - Camera.getXDistance();
            addSequential(new MoveToSetPoint(chassis, distance), 3.0);
        } else {
            addSequential(new MoveToSetPoint(chassis, Chassis.DISTANCE_KEY_TO_BRIDGE), 3.0);
        }
        addSequential(new BridgeDown(bridge));
    }

}
