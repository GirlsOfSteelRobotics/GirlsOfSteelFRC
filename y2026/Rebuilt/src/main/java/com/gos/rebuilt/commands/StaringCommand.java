package com.gos.rebuilt.commands;

import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rebuilt.Constants;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import org.littletonrobotics.frc2026.FieldConstants.Hub;


public class StaringCommand extends Command {
    private static final GosDoubleProperty TRANSLATION_DAMPER = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "ChassisTranslationDamper", 0.5);

    private final ChassisSubsystem m_chassis;
    private final CommandXboxController m_joystick;

    public StaringCommand(ChassisSubsystem chassis, CommandXboxController driverJoystick) {
        m_chassis = chassis;
        m_joystick = driverJoystick;

        addRequirements(m_chassis);
    }


    @Override
    public void execute() {

        m_chassis.staringDrive(
            MathUtil.applyDeadband(-m_joystick.getLeftY() * TRANSLATION_DAMPER.getValue(), .05),
            MathUtil.applyDeadband(-m_joystick.getLeftX() * TRANSLATION_DAMPER.getValue(), .05),
            m_chassis.getFaceAngle(Hub.innerCenterPoint.toTranslation2d())
        );



    }
}
