package com.gos.rapidreact.auto_modes;

import com.gos.rapidreact.commands.AutoNoLimelightConveyorAndShooterCommand;
import com.gos.rapidreact.commands.CollectorPivotPIDCommand;
import com.gos.rapidreact.commands.DriveDistanceCommand;
import com.gos.rapidreact.commands.autonomous.IntakeWithHorizontal;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import com.gos.rapidreact.trajectory.FourBallTrajectories;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TwoBallAutoHighCommandGroup extends SequentialCommandGroup {
    private static final double PIVOT_ANGLE_DOWN = 7;

    public TwoBallAutoHighCommandGroup(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor,
                                       HorizontalConveyorSubsystem horizontalConveyor, CollectorSubsystem collector) {
        super(FourBallTrajectories.fourBallPart1(chassis)
                .alongWith(new CollectorPivotPIDCommand(collector, PIVOT_ANGLE_DOWN))
                // Run the intake the entire time, while we wait for the previous commands to finish
                .raceWith(new IntakeWithHorizontal(collector, horizontalConveyor, 999)),
            new WaitCommand(.5),
            new AutoNoLimelightConveyorAndShooterCommand(shooter, verticalConveyor).withTimeout(7)
                .alongWith(new CollectorPivotPIDCommand(collector, CollectorSubsystem.UP_ANGLE)),
            // new ShootWithBothIntakes(verticalConveyor, horizontalConveyor, shooter, SHOT_RPM, 5),

            new DriveDistanceCommand(chassis, .2, .05));
    }
}
