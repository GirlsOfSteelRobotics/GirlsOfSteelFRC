package com.gos.rapidreact.auto_modes;

import com.gos.rapidreact.commands.CollectorDownCommand;
import com.gos.rapidreact.commands.HorizontalConveyorForwardCommand;
import com.gos.rapidreact.commands.ShooterRpmPIDCommand;
import com.gos.rapidreact.commands.autonomous.VertConveyAndShootCommandGroup;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import com.gos.rapidreact.trajectory.FiveBallTrajectories;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.rapidreact.subsystems.ShooterSubsystem.DEFAULT_SHOOTER_RPM;

public class FiveBallAutoLow extends SequentialCommandGroup {
    private static final double HORIZ_CONV_TIMEOUT = 2;
    private static final double VERT_CONV_TIMEOUT = 2;

    public FiveBallAutoLow(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor, HorizontalConveyorSubsystem horizontalConveyor, CollectorSubsystem collector) {
        super(new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM),
            new VertConveyAndShootCommandGroup(verticalConveyor, shooter, VERT_CONV_TIMEOUT)
                .alongWith(new CollectorDownCommand(collector)),
            FiveBallTrajectories.tarmacToFirstBall(chassis),
            new HorizontalConveyorForwardCommand(horizontalConveyor).withTimeout(HORIZ_CONV_TIMEOUT),
            FiveBallTrajectories.firstBallToSecondBall(chassis),
            new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM),
            new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM),
            new VertConveyAndShootCommandGroup(verticalConveyor, shooter, VERT_CONV_TIMEOUT),
            new VertConveyAndShootCommandGroup(verticalConveyor, shooter, VERT_CONV_TIMEOUT),
            new HorizontalConveyorForwardCommand(horizontalConveyor).withTimeout(HORIZ_CONV_TIMEOUT),
            FiveBallTrajectories.from4to7(chassis),
            new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM),
            new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM));

    }
}
