package com.gos.rapidreact.commands.autonomous;

import com.gos.rapidreact.commands.FeederVerticalConveyorForwardCommand;
import com.gos.rapidreact.commands.HorizontalConveyorForwardCommand;
import com.gos.rapidreact.commands.ShooterRpmPIDCommand;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootWithBothIntakes extends SequentialCommandGroup {
    public ShootWithBothIntakes(VerticalConveyorSubsystem verticalConveyor, HorizontalConveyorSubsystem horizontalConveyor, ShooterSubsystem shooter, double rpm, double conveyorsTimeout) {
        super(
            new ShooterRpmPIDCommand(shooter, rpm),
            new FeederVerticalConveyorForwardCommand(verticalConveyor).withTimeout(conveyorsTimeout)
                .alongWith(new HorizontalConveyorForwardCommand(horizontalConveyor).withTimeout(conveyorsTimeout)));
    }
}
