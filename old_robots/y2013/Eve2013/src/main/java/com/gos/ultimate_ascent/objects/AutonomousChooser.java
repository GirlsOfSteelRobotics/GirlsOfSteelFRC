package com.gos.ultimate_ascent.objects;

import com.gos.ultimate_ascent.subsystems.Chassis;
import com.gos.ultimate_ascent.subsystems.Feeder;
import com.gos.ultimate_ascent.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.ultimate_ascent.commands.Autonomous;
import com.gos.ultimate_ascent.commands.CameraAuto;
import com.gos.ultimate_ascent.commands.DoNothing;

public class AutonomousChooser {

    private final SendableChooser<Command> m_chooser;
    private Command m_autonomousCommand;

    public AutonomousChooser(Chassis chassis, Shooter shooter, Feeder feeder) {
        m_chooser = new SendableChooser<>();
        SmartDashboard.putData("Autonomous Chooser", m_chooser);

        m_chooser.setDefaultOption("Shooting Back Right", new Autonomous(
            chassis, shooter, feeder, PositionInfo.BACK_RIGHT, 3, false));
        m_chooser.addOption("Shooting Back Left", new Autonomous(
            chassis, shooter, feeder, PositionInfo.BACK_LEFT, 3, false));
        m_chooser.addOption("Aim With Camera", new CameraAuto(chassis));
        m_chooser.addOption("Noithing", new DoNothing());
    }

    public void start() {
        m_autonomousCommand = m_chooser.getSelected();
        m_autonomousCommand.schedule();
    }

    public void end() {
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }
    }

    public void cancel() {
        end();
    }

}
