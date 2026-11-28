package frc.robot.autos;

import frc.robot.commands.CombinedCommand;
import frc.robot.subsystems.ChassisSubsystem;
import org.wpilib.telemetry.Telemetry;
import org.wpilib.tunable.Selectable;

public class AutoFactory {
    private final Selectable<GosAuto> m_chooser;

    public AutoFactory(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        m_chooser = new Selectable<>();

        Telemetry.log("Autos", m_chooser);
    }

    public GosAuto getSelectedAuto() {
        return m_chooser.getSelected();
    }
}
