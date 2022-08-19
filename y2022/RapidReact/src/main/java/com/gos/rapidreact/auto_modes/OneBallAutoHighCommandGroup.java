package com.gos.rapidreact.auto_modes;

import com.gos.rapidreact.commands.AutoLimelightConveyorAndShooterCommand;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.ShooterLimelightSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import com.gos.rapidreact.trajectory.OneBallTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class OneBallAutoHighCommandGroup extends SequentialCommandGroup {

    public OneBallAutoHighCommandGroup(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor,
                                       ShooterLimelightSubsystem shooterLimelight) {
        super(OneBallTrajectory.oneBallHigh(chassis),
            new AutoLimelightConveyorAndShooterCommand(shooterLimelight, shooter, verticalConveyor));
    }

}
