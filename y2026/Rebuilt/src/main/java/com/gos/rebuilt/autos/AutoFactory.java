package com.gos.rebuilt.autos;

import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.rebuilt.commands.CombinedCommand;

public class AutoFactory {
    private final SendableChooser<GosAuto> m_chooser;

    public AutoFactory(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        m_chooser = new SendableChooser<>();
        m_chooser.addOption("Start Left Depot", new StartLeftDepot(chassisSubsystem, combinedCommand));
        m_chooser.addOption("Start Left Preload", new StartLeftPreload(chassisSubsystem, combinedCommand));
        m_chooser.addOption("Start Middle Shoot Middle", new StartMiddleShootMiddle(chassisSubsystem, combinedCommand));
        m_chooser.addOption("Start Right Preload", new StartRightPreload(chassisSubsystem, combinedCommand));
        m_chooser.addOption("Start Right Outpost", new StartRightOutpost(chassisSubsystem, combinedCommand));
        m_chooser.addOption("Start Right Preload Outpost", new StartRightPreloadOutpost(chassisSubsystem, combinedCommand));
        m_chooser.addOption("Right -> Trench -> Center", new RightTrenchCenter(chassisSubsystem, combinedCommand));
        m_chooser.addOption("Left -> Trench -> Center", new LeftTrenchCenter(chassisSubsystem, combinedCommand));
        m_chooser.addOption("Left -> Trench -> Center -> Depot", new LeftTrenchDepot(chassisSubsystem, combinedCommand));
        m_chooser.addOption("Right -> Trench -> Center -> Depot", new RightTrenchDepot(chassisSubsystem, combinedCommand));
        m_chooser.addOption("Middle -> Depot -> Outpost", new MiddleDepotOutpostShoot(chassisSubsystem, combinedCommand));

        SmartDashboard.putData("Autos", m_chooser);

    }

    public GosAuto getSelectedAuto() {
        return m_chooser.getSelected();
    }
}
