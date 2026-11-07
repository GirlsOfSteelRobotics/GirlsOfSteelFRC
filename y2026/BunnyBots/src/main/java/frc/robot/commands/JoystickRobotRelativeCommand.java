package frc.robot.commands;

import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ChassisSubsystem;

public class JoystickRobotRelativeCommand extends Command {
    private static final GosDoubleProperty TRANSLATION_DAMPER = new GosDoubleProperty(false, "ChassisTranslaatoinDamper", 0.9);
    private static final GosDoubleProperty ROTATIONAL_DAMPER = new GosDoubleProperty(false, "ChasisRotationDamper", .5);

    private final ChassisSubsystem m_chassis;
    private final CommandXboxController m_joystick;

    public JoystickRobotRelativeCommand(ChassisSubsystem chassis, CommandXboxController driverJoystick) {
        m_chassis = chassis;
        m_joystick = driverJoystick;

        addRequirements(m_chassis);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        m_chassis.driveRobotCentric(
            MathUtil.applyDeadband(-m_joystick.getLeftY() * TRANSLATION_DAMPER.getValue(), .05),
            MathUtil.applyDeadband(-m_joystick.getLeftX() * TRANSLATION_DAMPER.getValue(), .05),
            MathUtil.applyDeadband(-m_joystick.getRightX() * ROTATIONAL_DAMPER.getValue(), .05)
        );
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.driveRobotCentric(0, 0, 0);
    }


}
