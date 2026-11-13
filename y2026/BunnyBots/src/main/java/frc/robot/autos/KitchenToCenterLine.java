package frc.robot.autos;

import com.gos.lib.pathing.ChoreoUtils;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.enums.StartingPositions;
import frc.robot.subsystems.ChassisSubsystem;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class KitchenToCenterLine extends GosAuto {
    public KitchenToCenterLine(ChassisSubsystem chassisSubsystem) {
        super(StartingPositions.STARTKITCHENCENTERLINE);

        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("startKitchenCenterLine").getPose())));
        addCommands(followChoreoPath("KitchenToCenterLine"));
        addCommands(chassisSubsystem.createStop());
    }
}
