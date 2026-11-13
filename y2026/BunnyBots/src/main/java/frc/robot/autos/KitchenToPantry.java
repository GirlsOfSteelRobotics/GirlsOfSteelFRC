package frc.robot.autos;

import com.gos.lib.pathing.ChoreoUtils;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.enums.StartingPositions;
import frc.robot.subsystems.ChassisSubsystem;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class KitchenToPantry extends GosAuto {
    public KitchenToPantry(ChassisSubsystem chassisSubsystem) {
        super(StartingPositions.STARTKITCHENPANTRY);

        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("startKitchenPantry").getPose())));
        addCommands(followChoreoPath("KitchenToPantry"));
        addCommands(chassisSubsystem.createStop());
    }
}
