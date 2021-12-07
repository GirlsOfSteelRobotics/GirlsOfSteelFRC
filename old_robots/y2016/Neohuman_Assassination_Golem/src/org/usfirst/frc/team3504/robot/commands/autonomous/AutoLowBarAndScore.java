package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.commands.NudgeFlapDown;
import org.usfirst.frc.team3504.robot.commands.NudgeFlapUp;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Claw;
import org.usfirst.frc.team3504.robot.subsystems.Flap;
import org.usfirst.frc.team3504.robot.subsystems.Pivot;

/**
 *
 */
public class AutoLowBarAndScore extends CommandGroup {

    public  AutoLowBarAndScore(Chassis chassis, Flap flap, Pivot pivot, Claw claw) {
        addSequential(new NudgeFlapDown(flap));
        addSequential(new AutoDriveBackwards(chassis, 186, .6));
        addSequential(new NudgeFlapUp(flap));
        addSequential(new AutoTurn(chassis, 21, 0.3));
        addSequential(new AutoDriveForward(chassis, 140, .6));
        addSequential(new AutoPivotDown(pivot, 0.5));
        addSequential(new AutoReleaseBall(claw, 2.0));
    }
}
