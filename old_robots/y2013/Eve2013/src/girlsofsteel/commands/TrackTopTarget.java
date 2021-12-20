package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.objects.ShooterCamera;
import girlsofsteel.subsystems.Chassis;

public class TrackTopTarget extends CommandGroup {

    public TrackTopTarget(Chassis chassis){
        double turnTheta = ShooterCamera.getTopDiffAngle() +
                ShooterCamera.getLocationOffsetAngle();
        if (ShooterCamera.foundTopTarget()) {
            addSequential(new Rotate(chassis, turnTheta, true));
        }
    }

}
