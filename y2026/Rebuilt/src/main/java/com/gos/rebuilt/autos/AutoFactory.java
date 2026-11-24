package com.gos.rebuilt.autos;

import com.gos.rebuilt.subsystems.ChassisSubsystem;
import org.wpilib.tunable.Selectable;
import org.wpilib.tunable.Tunables;
import com.gos.rebuilt.commands.CombinedCommand;

public class AutoFactory {
    private final Selectable<GosAuto> m_chooser;

    public AutoFactory(ChassisSubsystem chassisSubsystem, CombinedCommand combinedCommand) {
        m_chooser = new Selectable<>();
        m_chooser.add("Left -> Preload", new StartLeftPreload(chassisSubsystem, combinedCommand));
        m_chooser.add("Left -> Trench -> Collect", new LeftTrenchCenter(chassisSubsystem, combinedCommand));
        m_chooser.add("Left -> Trench -> Collect -> Bump -> Shoot", new LeftTrenchCenterBump(chassisSubsystem, combinedCommand));
        m_chooser.add("Left  -> Preload -> Trench", new LeftPreloadTrenchCenter(chassisSubsystem, combinedCommand));

        m_chooser.add("Middle -> Preload", new StartMiddleShootMiddle(chassisSubsystem, combinedCommand));

        m_chooser.add("Right -> Preload", new StartRightPreload(chassisSubsystem, combinedCommand));
        m_chooser.add("Right -> Trench -> Collect -> Trench", new RightTrenchCenter(chassisSubsystem, combinedCommand));
        m_chooser.add("Right -> Preload -> Trench", new RightPreloadTrenchCenter(chassisSubsystem, combinedCommand));
        m_chooser.add("Right -> Trench -> Center -> Bump -> Shoot", new RightTrenchCenterBump(chassisSubsystem, combinedCommand));

        //m_chooser.add("Start Right Outpost", new StartRightOutpost(chassisSubsystem, combinedCommand));
        //m_chooser.add("Start Right Preload Outpost", new StartRightPreloadOutpost(chassisSubsystem, combinedCommand));
        // m_chooser.add("Start Left Depot", new StartLeftDepot(chassisSubsystem, combinedCommand));
        // m_chooser.add("Left -> Depot -> Shoot", new ShootLeftDepot(chassisSubsystem, combinedCommand));
        //m_chooser.add("Middle -> Shoot -> Depot -> Shoot", new MiddleShootDepot(chassisSubsystem, combinedCommand));
        //  m_chooser.add("Left -> Trench -> Center -> Depot", new LeftTrenchDepot(chassisSubsystem, combinedCommand));
        // m_chooser.add("Right -> Trench -> Center -> Depot", new RightTrenchDepot(chassisSubsystem, combinedCommand));
        //   m_chooser.add("Middle -> Depot -> Outpost", new MiddleDepotOutpostShoot(chassisSubsystem, combinedCommand));
        Tunables.publish("Autos", m_chooser);

    }

    public GosAuto getSelectedAuto() {
        return m_chooser.getSelected();
    }
}
