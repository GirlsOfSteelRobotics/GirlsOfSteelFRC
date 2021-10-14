package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.objects.ShooterCamera;

public class TrackTopTarget extends CommandGroup {

    public TrackTopTarget(){
        double turnTheta = ShooterCamera.getTopDiffAngle() +
                ShooterCamera.getLocationOffsetAngle();
        if (ShooterCamera.foundTopTarget()) {
            addSequential(new Rotate(turnTheta, true));
        }
    }

}
