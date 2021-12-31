package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.commands.CombinedShootKey;
import org.usfirst.frc.team3504.robot.subsystems.Agitator;
import org.usfirst.frc.team3504.robot.subsystems.Loader;
import org.usfirst.frc.team3504.robot.subsystems.Shooter;

/**
 *
 */
public class AutoShooter extends CommandGroup {

    public AutoShooter(Agitator agitator, Shooter shooter, Loader loader) {
        addSequential(new CombinedShootKey(agitator, shooter, loader));
    }
}
