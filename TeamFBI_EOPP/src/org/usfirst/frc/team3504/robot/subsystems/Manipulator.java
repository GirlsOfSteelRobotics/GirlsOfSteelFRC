package org.usfirst.frc.team3504.robot.subsystems;

//public class Manipulator {

	import org.usfirst.frc.team3504.robot.RobotMap;
	import org.usfirst.frc.team3504.robot.commands.ManipulatorCommand;

	import edu.wpi.first.wpilibj.Joystick;
	import edu.wpi.first.wpilibj.RobotDrive;
	import edu.wpi.first.wpilibj.command.Subsystem;

	/**
	 *
	 */
	public class Manipulator extends Subsystem {
	    
	    // Put methods for controlling this subsystem
	    // here. Call these from Commands.

	    public void initDefaultCommand() {
	        // Set the default command for a subsystem here.
	        //setDefaultCommand(new MySpecialCommand());
	    	setDefaultCommand(new ManipulatorCommand());
	    }
	    
	    public void manipulatorConveyorBeltMotorRight (Joystick joystick) {
	    	if (fwd) {
	    	    conveyorBeltMotorRight.set(1.0);	
	    	}
	    	else{
	    		conveyorBeltMotorRight.set(-1.0);
	    	}
	    	
	    }
	    
	    public void manipulatorConveyorBeltMotorLeft (Joystick joystick) {
	    	if (fwd) {
	    	    conveyorBeltMotorLeft.set(1.0);	
	    	}
	    	else{
	    		conveyorBeltMotorLeft.set(-1.0);
	    	}
	    	
	    }
	    
	    public void stop(Joystick joystick) {
	    	robotDrive.drive(0,0);
	    }
	}


}
