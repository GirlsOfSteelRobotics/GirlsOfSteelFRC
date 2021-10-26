package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TESTAutonomousMoveToBridgeCamera extends CommandGroup {

    public TESTAutonomousMoveToBridgeCamera(){
        requires(CommandBase.chassis);
//        SmartDashboard.putBoolean("Camera?", false);
//        SmartDashboard.putDouble("Distc ance from backboard to bridge", 0.0);
//        SmartDashboard.putDouble("Distance from robot to bridge", 0.0);
//        if(Camera.isConnected() && SmartDashboard.getBoolean("Camera?", false)){
//            double distance = SmartDashboard.getDouble("Distance from backboard to bridge",
//                0.0) - Camera.getXDistance();
//            addSequential(new MoveToSetPoint(distance),2.0);
//        }else{
            addSequential(new MoveToSetPoint(2.7),2.0);
//        }
        addSequential(new AutoBridgeDown(),2.0);
        addSequential(new MoveToSetPoint(-0.5), 3.0);
        addSequential(new BridgeUp());
    }

}
