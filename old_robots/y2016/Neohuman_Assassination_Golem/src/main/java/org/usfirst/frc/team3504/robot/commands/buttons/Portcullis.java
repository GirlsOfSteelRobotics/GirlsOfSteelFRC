package org.usfirst.frc.team3504.robot.commands.buttons;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.commands.Drive;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;

/**
 *
 */
public class Portcullis extends CommandGroup {

    public Portcullis(Chassis chassis) {
        //addSequential(new FlapUp());
        addSequential(new Drive(chassis, 5, 0)); //FIXME: need real values
    }
}
