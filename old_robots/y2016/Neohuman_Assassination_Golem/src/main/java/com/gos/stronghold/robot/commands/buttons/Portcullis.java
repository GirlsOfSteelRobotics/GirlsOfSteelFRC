package com.gos.stronghold.robot.commands.buttons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.stronghold.robot.commands.Drive;
import com.gos.stronghold.robot.subsystems.Chassis;

/**
 *
 */
public class Portcullis extends SequentialCommandGroup {

    public Portcullis(Chassis chassis) {
        //addCommands(new FlapUp());
        addCommands(new Drive(chassis, 5, 0)); //FIXME: need real values
    }
}
