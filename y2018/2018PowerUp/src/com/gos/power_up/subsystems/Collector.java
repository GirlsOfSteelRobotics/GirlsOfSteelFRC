package com.gos.power_up.subsystems;

import com.gos.power_up.RobotMap;
import com.gos.power_up.commands.CollectorHold;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Collector extends Subsystem {
	
	private WPI_TalonSRX collectLeft;
	private WPI_TalonSRX collectRight;
	
	public double collectorSpeed;

	public Collector() {
		collectLeft = new WPI_TalonSRX(RobotMap.COLLECT_LEFT);
		collectRight = new WPI_TalonSRX(RobotMap.COLLECT_RIGHT);
		collectLeft.setSensorPhase(true);
		collectRight.setSensorPhase(true);
		collectorSpeed = 0; //TODO do we want it to turn during auto
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new CollectorHold());
    }
    
    
    public void stop() {
    	collectLeft.stopMotor();
    	collectRight.stopMotor();
    	collectorSpeed = 0;
    	System.out.println("Collector: motors stopped");
    }
    
    public void collect() {
		collectLeft.set(-1.00); //TODO: tune this speed, and these values may be reversed
		collectRight.set(0.5);
    }
    
    public void release(double speed) {
		collectLeft.set(speed); //TODO: tune this speed, and these values may be reversed
		collectRight.set(-speed);
    }
    
    public int getRightCollectorID()
    {
    	return collectRight.getDeviceID();
    }
    
    public WPI_TalonSRX getRightCollector()
    {
    	return collectRight;
    }
    
    public void runCollector()
    {
    	collectLeft.set(-collectorSpeed);
    	collectRight.set(collectorSpeed);
    }
    
    public void runSlowCollect()
    {
    	System.out.println("Collector: holding cube in");
    	collectorSpeed = 0.15;
    }
    
}

