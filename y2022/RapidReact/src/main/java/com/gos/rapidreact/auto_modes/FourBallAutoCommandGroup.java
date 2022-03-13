package com.gos.rapidreact.auto_modes;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import com.gos.rapidreact.trajectory.FourBallTrajectories;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FourBallAutoCommandGroup extends SequentialCommandGroup {
    // private static final double FIRST_SHOT_RPM = DEFAULT_SHOOTER_RPM;
    // private static final double SECOND_SHOT_RPM = DEFAULT_SHOOTER_RPM;

    @SuppressWarnings("PMD") // TODO(ashley) Remove once all the commands are back in
    public FourBallAutoCommandGroup(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor, HorizontalConveyorSubsystem horizontalConveyor, CollectorSubsystem collector) {
        super(
            // new ShooterRpmPIDCommand(shooter, FIRST_SHOT_RPM),
            // new FeederVerticalConveyorForwardCommand(verticalConveyor)
            //     .alongWith(new ShooterRpmPIDCommand(shooter, FIRST_SHOT_RPM))
            //     .alongWith(new CollectorPivotPIDCommand(collector, CollectorSubsystem.DOWN_ANGLE),
            FourBallTrajectories.fourBallPart1(chassis),
            //    .alongWith(new RollerInCommand(collector).withTimeout(2)),
            //new HorizontalConveyorForwardCommand(horizontalConveyor).withTimeout(2));
            FourBallTrajectories.fourBallPart2(chassis),
            //new HorizontalConveyorForwardCommand(horizontalConveyor).withTimeout(2),
            //new ShooterRpmPIDCommand(shooter, FIRST_SHOT_RPM),
            //new FeederVerticalConveyorForwardCommand(verticalConveyor)
            //    .alongWith(new ShooterRpmPIDCommand(shooter, FIRST_SHOT_RPM)).withTimeout(1),
            FourBallTrajectories.fourBallPart3(chassis),
            //    .alongWith(new RollerInCommand(collector).withTimeout(2)),
            // new HorizontalConveyorForwardCommand(horizontalConveyor).withTimeout(2),
            FourBallTrajectories.fourBallPart4(chassis));
            // new HorizontalConveyorForwardCommand(horizontalConveyor).withTimeout(2),
            // new ShooterRpmPIDCommand(shooter, FIRST_SHOT_RPM),
            // new FeederVerticalConveyorForwardCommand(verticalConveyor)
            //     .alongWith(new ShooterRpmPIDCommand(shooter, FIRST_SHOT_RPM)


    }
}
