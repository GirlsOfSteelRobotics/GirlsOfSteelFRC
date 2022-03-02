package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.objects.Camera;
import com.gos.rebound_rumble.subsystems.Bridge;
import com.gos.rebound_rumble.subsystems.Chassis;
import com.gos.rebound_rumble.subsystems.Shooter;

public class AutonomousCommandGroup extends SequentialCommandGroup {

    @SuppressWarnings({"PMD.ExcessiveParameterList", "PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
    public AutonomousCommandGroup(OI oi, Shooter shooter, Chassis chassis, Bridge bridge, boolean shoot, boolean moveToBridge,
                                  double yDistance,
                                  boolean shootFromBridge, boolean goBackToKey,
                                  boolean shootFromKeyAfterBridge) {
        addRequirements(shooter);
        addRequirements(chassis);
        addRequirements(bridge);

        //        addParallel(new Collect());
        addCommands(new PrintCommand("reached"));

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
                addCommands(new ShootUsingTable(shooter, oi, false).withTimeout(6.0));
                addCommands(new PrintCommand("Shoot With Table"));
            } else {
                addCommands(new Shoot(shooter, oi, Shooter.KEY_SPEED).withTimeout(6.0));
                addCommands(new PrintCommand("Shoot two from key"));
            }
        }
        if (moveToBridge) {
            if (Camera.isConnected() && Camera.getXDistance() != 0) {
                //if the camera has found the target at least once
                yDistance = Chassis.DISTANCE_BACKBOARD_TO_BRIDGE
                    - Camera.getXDistance();
            }
            addCommands(new MoveToSetPoint(chassis, yDistance).withTimeout(2.0));
            addCommands(new PrintCommand("Move To Bridge"));
            addCommands(new AutoBridgeDown(bridge).withTimeout(2.0));
            addCommands(new MoveToSetPoint(chassis, -0.5).withTimeout(3.0));
            addCommands(new BridgeUp(bridge));
            addCommands(new PrintCommand("Bridge Collected"));
            if (shootFromBridge) {
                if (Camera.isConnected() && Camera.getXDistance() != 0) {
                    addCommands(new ShootUsingTable(shooter, oi, false).withTimeout(2.5));
                    addCommands(new PrintCommand("Shoot With Table"));
                } else {
                    addCommands(new Shoot(shooter, oi, Shooter.BRIDGE_SPEED).withTimeout(2.5));
                }
                addCommands(new PrintCommand("Shoot from Bridge"));
            }
            if (goBackToKey) {
                addCommands(new MoveToSetPoint(chassis, -yDistance));
                addCommands(new PrintCommand("Back to Key"));
                if (shootFromKeyAfterBridge) {
                    addCommands(new Shoot(shooter, oi, Shooter.KEY_SPEED).withTimeout(5));
                    addCommands(new PrintCommand("Shoot From Key Again"));
                }
            }
        }
    }
}
