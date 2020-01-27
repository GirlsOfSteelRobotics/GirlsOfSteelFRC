package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.Conveyor;
import frc.robot.commands.RunShooterRPM;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterConveyor;

public class AutoShoot extends ParallelCommandGroup{
	Command autonomousCommand;
	Shooter shooter;
	ShooterConveyor shooterConveyor;

	private double m_rpm;
	private double m_time;

	public AutoShoot(Shooter shooter, ShooterConveyor shooterConveyor, double rpm, double time) {
		this.shooter = shooter;
		this.shooterConveyor = shooterConveyor;
		m_rpm = rpm;
		m_time = time;
		//requires(Robot.Shooter);
		// Use requires() here to declare subsystem dependencies
		//super.addRequirements(shooter); 
		//super.addRequirements(shooterConveyor);

		super.addCommands(new RunShooterRPM(shooter, rpm).withTimeout(time));
		super.addCommands(new Conveyor(shooterConveyor, true).withTimeout(time));

	}
}