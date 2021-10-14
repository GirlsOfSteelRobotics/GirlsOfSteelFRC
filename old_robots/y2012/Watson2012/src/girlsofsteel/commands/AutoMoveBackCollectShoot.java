package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoMoveBackCollectShoot extends CommandGroup{

//    public AutoMoveBackCollectShoot(double x, double y, double degreesToFace){
//        addSequential(new GoToLocation(x,y,degreesToFace));
    public AutoMoveBackCollectShoot(double distance){
        addSequential(new MoveToSetPoint(distance));
        addSequential(new BridgeDown());
        addSequential(new Collect());
        addSequential(new ShootUsingTable(false));
    }

}
