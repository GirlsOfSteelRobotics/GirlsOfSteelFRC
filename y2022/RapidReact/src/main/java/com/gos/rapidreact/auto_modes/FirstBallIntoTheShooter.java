package com.gos.rapidreact.auto_modes;


import com.gos.rapidreact.commands.DriveDistanceCommand;
import com.gos.rapidreact.commands.ShooterRpmPIDCommand;
import com.gos.rapidreact.commands.VerticalConveyorTimedCommand;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class FirstBallIntoTheShooter extends SequentialCommandGroup {
    public FirstBallIntoTheShooter(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor) {
        super(new DriveDistanceCommand(chassis, .762, 0.05),
            new ShooterRpmPIDCommand(shooter, 2500),
            new VerticalConveyorTimedCommand(verticalConveyor, 10));
    }
}
