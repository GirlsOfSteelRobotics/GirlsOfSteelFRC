package com.gos.rebound_rumble.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.rebound_rumble.OI;
import com.gos.rebound_rumble.subsystems.Bridge;
import com.gos.rebound_rumble.subsystems.Chassis;
import com.gos.rebound_rumble.subsystems.Collector;
import com.gos.rebound_rumble.subsystems.Shooter;

public class AutoMoveBackCollectShoot extends SequentialCommandGroup {

    //    public AutoMoveBackCollectShoot(double x, double y, double degreesToFace){
    //        addCommands(new GoToLocation(x,y,degreesToFace));
    public AutoMoveBackCollectShoot(OI oi, Chassis chassis, Bridge bridge, Collector collector, Shooter shooter, double distance) {
        addCommands(new MoveToSetPoint(chassis, distance));
        addCommands(new BridgeDown(bridge));
        addCommands(new Collect(collector));
        addCommands(new ShootUsingTable(shooter, oi, false));
    }

}
