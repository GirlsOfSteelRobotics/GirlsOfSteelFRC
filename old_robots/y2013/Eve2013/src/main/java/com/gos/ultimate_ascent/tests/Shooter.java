package com.gos.ultimate_ascent.tests;

import com.gos.ultimate_ascent.subsystems.Feeder;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Shooter extends CommandGroup {

    public Shooter(Feeder feeder, com.gos.ultimate_ascent.subsystems.Shooter shooter) {
        addSequential(new ShooterJags(feeder, shooter));
        addSequential(new ShooterPID(shooter));
    }

}
