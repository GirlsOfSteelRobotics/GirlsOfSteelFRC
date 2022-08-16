package com.gos.ultimate_ascent.tests;

import com.gos.ultimate_ascent.subsystems.Feeder;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Shooter extends SequentialCommandGroup {

    public Shooter(Feeder feeder, com.gos.ultimate_ascent.subsystems.Shooter shooter) {
        addCommands(new ShooterJags(feeder, shooter));
        addCommands(new ShooterPID(shooter));
    }

}
