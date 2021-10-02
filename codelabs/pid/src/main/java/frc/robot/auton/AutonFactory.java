package frc.robot.auton;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;

public class AutonFactory {

    private final SendableChooser<Command> m_sendableChooser;

    @SuppressWarnings("PMD.UnusedFormalParameter")
    public AutonFactory(RobotContainer robotContainer) {
        m_sendableChooser = new SendableChooser<>();

        SmartDashboard.putData(m_sendableChooser);
    }

    public Command getAutonMode() {
        return m_sendableChooser.getSelected();
    }
}
