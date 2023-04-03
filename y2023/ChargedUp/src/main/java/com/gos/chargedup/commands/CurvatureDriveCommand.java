package com.gos.chargedup.commands;

import com.gos.chargedup.subsystems.ChassisSubsystem;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class CurvatureDriveCommand extends CommandBase {
    private static final GosDoubleProperty TURN_DAMPING = new GosDoubleProperty(false, "JoystickTurnDamping", 0.5);
    private static final GosDoubleProperty TURN_IN_PLACE_THRESHOLD = new GosDoubleProperty(false, "JoysticTurnInPlaceThreshold", 0.05);

    private final ChassisSubsystem m_chassis;

    private final CommandXboxController m_joystick;

    public CurvatureDriveCommand(ChassisSubsystem chassisSubsystem, CommandXboxController joystick) {
        m_chassis = chassisSubsystem;
        m_joystick = joystick;
        addRequirements(this.m_chassis);

    }

    @Override
    public void execute() {
        double speed = -m_joystick.getLeftY();
        double steer = TURN_DAMPING.getValue() * -m_joystick.getRightX();
        boolean allowTurnInplace = Math.abs(speed) < TURN_IN_PLACE_THRESHOLD.getValue();
        m_chassis.setCurvatureDrive(speed, steer, allowTurnInplace);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
