package frc.robot.autos;

import com.gos.lib.pathing.ChoreoUtils;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.enums.StartingPositions;
import frc.robot.subsystems.ChassisSubsystem;

import static com.gos.lib.pathing.PathPlannerUtils.followChoreoPath;

public class KitchenToOven extends GosAuto {
    public KitchenToOven(ChassisSubsystem chassisSubsystem) {
        super(StartingPositions.STARTKITCHENOVEN);

        addCommands(Commands.runOnce(() -> chassisSubsystem.resetPose(ChoreoUtils.getPathStartingPose("startKitchenOven").getPose())));
        addCommands(followChoreoPath("KitchenToOven"));
        addCommands(chassisSubsystem.createStop());


    }

}
