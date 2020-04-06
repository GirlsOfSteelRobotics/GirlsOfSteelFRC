package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ChassisSubsystem;


public class TelopDriveCommand extends CommandBase {
    private final XboxController m_joystick;
    private final ChassisSubsystem chassisSubsystem;

    public TelopDriveCommand(ChassisSubsystem chassisSubsystem) {
        this.chassisSubsystem = chassisSubsystem;
        m_joystick = new XboxController(0);

        addRequirements(this.chassisSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        chassisSubsystem.setSpeedAndSteer(m_joystick.getY(GenericHID.Hand.kLeft), m_joystick.getX(GenericHID.Hand.kRight));
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
