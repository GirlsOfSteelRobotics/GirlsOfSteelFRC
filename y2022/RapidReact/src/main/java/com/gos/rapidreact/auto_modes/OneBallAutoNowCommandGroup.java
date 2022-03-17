package com.gos.rapidreact.auto_modes;


import com.gos.rapidreact.commands.autonomous.ShootWithBothIntakes;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.CollectorSubsystem;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import com.gos.rapidreact.trajectory.FourBallTrajectories;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.gos.rapidreact.subsystems.ShooterSubsystem.TARMAC_EDGE_RPM;

public class OneBallAutoNowCommandGroup extends SequentialCommandGroup {
    private static final double FIRST_SHOT_RPM = TARMAC_EDGE_RPM;

    public OneBallAutoNowCommandGroup(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor,
                                      HorizontalConveyorSubsystem horizontalConveyor, CollectorSubsystem collector) {
        super(
            new ShootWithBothIntakes(verticalConveyor, horizontalConveyor, shooter, FIRST_SHOT_RPM, 5),
            FourBallTrajectories.fourBallPart1(chassis));
    }
}