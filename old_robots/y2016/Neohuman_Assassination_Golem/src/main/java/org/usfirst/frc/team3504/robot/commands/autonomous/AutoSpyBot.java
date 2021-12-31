package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team3504.robot.commands.Drive;
import org.usfirst.frc.team3504.robot.commands.PivotDown;
import org.usfirst.frc.team3504.robot.commands.PivotUp;
import org.usfirst.frc.team3504.robot.commands.ReleaseBall;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Claw;
import org.usfirst.frc.team3504.robot.subsystems.Pivot;
import org.usfirst.frc.team3504.robot.subsystems.Shooter;

/**
 *
 */
public class AutoSpyBot extends CommandGroup {

    public AutoSpyBot(Chassis chassis, Pivot pivot, Claw claw, Shooter shooter) {
        addSequential(new Drive(chassis, 132, 0));  //Drive x inches until across from goal. Goal should be on right side (possibly) of robot.
        addSequential(new Drive(chassis, 10, 0));  //TO DO: Change Drive. This line should turn the robot 90 degrees.
        addSequential(new Drive(chassis, 160, 0)); //Robot drives halfway across field
        addSequential(new Drive(chassis, 10, 0));   //TO DO: Turn the robot. If we turn the robot more than 90 degrees the first time, this line may be irrelevant.
        addSequential(new Drive(chassis, 60, 0));  //drive completely to goal.
        addSequential(new PivotDown(pivot)); //The pivot goes down.
        addSequential(new ReleaseBall(claw, shooter)); //The claws open and the ball is released.
        addSequential(new PivotUp(pivot)); //The pivot goes back up.
    }
}
