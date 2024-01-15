package com.gos.rapidreact.commands.autonomous;

import com.gos.rapidreact.commands.HorizontalConveyorForwardCommand;
import com.gos.rapidreact.commands.RollerInCommand;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class IntakeWithHorizontal extends ParallelCommandGroup {
    public IntakeWithHorizontal(CollectorSubsystem collector, HorizontalConveyorSubsystem horizConveyor, double intakeSeconds) {
        super(
            new RollerInCommand(collector).withTimeout(intakeSeconds),
            new HorizontalConveyorForwardCommand(horizConveyor).withTimeout(intakeSeconds)
        );
    }
}
