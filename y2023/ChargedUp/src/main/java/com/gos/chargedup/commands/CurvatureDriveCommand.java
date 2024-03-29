package com.gos.chargedup.commands;

import com.gos.chargedup.subsystems.TankDriveChassisSubsystem;
import com.gos.lib.properties.GosDoubleProperty;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class CurvatureDriveCommand extends Command {
    private static final GosDoubleProperty TURN_DAMPING = new GosDoubleProperty(true, "JoystickTurnDamping", 0.5);
    private static final GosDoubleProperty TURN_IN_PLACE_THRESHOLD = new GosDoubleProperty(true, "JoysticTurnInPlaceThreshold", 0.05);

    private final TankDriveChassisSubsystem m_chassis;

    private final CommandXboxController m_joystick;

    public CurvatureDriveCommand(TankDriveChassisSubsystem chassisSubsystem, CommandXboxController joystick) {
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
