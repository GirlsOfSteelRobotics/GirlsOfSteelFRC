package com.gos.codelabs.pid.auton_modes;

import com.gos.codelabs.pid.RobotContainer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

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
