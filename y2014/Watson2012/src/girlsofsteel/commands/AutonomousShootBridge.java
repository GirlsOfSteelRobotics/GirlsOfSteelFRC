package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import girlsofsteel.objects.Camera;

public class AutonomousShootBridge extends CommandGroup {
    
    public AutonomousShootBridge(){
        if(Camera.foundTarget()){
            addParallel(new ShootUsingTable(false));
        }else{
            addParallel(new Shoot(CommandBase.shooter.KEY_SPEED));
        }
        addSequential(new WaitCommand(4.0));
        if(Camera.foundTarget()){
            double distance = CommandBase.chassis.DISTANCE_BACKBOARD_TO_BRIDGE
                    - Camera.getXDistance();
            addSequential(new MoveToSetPoint(distance),3.0);
        }else{
            addSequential(new MoveToSetPoint(CommandBase.chassis.DISTANCE_KEY_TO_BRIDGE),3.0);
        }
        addSequential(new BridgeDown());
    }
    
}