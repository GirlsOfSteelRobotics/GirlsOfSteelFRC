package com.gos.reefscape.commands;

import com.gos.lib.properties.GosDoubleProperty;
import com.gos.reefscape.Constants;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class RobotRelativeDriveCommand extends Command {
    private static final GosDoubleProperty TRANSLATION_DAMPER = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "ChassisRobotRelativeDamper", 0.15);

    private final ChassisSubsystem m_chassis;
    private final CommandXboxController m_joystick;

    public RobotRelativeDriveCommand(ChassisSubsystem chassisSubsystem, CommandXboxController joystick) {
        this.m_chassis = chassisSubsystem;
        m_joystick = joystick;
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements(this.m_chassis);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        m_chassis.robotDriveWithJoystick(
            MathUtil.applyDeadband(-m_joystick.getRightY() * TRANSLATION_DAMPER.getValue(), .01),
            MathUtil.applyDeadband(-m_joystick.getRightX() * TRANSLATION_DAMPER.getValue(), .01),
            0);


    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.driveWithJoystick(0, 0, 0);
    }
}


