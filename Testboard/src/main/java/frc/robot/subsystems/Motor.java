package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.robot.LidarLitePWM;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.ReadLightSensor;

public class Motor extends Subsystem {

	private WPI_TalonSRX mainMotor;

	private DigitalInput lightSensor;

	private LidarLitePWM lidar;

	private final double speed = 0.5;

	public double LIDAR_TOLERANCE = 1; //tune

	
	public Motor () {
		 
		mainMotor = new WPI_TalonSRX(RobotMap.MAIN_MOTOR_TALON); 

		lightSensor = new DigitalInput(RobotMap.LIGHT_SENSOR_PORT);

		lidar = new LidarLitePWM(RobotMap.LIDAR_DIO);

	}

	// Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
		//setDefaultCommand(new MyCommand());
    }
    

	public boolean darkLightSensor(){
		return lightSensor.get();
	}

	public double getLidarDistance(){
		return lidar.getDistance(); 
	  }
		
	public void motorGo(){
		mainMotor.set(speed);
	}

    public void stop() {
		mainMotor.stopMotor();
    }
}

