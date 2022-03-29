package com.gos.rapidreact.auto_modes;

import com.gos.rapidreact.commands.autonomous.ShootWithBothIntakes;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.HorizontalConveyorSubsystem;
import com.gos.rapidreact.subsystems.ShooterSubsystem;
import com.gos.rapidreact.subsystems.VerticalConveyorSubsystem;
import com.gos.rapidreact.trajectory.FourBallTrajectories;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import static com.gos.rapidreact.subsystems.ShooterSubsystem.TARMAC_EDGE_RPM_HIGH;

public class OneBallAutoHighCommandGroup extends SequentialCommandGroup {
    private static final double SHOT_RPM = TARMAC_EDGE_RPM_HIGH;

    public OneBallAutoHighCommandGroup(ChassisSubsystem chassis, ShooterSubsystem shooter, VerticalConveyorSubsystem verticalConveyor,
                                      HorizontalConveyorSubsystem horizontalConveyor) {
        super(FourBallTrajectories.fourBallPart1(chassis),
            new ShootWithBothIntakes(verticalConveyor, horizontalConveyor, shooter, SHOT_RPM, 5));
    }

}
