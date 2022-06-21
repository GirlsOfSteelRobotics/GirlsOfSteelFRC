package com.gos.stronghold.robot.commands.buttons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.stronghold.robot.commands.Drive;
import com.gos.stronghold.robot.commands.FlapDown;
import com.gos.stronghold.robot.subsystems.Chassis;
import com.gos.stronghold.robot.subsystems.Flap;

/**
 *
 */
public class ChevalDeFrise extends SequentialCommandGroup {

    public ChevalDeFrise(Chassis chassis, Flap flap) {
        addCommands(new FlapDown(flap));
        addCommands(new Drive(chassis, 10, 0)); //FIXME: correct values
    }
}
