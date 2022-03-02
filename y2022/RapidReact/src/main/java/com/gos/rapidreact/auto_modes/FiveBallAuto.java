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
import com.gos.rapidreact.trajectory.Trajectory47;
import com.gos.rapidreact.trajectory.Trajectory54;
import com.gos.rapidreact.trajectory.TrajectoryB5;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.rapidreact.subsystems.ShooterSubsystem.DEFAULT_SHOOTER_RPM;

public class FiveBallAuto extends SequentialCommandGroup {

    public FiveBallAuto(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor, HorizontalConveyorSubsystem horizontalConveyor, CollectorSubsystem collector, double seconds) {
        super(new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM),
            new VertConveyAndShootCommandGroup(verticalConveyor, shooter, seconds).alongWith(new CollectorDownCommand(collector)),
            TrajectoryB5.fromBto5(chassis),
            new HorizontalConveyorForwardCommand(horizontalConveyor),
            Trajectory54.from5to4(chassis),
            new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM),
            new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM),
            new VertConveyAndShootCommandGroup(verticalConveyor, shooter, seconds),
            new VertConveyAndShootCommandGroup(verticalConveyor, shooter, seconds),
            new HorizontalConveyorForwardCommand(horizontalConveyor),
            Trajectory47.from4to7(chassis),
            new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM),
            new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM));

    }
}
