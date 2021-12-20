package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import girlsofsteel.OI;
import girlsofsteel.objects.Camera;
import girlsofsteel.subsystems.Bridge;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Shooter;

public class AutonomousCommandGroup extends CommandGroup {

    @SuppressWarnings({"PMD.ExcessiveParameterList", "PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
    public AutonomousCommandGroup(OI oi, Shooter shooter, Chassis chassis, Bridge bridge, boolean shoot, boolean moveToBridge,
                                  double yDistance,
                                  boolean shootFromBridge, boolean goBackToKey,
                                  boolean shootFromKeyAfterBridge) {
        requires(shooter);
        requires(chassis);
        requires(bridge);

//        addParallel(new Collect());
        addSequential(new PrintCommand("reached"));

        if (Camera.isConnected() && Camera.getXDistance() != 0) {
            //If we are ever going to use this we shouldn't be using this method. Use a command!
            yDistance = Chassis.DISTANCE_BACKBOARD_TO_BRIDGE // NOPMD
                    - Camera.getXDistance();
            //distance from backboard to bridge = half the field
            //(with bridge/2 + space between robot and bridge to push bridge
            //down)
        }
        if (shoot) {
            if (Camera.isConnected() && Camera.getXDistance() != 0) {
                addSequential(new ShootUsingTable(shooter, oi,  false), 6.0);
                addSequential(new PrintCommand("Shoot With Table"));
            } else {
                addSequential(new Shoot(shooter, oi, Shooter.KEY_SPEED), 6.0);
                addSequential(new PrintCommand("Shoot two from key"));
            }
        }
        if (moveToBridge) {
            if(Camera.isConnected() && Camera.getXDistance() != 0){
                //if the camera has found the target at least once
                yDistance = Chassis.DISTANCE_BACKBOARD_TO_BRIDGE
                        - Camera.getXDistance();
            }
            addSequential(new MoveToSetPoint(chassis, yDistance), 2.0);
            addSequential(new PrintCommand("Move To Bridge"));
            addSequential(new AutoBridgeDown(bridge),2.0);
            addSequential(new MoveToSetPoint(chassis, -0.5), 3.0);
            addSequential(new BridgeUp(bridge));
            addSequential(new PrintCommand("Bridge Collected"));
            if (shootFromBridge) {
                if (Camera.isConnected() && Camera.getXDistance() != 0) {
                    addSequential(new ShootUsingTable(shooter, oi, false), 2.5);
                    addSequential(new PrintCommand("Shoot With Table"));
                } else {
                    addSequential(new Shoot(shooter, oi, Shooter.BRIDGE_SPEED), 2.5);
                }
                addSequential(new PrintCommand("Shoot from Bridge"));
            }
            if (goBackToKey) {
                addSequential(new MoveToSetPoint(chassis, -yDistance));
                addSequential(new PrintCommand("Back to Key"));
                if (shootFromKeyAfterBridge) {
                    addSequential(new Shoot(shooter, oi, Shooter.KEY_SPEED), 5);
                    addSequential(new PrintCommand("Shoot From Key Again"));
                }
            }
        }
    }
}
