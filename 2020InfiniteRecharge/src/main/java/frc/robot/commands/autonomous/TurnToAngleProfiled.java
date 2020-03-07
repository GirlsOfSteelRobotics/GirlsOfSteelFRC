package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.lib.PropertyManager;
import frc.robot.subsystems.Chassis;

public class TurnToAngleProfiled extends ProfiledPIDCommand {

    private static final PropertyManager.IProperty<Double> AUTO_KP = new PropertyManager.DoubleProperty(
            "TurnToAngleProfiledKp", 0.05);
    private static final PropertyManager.IProperty<Double> AUTO_KI = new PropertyManager.DoubleProperty(
            "TurnToAngleProfiledKi", 0.05);
    private static final PropertyManager.IProperty<Double> AUTO_KD = new PropertyManager.DoubleProperty(
            "TurnToAngleProfiledKd", 0.05);
    private static final PropertyManager.IProperty<Double> MAX_TURN_RATE_DEG_PER_SEC = new PropertyManager.DoubleProperty(
            "MaxTurnRateDegPerSec", Constants.kMaxTurnRateDegPerS);
    private static final PropertyManager.IProperty<Double> MAX_TURN_ACCELERATION_DEG_PER_SEC_SQUARED = new PropertyManager.DoubleProperty(
            "MaxTurnAccelerationDegPerSecSquared", Constants.kMaxTurnAccelerationDegPerSSquared);

    // private final Chassis m_chassis;
    // private final double m_angle;
    // private final double m_allowableError;

    public TurnToAngleProfiled(double targetAngleDegrees, Chassis chassis) {
        super(new ProfiledPIDController(Constants.kTurnP, Constants.kTurnI, Constants.kTurnD,
                new TrapezoidProfile.Constraints(MAX_TURN_RATE_DEG_PER_SEC.getValue(),
                        MAX_TURN_ACCELERATION_DEG_PER_SEC_SQUARED.getValue())),
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
        // Set the controller tolerance - the delta tolerance ensures the robot is
        // stationary at the
        // setpoint before it is considered as having reached the reference

        getController().setTolerance(Constants.kTurnToleranceDeg, Constants.kTurnRateToleranceDegPerS);
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        super.execute();

        getController().setP(AUTO_KP.getValue());
        getController().setI(AUTO_KI.getValue());
        getController().setD(AUTO_KD.getValue());

        getController().setConstraints(new TrapezoidProfile.Constraints(MAX_TURN_RATE_DEG_PER_SEC.getValue(),
                MAX_TURN_ACCELERATION_DEG_PER_SEC_SQUARED.getValue()));

    }

    @Override
    public boolean isFinished() {
        // End when the controller is at the reference.
        return getController().atGoal();
    }
}