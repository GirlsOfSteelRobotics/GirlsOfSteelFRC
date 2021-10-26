package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.objects.PositionInfo;

public class Autonomous extends CommandGroup {

    public Autonomous(int shootingPosition, int shots, boolean move){
        int angle = PositionInfo.getAngle(shootingPosition);
        double speed = PositionInfo.getSpeed(shootingPosition);
        if(move){
            //move forward at 30% for 2 seconds
            addSequential(new StartGyro(0));
            addSequential(new AutoMove(0, 0.3, 2));
            addSequential(new Rotate(PositionInfo.getAngle(shootingPosition),
                    true));
        }else{
            addSequential(new StartGyro(angle));
        }
        addSequential(new AutoShootMany(shots, speed));
    }

}
