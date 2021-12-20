package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.OI;
import girlsofsteel.objects.Camera;
import girlsofsteel.subsystems.Shooter;

public class AutonomousCameraKey extends CommandGroup {

    public AutonomousCameraKey(OI oi, Shooter shooter){
        if(Camera.isConnected()){
            addParallel(new ShootUsingTable(shooter, oi,  false));
        }else{
            addParallel(new Shoot(shooter, oi, Shooter.KEY_SPEED));
        }
    }

}
