package org.usfirst.frc.team3504.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import java.io.IOException;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class GearCamera extends Subsystem {

	
	private final NetworkTable grip = NetworkTable.getTable("grip");
	
	public void testCamera(){
		try {
			new ProcessBuilder("/users/rfero/GearLiftVision").inheritIO().start();
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

