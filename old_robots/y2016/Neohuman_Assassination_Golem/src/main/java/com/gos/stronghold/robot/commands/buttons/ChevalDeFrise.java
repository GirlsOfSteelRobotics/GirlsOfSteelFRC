package com.gos.stronghold.robot.commands.buttons;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.stronghold.robot.commands.Drive;
import com.gos.stronghold.robot.commands.FlapDown;
import com.gos.stronghold.robot.subsystems.Chassis;
import com.gos.stronghold.robot.subsystems.Flap;

/**
 *
 */
public class ChevalDeFrise extends CommandGroup {

    public ChevalDeFrise(Chassis chassis, Flap flap) {
        addSequential(new FlapDown(flap));
        addSequential(new Drive(chassis, 10, 0)); //FIXME: correct values
    }
}
