package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

import org.usfirst.frc.team3504.robot.subsystems.Lights;

import com.mindsensors.CANLight;

public class Robot extends IterativeRobot {
    
    public static Lights lights;
    
    public void robotInit() {
       lights = new Lights();
    } 
}
