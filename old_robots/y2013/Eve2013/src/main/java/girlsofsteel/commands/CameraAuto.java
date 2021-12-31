package girlsofsteel.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import girlsofsteel.objects.ShooterCamera;
import girlsofsteel.subsystems.Chassis;

public class CameraAuto extends CommandGroup {
    private static final double angleAjust = 7;

    public CameraAuto(Chassis chassis) {
        double turnTheta = ShooterCamera.getTopDiffAngle() + angleAjust;
        if (ShooterCamera.foundTopTarget()) {
            addSequential(new Rotate(chassis, turnTheta, true));
        }
    }

}
