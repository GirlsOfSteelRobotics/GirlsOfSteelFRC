package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Collector;
import org.usfirst.frc.team3504.robot.subsystems.Lifter;
import org.usfirst.frc.team3504.robot.subsystems.Shack;

/**
 *
 */
public class AutoOneTote extends CommandGroup {

    // Collects one tote/container and takes it to the auto zone
    private final double m_distanceRight1;

    public AutoOneTote(Chassis chassis, Collector collector, Shack shack, Lifter lifter) {
        m_distanceRight1 = 50; // 107 (50 being used for testing)

        addSequential(new AutoCollector(collector));
        addSequential(new Lifting(shack, collector, lifter));
        addSequential(new AutoDriveRight(chassis, m_distanceRight1));
        addSequential(new Release(shack, collector));
    }
}
