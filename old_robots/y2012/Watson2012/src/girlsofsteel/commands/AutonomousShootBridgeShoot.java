package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import girlsofsteel.OI;
import girlsofsteel.objects.Camera;
import girlsofsteel.subsystems.Bridge;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Shooter;

public class AutonomousShootBridgeShoot extends CommandGroup {

    public AutonomousShootBridgeShoot(OI oi, Chassis chassis, Bridge bridge, Shooter shooter){
        if(Camera.foundTarget()){
            addParallel(new ShootUsingTable(shooter, oi, false));
        }else{
            addParallel(new Shoot(shooter, oi, Shooter.KEY_SPEED));
        }
        addSequential(new WaitCommand(5.5));
        if(Camera.foundTarget()){
            double distance = Chassis.DISTANCE_BACKBOARD_TO_BRIDGE
                    - Camera.getXDistance();
            addSequential(new MoveToSetPoint(chassis, distance),3.0);
        }else{
            addSequential(new MoveToSetPoint(chassis, Chassis.DISTANCE_KEY_TO_BRIDGE),3.0);
        }
        addSequential(new BridgeDown(bridge));
        if(Camera.foundTarget()){
            addParallel(new ShootUsingTable(shooter, oi,  false));
        }else{
            addParallel(new Shoot(shooter, oi, Shooter.BRIDGE_SPEED));
        }
    }

}
