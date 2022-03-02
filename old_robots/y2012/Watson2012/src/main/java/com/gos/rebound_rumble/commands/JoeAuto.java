package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.subsystems.Bridge;
import com.gos.rebound_rumble.subsystems.Chassis;
import com.gos.rebound_rumble.subsystems.Collector;
import com.gos.rebound_rumble.subsystems.Shooter;
import com.gos.rebound_rumble.subsystems.Turret;

public class JoeAuto extends SequentialCommandGroup {

    @SuppressWarnings({"PMD.UnusedFormalParameter", "PMD.ExcessiveParameterList"})
    public JoeAuto(OI oi, Chassis chassis, Collector collector, Shooter shooter, Turret turret, Bridge bridge, boolean autoTrack, boolean autoShoot,
                   boolean shootFromKey, boolean moveToBridge, boolean getXDistanceCamera,
                   double xDistance,
                   double yDistance, boolean bridgeCollect,
                   boolean autoShootFromBridge, boolean goBackToKey,
                   boolean shootFromKeyAfterBridge) {

        addParallel(new Collect(collector));
        addCommands(new PrintCommand("reached"));

        if (autoTrack) {
            addParallel(new TurretTrackTarget(turret, oi.getOperatorJoystick()));
        }

        if (autoShoot) {
            addCommands(new ShootUsingTable(shooter, oi, false), 10);
        } else if (shootFromKey) {
            addCommands(new Shoot(shooter, oi, Shooter.KEY_SPEED), 10);
            addCommands(new PrintCommand("Shoot From Key"));
        }

        if (moveToBridge) {
            addCommands(new MoveToSetPoint(chassis, yDistance));
            addCommands(new PrintCommand("Move to Bridge"));

            if (bridgeCollect) {
                addCommands(new BridgeDown(bridge), 3);
                addCommands(new BridgeUp(bridge));
                addCommands(new MoveToSetPoint(chassis, -0.5), 3);
            }

            if (autoShootFromBridge) {
                addCommands(new Shoot(shooter, oi, Shooter.BRIDGE_SPEED), 10);
            }

            if (goBackToKey) {
                addCommands(new MoveToSetPoint(chassis, -yDistance));
                addCommands(new PrintCommand("Back to Key"));
                if (shootFromKeyAfterBridge) {
                    addCommands(new Shoot(shooter, oi, Shooter.KEY_SPEED));
                    addCommands(new PrintCommand("Shoot after back to key"));
                }
            }
        }
    }
}
