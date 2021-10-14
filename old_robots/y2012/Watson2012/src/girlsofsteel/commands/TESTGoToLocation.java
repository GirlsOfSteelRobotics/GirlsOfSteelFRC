package girlsofsteel.commands;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TESTGoToLocation extends CommandGroup {

    public TESTGoToLocation(){
        //degree change relative to beginning position of the robot to end facing
        //runs the TurnToSetPoint and MoveToSetPoint commands in sequence to go to location.
        double x = SmartDashboard.getDouble("GTL,x", 0.0);
        double y = SmartDashboard.getDouble("GTL,y", 0.0);
        double degreesToFace = SmartDashboard.getDouble("GTL,degrees to face", 0.0);
        double degreesToTurn = MathUtils.atan2(y, x)*180/Math.PI;
        double distanceToMove = Math.sqrt((x*x)+(y*y));
        addSequential(new TurnToSetPoint(degreesToTurn));
        addSequential(new MoveToSetPoint(distanceToMove));
        addSequential(new TurnToSetPoint(degreesToFace - degreesToTurn));
    }
}
