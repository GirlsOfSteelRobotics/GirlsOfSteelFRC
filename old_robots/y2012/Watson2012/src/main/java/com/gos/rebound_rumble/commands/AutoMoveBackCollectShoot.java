package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.subsystems.Bridge;
import com.gos.rebound_rumble.subsystems.Chassis;
import com.gos.rebound_rumble.subsystems.Collector;
import com.gos.rebound_rumble.subsystems.Shooter;

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
