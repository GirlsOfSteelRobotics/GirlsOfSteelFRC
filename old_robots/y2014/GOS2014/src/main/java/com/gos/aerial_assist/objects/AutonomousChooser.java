package com.gos.aerial_assist.objects;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.aerial_assist.commands.AutonomousLowGoal;
import com.gos.aerial_assist.commands.AutonomousLowGoalHot;
import com.gos.aerial_assist.commands.AutonomousMobility;
import com.gos.aerial_assist.commands.DoNothing;
import com.gos.aerial_assist.subsystems.Chassis;
import com.gos.aerial_assist.subsystems.Collector;
import com.gos.aerial_assist.subsystems.Driving;
import com.gos.aerial_assist.subsystems.Manipulator;

public class AutonomousChooser {

    private final SendableChooser<Command> m_chooser;
    private Command m_autonomousCommand;

    public AutonomousChooser(Driving driving, Chassis chassis, Camera camera, Collector collector, Manipulator manipulator) {
        m_chooser = new SendableChooser<>();
        SmartDashboard.putData("Autonomous Chooser", m_chooser);

        m_chooser.setDefaultOption("Low Goal with Camera", new AutonomousLowGoalHot(chassis, driving, camera, collector, manipulator));
        m_chooser.addOption("Low Goal (no camera)", new AutonomousLowGoal(chassis, driving, camera, manipulator, collector));
        m_chooser.addOption("Mobility", new AutonomousMobility(chassis, driving));
        m_chooser.addOption("Nothing", new DoNothing(driving));
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
