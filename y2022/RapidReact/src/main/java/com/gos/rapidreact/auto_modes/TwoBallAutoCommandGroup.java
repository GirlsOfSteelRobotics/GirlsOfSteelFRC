package com.gos.rapidreact.auto_modes;

import com.gos.rapidreact.commands.CollectorPivotPIDCommand;
import com.gos.rapidreact.commands.DriveDistanceCommand;
import com.gos.rapidreact.commands.FeederVerticalConveyorForwardCommand;
import com.gos.rapidreact.commands.HorizontalConveyorForwardCommand;
import com.gos.rapidreact.commands.RollerInCommand;
import com.gos.rapidreact.commands.ShooterRpmPIDCommand;
import com.gos.rapidreact.commands.VerticalConveyorUpCommand;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.rapidreact.subsystems.ShooterSubsystem.DEFAULT_SHOOTER_RPM;

public class TwoBallAutoCommandGroup extends SequentialCommandGroup {
    private static final double DRIVE_DISTANCE = Units.inchesToMeters(35);
    private static final double ALLOWABLE_ERROR = 0.05;

    public TwoBallAutoCommandGroup(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor, HorizontalConveyorSubsystem horizConveyor, CollectorSubsystem collector, double seconds) {
        super(new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM),
            new FeederVerticalConveyorForwardCommand(verticalConveyor).withTimeout(seconds).alongWith(new CollectorPivotPIDCommand(collector, 0)),
            new DriveDistanceCommand(chassis, DRIVE_DISTANCE, ALLOWABLE_ERROR).alongWith(new RollerInCommand(collector).withTimeout(2)).alongWith(new HorizontalConveyorForwardCommand(horizConveyor).withTimeout(2)),
            new ShooterRpmPIDCommand(shooter, 2500),
            new FeederVerticalConveyorForwardCommand(verticalConveyor).withTimeout(seconds));
    }
}
