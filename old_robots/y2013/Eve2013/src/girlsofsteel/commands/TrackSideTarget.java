package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.objects.ShooterCamera;
import girlsofsteel.subsystems.Chassis;

public class TrackSideTarget extends CommandGroup {

    public TrackSideTarget(Chassis chassis){
        double turnTheta = ShooterCamera.getSideDiffAngle() +
                ShooterCamera.getLocationOffsetAngle();
        if (ShooterCamera.foundSideTarget()) {
            addSequential(new Rotate(chassis, turnTheta, true));
        }
    }

}
