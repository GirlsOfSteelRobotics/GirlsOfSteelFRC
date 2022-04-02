package com.gos.rapidreact.auto_modes;

import com.gos.rapidreact.commands.CollectorPivotPIDCommand;
import com.gos.rapidreact.commands.DriveDistanceCommand;
import com.gos.rapidreact.commands.FeederVerticalConveyorForwardCommand;
import com.gos.rapidreact.commands.HorizontalConveyorForwardCommand;
import com.gos.rapidreact.commands.RollerInCommand;
import com.gos.rapidreact.commands.ShooterRpmPIDCommand;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.rapidreact.subsystems.ShooterSubsystem.DEFAULT_SHOOTER_RPM;

public class TwoBallAutoLowCommandGroup extends SequentialCommandGroup {
    private static final double FIRST_SHOT_VERT_CONV_TIME = 3;
    private static final double FIRST_SHOT_RPM = 1900;
    private static final double SECOND_SHOT_RPM = DEFAULT_SHOOTER_RPM;

    private static final double SECOND_SHOT_VERT_CONV_TIME = 3;

    private static final double DRIVE_DISTANCE = Units.inchesToMeters(35);
    private static final double ALLOWABLE_ERROR = 0.05;

    public TwoBallAutoLowCommandGroup(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor, HorizontalConveyorSubsystem horizConveyor, CollectorSubsystem collector) {
        super(
            new ShooterRpmPIDCommand(shooter, FIRST_SHOT_RPM),
            new FeederVerticalConveyorForwardCommand(verticalConveyor).withTimeout(FIRST_SHOT_VERT_CONV_TIME)
                .alongWith(new CollectorPivotPIDCommand(collector, 0)),
            new DriveDistanceCommand(chassis, DRIVE_DISTANCE, ALLOWABLE_ERROR)
                .alongWith(new RollerInCommand(collector).withTimeout(2))
                .alongWith(new HorizontalConveyorForwardCommand(horizConveyor).withTimeout(2)),
            new DriveDistanceCommand(chassis, -DRIVE_DISTANCE, ALLOWABLE_ERROR),
            new ShooterRpmPIDCommand(shooter, SECOND_SHOT_RPM),
            new FeederVerticalConveyorForwardCommand(verticalConveyor)
                .withTimeout(SECOND_SHOT_VERT_CONV_TIME));
    }
}
