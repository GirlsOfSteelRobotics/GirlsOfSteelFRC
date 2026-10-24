package frc.robot.commands;

import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ChassisSubsystem;

public class DavidDriveCommand extends Command {
    private static final GosDoubleProperty TRANSLATION_DAMPER = new GosDoubleProperty(false, "ChassisTranslationDamper", 0.9);

    private static final GosDoubleProperty kp = new GosDoubleProperty(false, "DavidDrivekp", 0);
    private final ChassisSubsystem m_chassis;
    private final CommandXboxController m_joystick;

    public DavidDriveCommand(ChassisSubsystem chassis, CommandXboxController driverJoystick) {
        m_chassis = chassis;
        m_joystick = driverJoystick;

        addRequirements(m_chassis);
    }
    public void execute() {
        double angle = Math.atan2(-m_joystick.getRightY(), -m_joystick.getRightX());
        //P conorller formula -> out = error *kp
        double error = angle - m_chassis.getState().Pose.getRotation().getRadians();
        double output_error = error * kp.getValue();
        m_chassis.driveFieldCentric(
            MathUtil.applyDeadband(-m_joystick.getLeftY() * TRANSLATION_DAMPER.getValue(), .05),
            MathUtil.applyDeadband(-m_joystick.getLeftX() * TRANSLATION_DAMPER.getValue(), .05),
            output_error);
    }
}
