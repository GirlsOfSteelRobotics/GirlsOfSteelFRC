package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.objects.Camera;

public class AutonomousCameraKey extends CommandGroup {

    public AutonomousCameraKey(){
        if(Camera.isConnected()){
            addParallel(new ShootUsingTable(false));
        }else{
            addParallel(new Shoot(CommandBase.shooter.KEY_SPEED));
        }
    }

}
