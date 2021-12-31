package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.DriveByVision;
import org.usfirst.frc.team3504.robot.subsystems.Camera;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;

/**
 *
 */
public class AutoCenterGear extends CommandGroup {

    public AutoCenterGear(Chassis chassis, Shifters shifters, Camera camera) {
        addSequential(new DriveByVision(chassis, camera));
        addSequential(new DriveByDistance(chassis, shifters, -3.0, Shifters.Speed.kLow));
    }
}
