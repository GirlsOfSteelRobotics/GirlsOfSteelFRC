package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import com.gos.aerial_assist.subsystems.Chassis;
import com.gos.aerial_assist.subsystems.Driving;

/**
 * @author arushibandi
 */
public class AutonomousMobility extends SequentialCommandGroup {

    public AutonomousMobility(Chassis chassis, Driving driving) {
        addCommands(new MoveToPositionLSPB(chassis, driving, 2.5)); //should move 2.5 meters forward
    }
}
