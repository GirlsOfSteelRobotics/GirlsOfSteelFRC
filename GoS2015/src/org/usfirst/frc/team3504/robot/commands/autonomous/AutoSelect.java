package org.usfirst.frc.team3504.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team3504.robot.Robot;

/**
 *
 */
public class AutoSelect extends CommandGroup {
    
	
    public  AutoSelect() {
    	Joystick autoJoystick = Robot.oi.getAutonomousJoystick();
    	if (autoJoystick.getRawButton(1)){
    		autoJoystick.setOutput(1, true);
    		addSequential(new AutoDriveForward(6));
    	} else if (autoJoystick.getRawButton(2)){
    		autoJoystick.setOutput(2, true);
    		addSequential(new AutoDriveBackwards());
    	} else if (autoJoystick.getRawButton(3)){
    		autoJoystick.setOutput(3, true);
    		addSequential(new AutoDriveLeft());
    	} else if (autoJoystick.getRawButton(4)){
    		autoJoystick.setOutput(4, true);
    		addSequential(new AutoDriveRight());
    	} else if (autoJoystick.getRawButton(5)){
    		autoJoystick.setOutput(5, true);
    		addSequential(new AutoPlow());
    	} else if (autoJoystick.getRawButton(6)){
    		autoJoystick.setOutput(6, true);
    		addSequential(new AutoUltrasonic());
    	} else {
    		autoJoystick.setOutput(1, true);
    		autoJoystick.setOutput(2, true);
    		autoJoystick.setOutput(3, true);
    		autoJoystick.setOutput(4, true);
    		autoJoystick.setOutput(5, true);
    		autoJoystick.setOutput(6, true);
    		addSequential(new AutoDriveForward(1));
    	}
    
    	
    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
