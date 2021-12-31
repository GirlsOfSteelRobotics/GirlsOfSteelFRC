package girlsofsteel.commands;

//moves to bridge, shoots, pushes bridge down

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.OI;
import girlsofsteel.objects.Camera;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Shooter;


public class AutonomousBridgeShoot extends CommandGroup {

    public AutonomousBridgeShoot(OI oi, Chassis chassis, Shooter shooter) {
        if (Camera.foundTarget()) {
            double distance = Chassis.DISTANCE_BACKBOARD_TO_BRIDGE
                - Camera.getXDistance();
            addSequential(new MoveToSetPoint(chassis, distance), 3.0);
        } else {
            addSequential(new MoveToSetPoint(chassis, Chassis.DISTANCE_KEY_TO_BRIDGE), 3.0);
        }
        if (Camera.foundTarget()) {
            addParallel(new ShootUsingTable(shooter, oi, false));
        } else {
            addParallel(new Shoot(shooter, oi, Shooter.BRIDGE_SPEED));
        }
    }

}
