package frc.robot.autos;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.ChassisSubsystem;

public class AutoFactory {
    private final SendableChooser<GosAuto> m_chooser;

    public AutoFactory(ChassisSubsystem chassisSubsystem) {
        m_chooser = new SendableChooser<>();

        m_chooser.addOption("Kitchen -> Center Line", new KitchenToCenterLine(chassisSubsystem));

        SmartDashboard.putData("Autos", m_chooser);
    }

    public GosAuto getSelectedAuto() {
        return m_chooser.getSelected();
    }
}
