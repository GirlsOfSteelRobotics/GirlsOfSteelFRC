package com.gos.rapidreact.commands;

import com.gos.lib.properties.GosDoubleProperty;
import com.gos.rapidreact.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class TeleopArcadeChassisCommand extends CommandBase {
    private static final GosDoubleProperty SLOW_MULTIPLIER = new GosDoubleProperty(false, "TeleDriveSlowMult", 0.5);
    private static final GosDoubleProperty TURN_SCALING_MULTIPLIER = new GosDoubleProperty(false, "TeleTurnScaling", 0.8);
    private final ChassisSubsystem m_chassis;
    private final XboxController m_joystick;

    public TeleopArcadeChassisCommand(ChassisSubsystem chassis, XboxController joystick) {
        m_chassis = chassis;
        m_joystick = joystick;
        addRequirements(m_chassis);

    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double throttle = -m_joystick.getLeftY();
        double steer = m_joystick.getRightX();
        if (m_joystick.getRightTriggerAxis() > 0.5) {
            throttle *= SLOW_MULTIPLIER.getValue();
            steer *= SLOW_MULTIPLIER.getValue();
        }
        steer *= TURN_SCALING_MULTIPLIER.getValue();
        m_chassis.setArcadeDrive(throttle, steer);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
