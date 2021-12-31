package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.commands.CombinedShootGear;
import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.DriveByMotionProfile;
import org.usfirst.frc.team3504.robot.commands.DriveByVision;
import org.usfirst.frc.team3504.robot.commands.TimeDelay;
import org.usfirst.frc.team3504.robot.commands.TurnByDistance;
import org.usfirst.frc.team3504.robot.commands.TurnToGear.Direction;
import org.usfirst.frc.team3504.robot.subsystems.Agitator;
import org.usfirst.frc.team3504.robot.subsystems.Camera;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Loader;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;
import org.usfirst.frc.team3504.robot.subsystems.Shooter;

/**
 *
 */
public class AutoBoilerGearAndShoot extends CommandGroup {

    public AutoBoilerGearAndShoot(Chassis chassis, Shifters shifters, Agitator agitator, Shooter shooter, Loader loader, Camera camera, double distance, Direction direction) {

        addSequential(new DriveByDistance(chassis, shifters, distance, Shifters.Speed.kLow));
        if (direction == Direction.kLeft) {
            addSequential(new DriveByMotionProfile(chassis, "/home/lvuser/shortTurn.dat", "/home/lvuser/longTurn.dat", 1.0));
        } else if (direction == Direction.kRight) {
            addSequential(new DriveByMotionProfile(chassis, "/home/lvuser/longTurn.dat", "/home/lvuser/shortTurn.dat", 1.0));
        }
        addSequential(new DriveByVision(chassis, camera));
        addSequential(new DriveByMotionProfile(chassis, "/home/lvuser/BackupFourInches.dat", "/home/lvuser/BackupFourInches.dat", 1.0));

        if (direction == Direction.kLeft) {
            addParallel(new TurnByDistance(chassis, shifters, -8.0, 3.0, Shifters.Speed.kLow));
        } else if (direction == Direction.kRight) {
            addParallel(new TurnByDistance(chassis, shifters, 3.0, -2.0, Shifters.Speed.kLow));
        }

        addSequential(new TimeDelay(1.5));
        addSequential(new CombinedShootGear(agitator, shooter, loader));
    }
}
