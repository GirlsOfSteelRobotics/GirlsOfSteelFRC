package com.gos.rapidreact.auto_modes;

import com.gos.rapidreact.commands.CollectorPivotPIDCommand;
import com.gos.rapidreact.commands.FeederVerticalConveyorForwardCommand;
import com.gos.rapidreact.commands.HorizontalConveyorForwardCommand;
import com.gos.rapidreact.commands.RollerInCommand;
import com.gos.rapidreact.commands.ShooterRpmPIDCommand;
import com.gos.rapidreact.commands.VerticalConveyorUpCommand;
import com.gos.rapidreact.commands.autonomous.IntakeWithHorizontal;
import com.gos.rapidreact.commands.autonomous.ShootWithBothIntakes;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import com.gos.rapidreact.trajectory.FourBallTrajectories;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.rapidreact.subsystems.ShooterSubsystem.DEFAULT_SHOOTER_RPM;

public class FourBallAutoCommandGroup extends SequentialCommandGroup {
    private static final double FIRST_SHOT_RPM = DEFAULT_SHOOTER_RPM;
    // private static final double SECOND_SHOT_RPM = DEFAULT_SHOOTER_RPM;

    @SuppressWarnings("PMD") // TODO(ashley) Remove once all the commands are back in
    public FourBallAutoCommandGroup(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor, HorizontalConveyorSubsystem horizontalConveyor, CollectorSubsystem collector) {
        super(
            FourBallTrajectories.fourBallPart1(chassis)
                .alongWith(new IntakeWithHorizontal(collector, horizontalConveyor, 3))
                .alongWith(new CollectorPivotPIDCommand(collector, CollectorSubsystem.DOWN_ANGLE)).withTimeout(3),
            FourBallTrajectories.fourBallPart2(chassis),
            new ShootWithBothIntakes(verticalConveyor, horizontalConveyor, shooter, FIRST_SHOT_RPM, 3));
//            FourBallTrajectories.fourBallPart3(chassis),
//                .alongWith(new RollerInCommand(collector).withTimeout(2)),
//                .alongWith(new HorizontalConveyorForwardCommand(horizontalConveyor).withTimeout(3)),
//            FourBallTrajectories.fourBallPart4(chassis),
//                .alongWith(new IntakeWithHorizontal(collector, horizontalConveyor, 3)),
//                .alongWith(new ShootWithBothIntakes(verticalConveyor, horizontalConveyor, shooter, FIRST_SHOT_RPM, 5));
    }
}
