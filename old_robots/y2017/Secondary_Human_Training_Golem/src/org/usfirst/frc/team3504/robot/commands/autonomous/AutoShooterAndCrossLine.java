package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.commands.CombinedShootKey;
import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.TimeDelay;
import org.usfirst.frc.team3504.robot.commands.TurnByDistance;
import org.usfirst.frc.team3504.robot.subsystems.Agitator;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Loader;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;
import org.usfirst.frc.team3504.robot.subsystems.Shooter;

/**
 *
 */
public class AutoShooterAndCrossLine extends CommandGroup {

    public AutoShooterAndCrossLine(Chassis chassis, Shifters shifters, Agitator agitator, Shooter shooter, Loader loader) {
        addParallel(new CombinedShootKey(agitator, shooter, loader));
        addSequential(new TimeDelay(10.0));
        addSequential(new TurnByDistance(chassis, shifters, -10.0, -60.0, Shifters.Speed.kLow));
        addSequential(new DriveByDistance(chassis, shifters, -48.0, Shifters.Speed.kLow));
    }
}
