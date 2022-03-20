package com.gos.rapidreact.auto_modes;


import com.gos.rapidreact.commands.CollectorPivotPIDCommand;
import com.gos.rapidreact.commands.autonomous.IntakeWithHorizontal;
import com.gos.rapidreact.commands.autonomous.ShootWithBothIntakes;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import com.gos.rapidreact.trajectory.FourBallTrajectories;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.rapidreact.subsystems.ShooterSubsystem.TARMAC_EDGE_RPM;

public class TwoBallAutoNewCommandGroup extends SequentialCommandGroup {
    private static final double FIRST_SHOT_RPM = TARMAC_EDGE_RPM;
    private static final double PIVOT_ANGLE_DOWN = 7;

    public TwoBallAutoNewCommandGroup(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor,
                                      HorizontalConveyorSubsystem horizontalConveyor, CollectorSubsystem collector) {
        // Drive to the second ball, while lowering the shooter.
        super(
            FourBallTrajectories.fourBallPart1(chassis)
                .alongWith(new CollectorPivotPIDCommand(collector, PIVOT_ANGLE_DOWN))
                // Run the intake the entire time, while we wait for the previous commands to finish
                .raceWith(new IntakeWithHorizontal(collector, horizontalConveyor, 999)),
            FourBallTrajectories.fourBallPart2(chassis),
            new ShootWithBothIntakes(verticalConveyor, horizontalConveyor, shooter, FIRST_SHOT_RPM, 5)
                .alongWith(new CollectorPivotPIDCommand(collector, CollectorSubsystem.UP_ANGLE)));

    }
}
