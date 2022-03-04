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
import com.gos.rapidreact.trajectory.Trajectory54;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.rapidreact.trajectory.TrajectoryB5;

import static com.gos.rapidreact.subsystems.ShooterSubsystem.DEFAULT_SHOOTER_RPM;

public class ThreeBallAuto extends SequentialCommandGroup {

    public ThreeBallAuto(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor, HorizontalConveyorSubsystem horizontalConveyor, CollectorSubsystem collector, double seconds) {
        super(new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM),
            new FeederVerticalConveyorForwardCommand(verticalConveyor).alongWith(
                new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM)).alongWith(
                    new CollectorDownCommand(collector)).withTimeout(1),
            TrajectoryB5.fromBto5(chassis).alongWith(new RollerInCommand(collector).withTimeout(2)),
            Trajectory54.from5to4(chassis).alongWith(new RollerInCommand(collector).withTimeout(2)),
            new HorizontalConveyorForwardCommand(horizontalConveyor),
            new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM),
            new FeederVerticalConveyorForwardCommand(verticalConveyor).alongWith(
                new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM)));
    }
}
