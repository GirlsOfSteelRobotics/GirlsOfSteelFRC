package frc.robot.autos;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.CombinedCommand;
import frc.robot.subsystems.ChassisSubsystem;

public class AutoFactory {
    private final SendableChooser<GosAuto> m_chooser;

    public AutoFactory(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        m_chooser = new SendableChooser<>();

        SmartDashboard.putData("Autos", m_chooser);
    }

    public GosAuto getSelectedAuto() {
        return m_chooser.getSelected();
    }
}
