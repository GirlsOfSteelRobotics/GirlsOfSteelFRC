package com.gos.rapidreact.commands;


import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShooterFeederCommandGroup extends SequentialCommandGroup {

    public ShooterFeederCommandGroup(VerticalConveyorSubsystem verticalConveyor, ShooterSubsystem shooter, int seconds) {
        super(new ShooterRpmPIDCommand(shooter, ShooterSubsystem.DEFAULT_SHOOTER_RPM),
            new FeederVerticalConveyorForwardCommand(verticalConveyor).withTimeout(seconds));

    }
}