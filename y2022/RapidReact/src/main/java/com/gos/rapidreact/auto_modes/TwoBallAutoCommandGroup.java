package com.gos.rapidreact.auto_modes;


import com.gos.rapidreact.commands.DriveDistanceCommand;
import com.gos.rapidreact.commands.ShooterRpmPIDCommand;
import com.gos.rapidreact.commands.VerticalConveyorUpCommand;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.rapidreact.subsystems.ShooterSubsystem.DEFAULT_SHOOTER_RPM;

public class TwoBallAutoCommandGroup extends SequentialCommandGroup {
    private static final double DRIVE_DISTANCE = Units.inchesToMeters(30);
    private static final double ALLOWABLE_ERROR = 0.05;

    public TwoBallAutoCommandGroup(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor, double seconds) {
        super(new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM),
            new VerticalConveyorUpCommand(verticalConveyor).withTimeout(seconds), new DriveDistanceCommand(chassis, DRIVE_DISTANCE, ALLOWABLE_ERROR), new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM),
            new VerticalConveyorUpCommand(verticalConveyor).withTimeout(seconds));
    }
}