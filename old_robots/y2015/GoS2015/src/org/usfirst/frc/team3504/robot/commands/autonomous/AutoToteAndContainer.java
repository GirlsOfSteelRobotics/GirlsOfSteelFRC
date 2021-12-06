package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Collector;
import org.usfirst.frc.team3504.robot.subsystems.Lifter;
import org.usfirst.frc.team3504.robot.subsystems.Shack;

/**
 *
 */
public class AutoToteAndContainer extends CommandGroup {

    // collects one tote and one container and takes them to the auto zone
    private double m_distanceLeft1;
    private double m_distanceFirst1;

    public AutoToteAndContainer(Chassis chassis, Shack shack, Collector collector, Lifter lifter) {
        addSequential(new AutoCollector(collector));
        addSequential(new Lifting(shack, collector, lifter));
        addSequential(new AutoFirstPickup(chassis, m_distanceFirst1));
        addSequential(new AutoCollector(collector));
        addSequential(new Lifting(shack, collector, lifter));
        addSequential(new AutoDriveLeft(chassis, m_distanceLeft1));
    }
}
