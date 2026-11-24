package frc.robot.commands;

import com.gos.lib.properties.GosDoubleProperty;
import org.wpilib.command2.button.CommandNiDsXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.ElevatorSubsystem;
import org.wpilib.command2.Command;

public class MoveElevatorWithJoystickCommand extends Command {
    private final ElevatorSubsystem m_elevatorSubsystem;
    private final CommandNiDsXboxController m_controller;

    private static final GosDoubleProperty JOYSTICK_DAMPER = new GosDoubleProperty(Constants.DEFAULT_CONSTANT_PROPERTIES, "Elevator Damper", 0.4);

    public MoveElevatorWithJoystickCommand(ElevatorSubsystem elevatorSubsystem, CommandNiDsXboxController controller) {
        this.m_elevatorSubsystem = elevatorSubsystem;

        addRequirements(this.m_elevatorSubsystem);
        m_controller = controller;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {

        if (Math.abs(m_controller.getRightY()) <= 0.02) {
            m_elevatorSubsystem.stop();
        } else {
            m_elevatorSubsystem.setThrottle(-m_controller.getRightY() * JOYSTICK_DAMPER.getValue());
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_elevatorSubsystem.stop();
    }


}
