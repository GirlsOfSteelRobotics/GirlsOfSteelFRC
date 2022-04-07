package com.gos.rapidreact.auto_modes;

import com.gos.rapidreact.commands.CollectorPivotPIDCommand;
import com.gos.rapidreact.commands.HorizontalConveyorBackwardCommand;
import com.gos.rapidreact.commands.TurnToAngleCommand;
import com.gos.rapidreact.commands.autonomous.IntakeWithHorizontal;
import com.gos.rapidreact.commands.autonomous.ShootWithBothIntakes;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import com.gos.rapidreact.trajectory.FourBallTrajectories;
import com.gos.rapidreact.trajectory.SabotageTrajectories;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.rapidreact.subsystems.ShooterSubsystem.TARMAC_EDGE_RPM_HIGH;

public class SabotageAutoUpperCommandGroup extends SequentialCommandGroup {
    private static final double SHOT_RPM = TARMAC_EDGE_RPM_HIGH;
    private static final double PIVOT_ANGLE_DOWN = 7;

    public SabotageAutoUpperCommandGroup(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor,
                                         HorizontalConveyorSubsystem horizontalConveyor, CollectorSubsystem collector) {
        super(SabotageTrajectories.sabotageHighPart1(chassis)
                .alongWith(new CollectorPivotPIDCommand(collector, PIVOT_ANGLE_DOWN))
                // Run the intake the entire time, while we wait for the previous commands to finish
                .raceWith(new IntakeWithHorizontal(collector, horizontalConveyor, 999)),
            new ShootWithBothIntakes(verticalConveyor, horizontalConveyor, shooter, SHOT_RPM, 1.5),
            new TurnToAngleCommand(chassis, SabotageTrajectories.getPartTwoStartAngle(chassis)),
             SabotageTrajectories.sabotageHighPart2(chassis)
                 .raceWith(new IntakeWithHorizontal(collector, horizontalConveyor, 999)),
             SabotageTrajectories.sabotageHighPart3(chassis),
            new ShootWithBothIntakes(verticalConveyor, horizontalConveyor, shooter, 500, 5));
            //new HorizontalConveyorBackwardCommand(horizontalConveyor).withTimeout(5));
        System.out.println(-SabotageTrajectories.getPartTwoStartAngle(chassis));

    }
}
