package frc.robot.commands;

import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.Command;


/**
 * An example command that uses an example subsystem.
 */
public class SwerveDrivetrain extends Command {
    @SuppressWarnings("PMD.UnusedPrivateField")
    private final ChassisSubsystem m_commandSwerveDrivetrain;
    private final CommandXboxController m_joystick;
    private static final GosDoubleProperty TRANSLATION_DAMPER = new GosDoubleProperty(false, "ChassisTranslationDamper", 0.9);
    private static final GosDoubleProperty ROTATIONAL_DAMPER = new GosDoubleProperty(false, "ChassisRotationDamper", .5);

    /**
     * Creates a new ExampleCommand.
     *
     *
     */
    public SwerveDrivetrain(ChassisSubsystem mCommandSwerveDrivetrain, CommandXboxController mJoystick) {
        m_commandSwerveDrivetrain = mCommandSwerveDrivetrain;
        m_joystick = mJoystick;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(m_commandSwerveDrivetrain);

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_commandSwerveDrivetrain.driveFieldCentric(
            MathUtil.applyDeadband(-m_joystick.getLeftY() * TRANSLATION_DAMPER.getValue(), .05),
            MathUtil.applyDeadband(-m_joystick.getLeftX() * TRANSLATION_DAMPER.getValue(), .05),
            MathUtil.applyDeadband(-m_joystick.getRightX() * ROTATIONAL_DAMPER.getValue(), .05));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}

