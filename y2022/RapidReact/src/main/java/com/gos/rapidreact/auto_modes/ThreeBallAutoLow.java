package com.gos.rapidreact.auto_modes;

import com.gos.rapidreact.commands.CollectorDownCommand;
import com.gos.rapidreact.commands.FeederVerticalConveyorForwardCommand;
import com.gos.rapidreact.commands.HorizontalConveyorForwardCommand;
import com.gos.rapidreact.commands.RollerInCommand;
import com.gos.rapidreact.commands.ShooterRpmPIDCommand;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import com.gos.rapidreact.trajectory.ThreeBallTrajectories;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.rapidreact.subsystems.ShooterSubsystem.DEFAULT_SHOOTER_RPM;

public class ThreeBallAutoLow extends SequentialCommandGroup {
    private static final double FIRST_SHOT_RPM = DEFAULT_SHOOTER_RPM;
    private static final double SECOND_SHOT_RPM = DEFAULT_SHOOTER_RPM;

    public ThreeBallAutoLow(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor, HorizontalConveyorSubsystem horizontalConveyor, CollectorSubsystem collector) {
        super(
            new ShooterRpmPIDCommand(shooter, FIRST_SHOT_RPM),
            new FeederVerticalConveyorForwardCommand(verticalConveyor).withTimeout(1)
                .alongWith(new ShooterRpmPIDCommand(shooter, FIRST_SHOT_RPM))
                .alongWith(new CollectorDownCommand(collector)).withTimeout(1),
            ThreeBallTrajectories.tarmacToFirstBall(chassis)
                .alongWith(new RollerInCommand(collector).withTimeout(2)),
            new HorizontalConveyorForwardCommand(horizontalConveyor).withTimeout(2),
            ThreeBallTrajectories.firstBallToSecondBall(chassis)
                .alongWith(new RollerInCommand(collector).withTimeout(2)),
            new HorizontalConveyorForwardCommand(horizontalConveyor).withTimeout(2),
            new ShooterRpmPIDCommand(shooter, SECOND_SHOT_RPM),
            new FeederVerticalConveyorForwardCommand(verticalConveyor)
                .alongWith(new ShooterRpmPIDCommand(shooter, SECOND_SHOT_RPM)));
    }
}
