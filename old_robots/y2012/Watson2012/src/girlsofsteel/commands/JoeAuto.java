package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;

public class JoeAuto extends CommandGroup {

    public JoeAuto(boolean autoTrack, boolean autoShoot,
            boolean shootFromKey, boolean moveToBridge, boolean getXDistanceCamera,
            double xDistance,
            double yDistance, boolean bridgeCollect,
            boolean autoShootFromBridge, boolean goBackToKey,
            boolean shootFromKeyAfterBridge) {

        addParallel(new Collect());
        addSequential(new PrintCommand("reached"));

        if (autoTrack) {
            addParallel(new TurretTrackTarget());
        }

        if (autoShoot) {
            addSequential(new ShootUsingTable(false), 10);
        } else if (shootFromKey) {
            addSequential(new Shoot(CommandBase.shooter.KEY_SPEED), 10);
            addSequential(new PrintCommand("Shoot From Key"));
        }

        if (moveToBridge) {
            addSequential(new MoveToSetPoint(yDistance));
            addSequential(new PrintCommand("Move to Bridge"));

            if (bridgeCollect) {
                addSequential(new BridgeDown(), 3);
                addSequential(new BridgeUp());
                addSequential(new MoveToSetPoint(-0.5), 3);
            }

            if (autoShootFromBridge) {
                addSequential(new Shoot(CommandBase.shooter.BRIDGE_SPEED), 10);
            }

            if (goBackToKey) {
                addSequential(new MoveToSetPoint(-yDistance));
                addSequential(new PrintCommand("Back to Key"));
                if (shootFromKeyAfterBridge) {
                    addSequential(new Shoot(CommandBase.shooter.KEY_SPEED));
                    addSequential(new PrintCommand("Shoot after back to key"));
                }
            }
        }
    }
}
