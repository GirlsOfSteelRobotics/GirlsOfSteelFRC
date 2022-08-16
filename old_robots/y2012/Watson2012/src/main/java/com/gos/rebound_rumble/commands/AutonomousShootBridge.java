package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.objects.Camera;
import com.gos.rebound_rumble.subsystems.Bridge;
import com.gos.rebound_rumble.subsystems.Chassis;
import com.gos.rebound_rumble.subsystems.Shooter;

public class AutonomousShootBridge extends SequentialCommandGroup {

    public AutonomousShootBridge(OI oi, Chassis chassis, Shooter shooter, Bridge bridge) {
        if (Camera.foundTarget()) {
            addCommands(new ShootUsingTable(shooter, oi, false));
        } else {
            addCommands(new Shoot(shooter, oi, Shooter.KEY_SPEED));
        }
        addCommands(new WaitCommand(4.0));
        if (Camera.foundTarget()) {
            double distance = Chassis.DISTANCE_BACKBOARD_TO_BRIDGE
                - Camera.getXDistance();
            addCommands(new MoveToSetPoint(chassis, distance).withTimeout(3.0));
        } else {
            addCommands(new MoveToSetPoint(chassis, Chassis.DISTANCE_KEY_TO_BRIDGE).withTimeout(3.0));
        }
        addCommands(new BridgeDown(bridge));
    }

}
