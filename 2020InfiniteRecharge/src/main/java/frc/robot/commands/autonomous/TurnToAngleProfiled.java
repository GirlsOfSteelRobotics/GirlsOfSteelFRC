package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;

import frc.robot.lib.PropertyManager;
import frc.robot.subsystems.Chassis;

public class TurnToAngleProfiled extends ProfiledPIDCommand {

    private static final PropertyManager.IProperty<Double> AUTO_KP = new PropertyManager.DoubleProperty("TurnToAngleKp", 0.05);

    private final Chassis m_chassis;
    private final double m_angle;
    private final double m_allowableError;

    private double m_error;

    public TurnToAngleProfiled(double targetAngleDegrees, Chassis chassis) {
        super(
            new ProfiledPIDController(DriveConstants.kTurnP, DriveConstants.kTurnI, DriveConstants.kTurnD, new TrapezoidProfile.Constraints(
            ChassisSpeeds.kMaxTurnRateDegPerS,
            ChassisSpeeds.kMaxTurnAccelerationDegPerSSquared)),
        // Close loop on heading
        drive::getHeading,
        // Set reference to target
        targetAngleDegrees,
        // Pipe output to turn robot
        (output, setpoint) -> drive.arcadeDrive(0, output),
        // Require the drive
        drive);
       
       
       
    
        addRequirements(chassis);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute() {
        double currentAngle = m_chassis.getHeading();
        m_error = m_angle - currentAngle;

        double turnSpeed = m_error * AUTO_KP.getValue();
        m_chassis.setSpeedAndSteer(0, turnSpeed);

        //System.out.println("error:" + m_error + "speed:" + speed);
    }

    @Override
    public boolean isFinished() {
        if (Math.abs(m_error) < m_allowableError) {
            System.out.println("Done!");
            return true;
        }
        else {
            System.out.println("Turn to angle" + "error:" + m_error + "allowableError" + m_allowableError);
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.setSpeed(0);
    }
}
