package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import girlsofsteel.OI;
import girlsofsteel.subsystems.Bridge;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Collector;
import girlsofsteel.subsystems.Shooter;
import girlsofsteel.subsystems.Turret;

public class JoeAuto extends CommandGroup {

    @SuppressWarnings({"PMD.UnusedFormalParameter", "PMD.ExcessiveParameterList"})
    public JoeAuto(OI oi, Chassis chassis, Collector collector, Shooter shooter, Turret turret, Bridge bridge, boolean autoTrack, boolean autoShoot,
                   boolean shootFromKey, boolean moveToBridge, boolean getXDistanceCamera,
                   double xDistance,
                   double yDistance, boolean bridgeCollect,
                   boolean autoShootFromBridge, boolean goBackToKey,
                   boolean shootFromKeyAfterBridge) {

        addParallel(new Collect(collector));
        addSequential(new PrintCommand("reached"));

        if (autoTrack) {
            addParallel(new TurretTrackTarget(turret, oi.getOperatorJoystick()));
        }

        if (autoShoot) {
            addSequential(new ShootUsingTable(shooter, oi, false), 10);
        } else if (shootFromKey) {
            addSequential(new Shoot(shooter, oi, Shooter.KEY_SPEED), 10);
            addSequential(new PrintCommand("Shoot From Key"));
        }

        if (moveToBridge) {
            addSequential(new MoveToSetPoint(chassis, yDistance));
            addSequential(new PrintCommand("Move to Bridge"));

            if (bridgeCollect) {
                addSequential(new BridgeDown(bridge), 3);
                addSequential(new BridgeUp(bridge));
                addSequential(new MoveToSetPoint(chassis, -0.5), 3);
            }

            if (autoShootFromBridge) {
                addSequential(new Shoot(shooter, oi, Shooter.BRIDGE_SPEED), 10);
            }

            if (goBackToKey) {
                addSequential(new MoveToSetPoint(chassis, -yDistance));
                addSequential(new PrintCommand("Back to Key"));
                if (shootFromKeyAfterBridge) {
                    addSequential(new Shoot(shooter, oi, Shooter.KEY_SPEED));
                    addSequential(new PrintCommand("Shoot after back to key"));
                }
            }
        }
    }
}
