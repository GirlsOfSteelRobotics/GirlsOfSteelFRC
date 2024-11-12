package com.scra.mepi.rapid_react.autos;

import com.scra.mepi.rapid_react.commands.ShooterPIDCommand;
import com.scra.mepi.rapid_react.subsystems.ShooterSubsytem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Shoot extends SequentialCommandGroup {
    public Shoot(ShooterSubsytem shooterSubsytem) {
        addCommands(new ShooterPIDCommand(shooterSubsytem));
    }
}
