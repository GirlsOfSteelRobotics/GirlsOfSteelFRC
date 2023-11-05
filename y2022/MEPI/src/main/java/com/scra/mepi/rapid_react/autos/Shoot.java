package com.scra.mepi.rapid_react.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.scra.mepi.rapid_react.commands.ShooterPIDCommand;
import com.scra.mepi.rapid_react.subsystems.ShooterSubsytem;

public class Shoot extends SequentialCommandGroup {
    public Shoot(ShooterSubsytem shooterSubsytem) {
        addCommands(new ShooterPIDCommand(shooterSubsytem));
    }
}
