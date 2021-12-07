package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.objects.ShooterCamera;

public class CameraAuto extends CommandGroup {
    private final static double angleAjust = 7;

    public CameraAuto(){
        double turnTheta = ShooterCamera.getTopDiffAngle() + angleAjust;
        if (ShooterCamera.foundTopTarget()) {
            addSequential(new Rotate(turnTheta, true));
        }
    }

}
