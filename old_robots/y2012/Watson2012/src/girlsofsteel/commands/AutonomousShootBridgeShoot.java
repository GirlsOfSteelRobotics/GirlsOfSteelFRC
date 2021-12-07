package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import girlsofsteel.objects.Camera;
import girlsofsteel.subsystems.Chassis;

public class AutonomousShootBridgeShoot extends CommandGroup {

    public AutonomousShootBridgeShoot(){
        if(Camera.foundTarget()){
            addParallel(new ShootUsingTable(false));
        }else{
            addParallel(new Shoot(CommandBase.shooter.KEY_SPEED));
        }
        addSequential(new WaitCommand(5.5));
        if(Camera.foundTarget()){
            double distance = Chassis.DISTANCE_BACKBOARD_TO_BRIDGE
                    - Camera.getXDistance();
            addSequential(new MoveToSetPoint(distance),3.0);
        }else{
            addSequential(new MoveToSetPoint(Chassis.DISTANCE_KEY_TO_BRIDGE),3.0);
        }
        addSequential(new BridgeDown());
        if(Camera.foundTarget()){
            addParallel(new ShootUsingTable(false));
        }else{
            addParallel(new Shoot(CommandBase.shooter.BRIDGE_SPEED));
        }
    }

}
