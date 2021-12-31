package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.OI;
import girlsofsteel.subsystems.Bridge;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Collector;
import girlsofsteel.subsystems.Shooter;

public class AutoMoveBackCollectShoot extends CommandGroup {

    //    public AutoMoveBackCollectShoot(double x, double y, double degreesToFace){
//        addSequential(new GoToLocation(x,y,degreesToFace));
    public AutoMoveBackCollectShoot(OI oi, Chassis chassis, Bridge bridge, Collector collector, Shooter shooter, double distance) {
        addSequential(new MoveToSetPoint(chassis, distance));
        addSequential(new BridgeDown(bridge));
        addSequential(new Collect(collector));
        addSequential(new ShootUsingTable(shooter, oi, false));
    }

}
