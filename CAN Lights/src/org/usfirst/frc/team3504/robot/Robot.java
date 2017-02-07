package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

import com.mindsensors.CANLight;

public class Robot extends IterativeRobot {
    
    CANLight lights;
    
    public void robotInit() {
        lights = new CANLight(3);
        
    }
    
    public void disabledPeriodic() {
    	lights.showRGB(255, 0, 0);
    }
    
}
