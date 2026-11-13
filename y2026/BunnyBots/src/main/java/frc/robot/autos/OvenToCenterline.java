package frc.robot.autos;

import com.gos.lib.pathing.ChoreoUtils;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.CombinedCommand;
import frc.robot.enums.StartingPositions;
import frc.robot.subsystems.ChassisSubsystem;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class OvenToCenterline extends GosAuto {
    public OvenToCenterline(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        super(StartingPositions.OVEN);

        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("startOvenCenterLine").getPose())));
        addCommands(followChoreoPath("OvenToCenterLine"));
        addCommands(chassisSubsystem.createStop());


    }


}
