package org.usfirst.frc.team3504.robot.subsystems;
import org.usfirst.frc.team3504.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3504.robot.subsystems.DriveTrain;

import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
	
	private WPI_TalonSRX masterTalon;
	private WPI_TalonSRX slaveTalon1;
	private WPI_TalonSRX slaveTalon2; 

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public DriveTrain() {
		masterTalon = new WPI_TalonSRX(RobotMap.DRIVE_MASTER_PORT);
		slaveTalon1 = new WPI_TalonSRX(RobotMap.DRIVE_SLAVE_PORT_ONE);
		slaveTalon2 = new WPI_TalonSRX(RobotMap.DRIVE_SLAVE_PORT_TWO); 
		
		
	}
	
	public void forward() {
		masterTalon.set(0.8); 
	}

	// spins the motor backward
	public void backward() {
		masterTalon.set(-0.8);
	}

	// stops spinning the motor
	public void stop() {
		masterTalon.set(0);
	}


    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
