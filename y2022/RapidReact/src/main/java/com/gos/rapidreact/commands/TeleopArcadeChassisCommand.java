package com.gos.rapidreact.commands;

import com.gos.rapidreact.subsystems.ChassisSubsystem;
import com.gos.rapidreact.subsystems.HangerSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class TeleopArcadeChassisCommand extends CommandBase {
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
        m_chassis.setArcadeDrive(-m_joystick.getLeftY(), m_joystick.getRightX());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }

    public static class HangerDownCommand extends CommandBase {
        private HangerSubsystem m_hanger;

        public HangerDownCommand(HangerSubsystem hanger) {
            m_hanger = hanger;
        }

        @Override
        public void initialize() {

        }

        @Override
        public void execute() {
            m_hanger.setLeftHangerSpeed(HangerSubsystem.HANGER_UP_SPEED);
            m_hanger.setRightHangerSpeed(HangerSubsystem.HANGER_UP_SPEED);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public void end(boolean interrupted) {
            m_hanger.setLeftHangerSpeed(0);
            m_hanger.setRightHangerSpeed(0);

        }
    }
}
