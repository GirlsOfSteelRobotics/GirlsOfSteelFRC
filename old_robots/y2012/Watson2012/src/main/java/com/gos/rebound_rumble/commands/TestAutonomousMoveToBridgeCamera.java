package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.rebound_rumble.subsystems.Bridge;
import com.gos.rebound_rumble.subsystems.Chassis;

public class TestAutonomousMoveToBridgeCamera extends SequentialCommandGroup {

    public TestAutonomousMoveToBridgeCamera(Chassis chassis, Bridge bridge) {
        addRequirements(chassis);
        //        SmartDashboard.putBoolean("Camera?", false);
        //        SmartDashboard.putNumber("Distc ance from backboard to bridge", 0.0);
        //        SmartDashboard.putNumber("Distance from robot to bridge", 0.0);
        //        if(Camera.isConnected() && SmartDashboard.getBoolean("Camera?", false)){
        //            double distance = SmartDashboard.getDouble("Distance from backboard to bridge",
        //                0.0) - Camera.getXDistance();
        //            addCommands(new MoveToSetPoint(distance),2.0);
        //        }else{
        addCommands(new MoveToSetPoint(chassis, 2.7).withTimeout(2.0));
        //        }
        addCommands(new AutoBridgeDown(bridge).withTimeout(2.0));
        addCommands(new MoveToSetPoint(chassis, -0.5).withTimeout(3.0));
        addCommands(new BridgeUp(bridge));
    }

}
