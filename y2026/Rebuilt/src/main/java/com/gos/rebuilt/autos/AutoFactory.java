package com.gos.rebuilt.autos;

import com.gos.rebuilt.subsystems.ChassisSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class AutoFactory {
    private SendableChooser<Command> m_chooser;

    public AutoFactory(ChassisSubsystem chassisSubsystem) {
        m_chooser = new SendableChooser<>();
        m_chooser.addOption("Start Left Depot", new StartLeftDepot(chassisSubsystem));
        m_chooser.addOption("Start Left Preload", new StartLeftPreload(chassisSubsystem));
        m_chooser.addOption("Start Middle Shoot Middle", new StartMiddleShootMiddle(chassisSubsystem));
        m_chooser.addOption("Start Right Preload", new StartRightPreload(chassisSubsystem));
        m_chooser.addOption("Start Right Outpost", new StartRightOutpost(chassisSubsystem));
        SmartDashboard.putData("Autos", m_chooser);
    }

    public Command getSelectedAuto() {
        return m_chooser.getSelected();
    }
}
