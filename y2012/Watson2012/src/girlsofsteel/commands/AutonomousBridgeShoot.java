package girlsofsteel.commands;

//moves to bridge, shoots, pushes bridge down

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.objects.Camera;


public class AutonomousBridgeShoot extends CommandGroup {
    
    public AutonomousBridgeShoot(){
        if(Camera.foundTarget()){
            double distance = CommandBase.chassis.DISTANCE_BACKBOARD_TO_BRIDGE
                    - Camera.getXDistance();
            addSequential(new MoveToSetPoint(distance),3.0);
        }else{
            addSequential(new MoveToSetPoint(CommandBase.chassis.DISTANCE_KEY_TO_BRIDGE),3.0);
        }
        if(Camera.foundTarget()){
            addParallel(new ShootUsingTable(false));
        }else{
            addParallel(new Shoot(CommandBase.shooter.BRIDGE_SPEED));
        }
    }
    
}
