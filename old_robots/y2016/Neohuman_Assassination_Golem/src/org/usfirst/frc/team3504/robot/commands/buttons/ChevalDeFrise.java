package org.usfirst.frc.team3504.robot.commands.buttons;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.commands.Drive;
import org.usfirst.frc.team3504.robot.commands.FlapDown;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Flap;

/**
 *
 */
public class ChevalDeFrise extends CommandGroup {

    public ChevalDeFrise(Chassis chassis, Flap flap) {
        addSequential(new FlapDown(flap));
        addSequential(new Drive(chassis, 10, 0)); //FIXME: correct values
    }
}
