package com.gos.stronghold.robot.commands.buttons;

import edu.wpi.first.wpilibj.command.CommandGroup;
import com.gos.stronghold.robot.commands.Drive;
import com.gos.stronghold.robot.subsystems.Chassis;

/**
 *
 */
public class Portcullis extends CommandGroup {

    public Portcullis(Chassis chassis) {
        //addSequential(new FlapUp());
        addSequential(new Drive(chassis, 5, 0)); //FIXME: need real values
    }
}
