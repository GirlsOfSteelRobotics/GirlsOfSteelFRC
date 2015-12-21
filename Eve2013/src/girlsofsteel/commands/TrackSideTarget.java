package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.objects.ShooterCamera;

public class TrackSideTarget extends CommandGroup {

    public TrackSideTarget(){
        double turnTheta = ShooterCamera.getSideDiffAngle() + 
                ShooterCamera.getLocationOffsetAngle();
        if (ShooterCamera.foundSideTarget()) {
            addSequential(new Rotate(turnTheta, true));
        }
    }
    
}