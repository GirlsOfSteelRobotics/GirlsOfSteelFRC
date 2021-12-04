package girlsofsteel.commands;

//moves to bridge, shoots, pushes bridge down

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.objects.Camera;
import girlsofsteel.subsystems.Chassis;


public class AutonomousBridgeShoot extends CommandGroup {

    public AutonomousBridgeShoot(){
        if(Camera.foundTarget()){
            double distance = Chassis.DISTANCE_BACKBOARD_TO_BRIDGE
                    - Camera.getXDistance();
            addSequential(new MoveToSetPoint(distance),3.0);
        }else{
            addSequential(new MoveToSetPoint(Chassis.DISTANCE_KEY_TO_BRIDGE),3.0);
        }
        if(Camera.foundTarget()){
            addParallel(new ShootUsingTable(false));
        }else{
            addParallel(new Shoot(CommandBase.shooter.BRIDGE_SPEED));
        }
    }

}
