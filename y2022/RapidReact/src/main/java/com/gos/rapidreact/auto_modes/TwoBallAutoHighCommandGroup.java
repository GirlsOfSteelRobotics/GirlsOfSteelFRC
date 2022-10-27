package com.gos.rapidreact.auto_modes;

import com.gos.rapidreact.commands.AutoNoLimelightConveyorAndShooterCommand;
import com.gos.rapidreact.commands.CollectorPivotPIDCommand;
import com.gos.rapidreact.commands.HorizontalConveyorForwardCommand;
import com.gos.rapidreact.commands.ShooterRpmPIDCommand;
import com.gos.rapidreact.commands.autonomous.IntakeWithHorizontal;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import com.gos.rapidreact.trajectory.TwoBallTrajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class TwoBallAutoHighCommandGroup extends SequentialCommandGroup {
    private static final double PIVOT_ANGLE_DOWN = CollectorSubsystem.DOWN_ANGLE_AUTO;

    public TwoBallAutoHighCommandGroup(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor,
                                       HorizontalConveyorSubsystem horizontalConveyor, CollectorSubsystem collector) {
        super(TwoBallTrajectory.twoBallHighPart1(chassis)
                .alongWith(new CollectorPivotPIDCommand(collector, PIVOT_ANGLE_DOWN))
                // Run the intake the entire time, while we wait for the previous commands to finish
                .alongWith(new ShooterRpmPIDCommand(shooter, ShooterSubsystem.TARMAC_EDGE_RPM_HIGH))
                .raceWith(new IntakeWithHorizontal(collector, horizontalConveyor, 999)),
            new WaitCommand(.5),
            new AutoNoLimelightConveyorAndShooterCommand(shooter, verticalConveyor).alongWith(new HorizontalConveyorForwardCommand(horizontalConveyor).withTimeout(7)
                .alongWith(new CollectorPivotPIDCommand(collector, CollectorSubsystem.UP_ANGLE))));
            // new ShootWithBothIntakes(verticalConveyor, horizontalConveyor, shooter, SHOT_RPM, 5),

            // new DriveDistanceCommand(chassis, .2, .05)));
    }
}
