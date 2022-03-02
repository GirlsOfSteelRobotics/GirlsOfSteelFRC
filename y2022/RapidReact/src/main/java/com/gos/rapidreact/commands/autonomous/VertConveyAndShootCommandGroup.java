package com.gos.rapidreact.commands.autonomous;

import com.gos.rapidreact.commands.FeederVerticalConveyorForwardCommand;
import com.gos.rapidreact.commands.ShooterRpmPIDCommand;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import static com.gos.rapidreact.subsystems.ShooterSubsystem.DEFAULT_SHOOTER_RPM;

public class VertConveyAndShootCommandGroup extends ParallelCommandGroup {
    public VertConveyAndShootCommandGroup(VerticalConveyorSubsystem verticalConveyor, ShooterSubsystem shooter, double seconds) {
        super(new ShooterRpmPIDCommand(shooter, DEFAULT_SHOOTER_RPM).withTimeout(1),
            new FeederVerticalConveyorForwardCommand(verticalConveyor).withTimeout(seconds));
    }
}
