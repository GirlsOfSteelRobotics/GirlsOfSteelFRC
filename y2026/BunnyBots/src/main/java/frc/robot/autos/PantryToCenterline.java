package frc.robot.autos;

import com.gos.lib.pathing.ChoreoUtils;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.enums.StartingPositions;
import frc.robot.subsystems.ChassisSubsystem;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class PantryToCenterline extends GosAuto {
    public PantryToCenterline(ChassisSubsystem chassisSubsystem) {
        super(StartingPositions.PANTRYBASE);

        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("startPantryCenterLine").getPose())));
        addCommands(followChoreoPath("PantryToCenterLine"));
        addCommands(chassisSubsystem.createStop());
    }
}
