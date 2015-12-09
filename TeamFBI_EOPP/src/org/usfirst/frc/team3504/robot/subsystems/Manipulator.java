package org.usfirst.frc.team3504.robot.subsystems;


	import org.usfirst.frc.team3504.robot.RobotMap;
	import org.usfirst.frc.team3504.robot.commands.ManipulatorCommand;

	import edu.wpi.first.wpilibj.Joystick;
	import edu.wpi.first.wpilibj.RobotDrive;
	import edu.wpi.first.wpilibj.command.Subsystem;
	import edu.wpi.first.wpilibj.*;

	/**
	 *
	 */
	public class Manipulator extends Subsystem {
	    SpeedController conveyorBeltMotorRight = RobotMap.conveyorBeltMotorRight;
	    SpeedController conveyorBeltMotorLeft = RobotMap.conveyorBeltMotorLeft;
	    		
	    // Put methods for controlling this subsystem
	    // here. Call these from Commands.

	    public void initDefaultCommand() {
	        // Set the default command for a subsystem here.
	        //setDefaultCommand(new MySpecialCommand());
	    	setDefaultCommand(new ManipulatorCommand());
	    }
	    
	    public void manipulatorConveyorBeltMotorRight (boolean fwd) {
	    	if (fwd) {
	    	    conveyorBeltMotorRight.set(1.0);	
	    	}
	    	else{
	    		conveyorBeltMotorRight.set(-1.0);
	    	}
	    	
	    }
	    
	    public void manipulatorConveyorBeltMotorLeft (boolean fwd) {
	    	if (fwd) {
	    	    conveyorBeltMotorLeft.set(1.0);	
	    	}
	    	else{
	    		conveyorBeltMotorLeft.set(-1.0);
	    	}
	    	
	    }
	    
	    public void stopConveyorBeltMotor() {
	    	conveyorBeltMotorRight.set(0.0);
	    	conveyorBeltMotorLeft.set(0.0);
	    }

	}


