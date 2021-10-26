package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import girlsofsteel.objects.Camera;

public class AutonomousCommandGroup extends CommandGroup {

    boolean shoot;//at the start of autonomous
    boolean moveToBridge;//do you want to move towards the bridge?
    double yDistance;//only work when moveToBridge is true
    boolean shootFromBridge;//use camera to shoot from the position you are
    //in after moving to the bridge
    boolean goBackToKey;//move back to the key (starting place)
    boolean shootFromKeyAfterBridge;//after moving back to the key, you shoot

    public AutonomousCommandGroup(boolean shoot, boolean moveToBridge,
            double yDistance,
            boolean shootFromBridge, boolean goBackToKey,
            boolean shootFromKeyAfterBridge) {
        requires(CommandBase.shooter);
        requires(CommandBase.chassis);
        requires(CommandBase.bridge);
        this.shoot = shoot;
        this.moveToBridge = moveToBridge;
        this.yDistance = yDistance;
        this.shootFromBridge = shootFromBridge;
        this.goBackToKey = goBackToKey;
        this.shootFromKeyAfterBridge = shootFromKeyAfterBridge;

//        addParallel(new Collect());
        addSequential(new PrintCommand("reached"));

        if (Camera.isConnected() && Camera.getXDistance() != 0) {
            //If we are ever going to use this we shouldn't be using this method. Use a command!
            yDistance = CommandBase.chassis.DISTANCE_BACKBOARD_TO_BRIDGE
                    - Camera.getXDistance();
            //distance from backboard to bridge = half the field
            //(with bridge/2 + space between robot and bridge to push bridge
            //down)
        }
        if (shoot) {
            if (Camera.isConnected() && Camera.getXDistance() != 0) {
                addSequential(new ShootUsingTable(false), 6.0);
                addSequential(new PrintCommand("Shoot With Table"));
            } else {
                addSequential(new Shoot(CommandBase.shooter.KEY_SPEED), 6.0);
                addSequential(new PrintCommand("Shoot two from key"));
            }
        }
        if (moveToBridge) {
            if(Camera.isConnected() && Camera.getXDistance() != 0){
                //if the camera has found the target at least once
                yDistance = CommandBase.chassis.DISTANCE_BACKBOARD_TO_BRIDGE
                        - Camera.getXDistance();
            }
            addSequential(new MoveToSetPoint(yDistance), 2.0);
            addSequential(new PrintCommand("Move To Bridge"));
            addSequential(new AutoBridgeDown(),2.0);
            addSequential(new MoveToSetPoint(-0.5), 3.0);
            addSequential(new BridgeUp());
            addSequential(new PrintCommand("Bridge Collected"));
            if (shootFromBridge) {
                if (Camera.isConnected() && Camera.getXDistance() != 0) {
                    addSequential(new ShootUsingTable(false), 2.5);
                    addSequential(new PrintCommand("Shoot With Table"));
                } else {
                    addSequential(new Shoot(CommandBase.shooter.BRIDGE_SPEED), 2.5);
                }
                addSequential(new PrintCommand("Shoot from Bridge"));
            }
            if (goBackToKey) {
                addSequential(new MoveToSetPoint(-yDistance));
                addSequential(new PrintCommand("Back to Key"));
                if (shootFromKeyAfterBridge) {
                    addSequential(new Shoot(CommandBase.shooter.KEY_SPEED), 5);
                    addSequential(new PrintCommand("Shoot From Key Again"));
                }
            }
        }
    }
}
