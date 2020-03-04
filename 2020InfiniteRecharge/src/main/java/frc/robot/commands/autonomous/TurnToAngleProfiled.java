package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.lib.PropertyManager;
import frc.robot.subsystems.Chassis;

public class TurnToAngleProfiled extends ProfiledPIDCommand {

    private static final PropertyManager.IProperty<Double> AUTO_KP = new PropertyManager.DoubleProperty("TurnToAngleKp", 0.05);

    // private final Chassis m_chassis;
    // private final double m_angle;
    // private final double m_allowableError;

    private double m_error;

    public TurnToAngleProfiled(double targetAngleDegrees, Chassis chassis) {
        super(
            new ProfiledPIDController(Constants.kTurnP, Constants.kTurnI,
            Constants.kTurnD, new TrapezoidProfile.Constraints(
                Constants.kMaxTurnRateDegPerS,
                Constants.kMaxTurnAccelerationDegPerSSquared)),
            // Close loop on heading
            chassis::getHeading,
            // Set reference to target
            targetAngleDegrees,
            // Pipe output to turn robot
            (output, setpoint) -> chassis.setSteer(output),
            // Require the drive
            chassis);
    
        // Set the controller to be continuous (because it is an angle controller)
        getController().enableContinuousInput(-180, 180);
        // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
        // setpoint before it is considered as having reached the reference
        getController()
            .setTolerance(Constants.kTurnToleranceDeg, Constants.kTurnRateToleranceDegPerS);
      }
    
      @Override
      public boolean isFinished() {
        // End when the controller is at the reference.
        return getController().atGoal();
      }
    }