package girlsofsteel.objects;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.commands.AutonomousLowGoal;
import girlsofsteel.commands.AutonomousLowGoalHot;
import girlsofsteel.commands.AutonomousMobility;
import girlsofsteel.commands.DoNothing;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Collector;
import girlsofsteel.subsystems.Driving;
import girlsofsteel.subsystems.Manipulator;

public class AutonomousChooser {

    private final SendableChooser<Command> m_chooser;
    private Command m_autonomousCommand;

    public AutonomousChooser(Driving driving, Chassis chassis, Camera camera, Collector collector, Manipulator manipulator) {
        m_chooser = new SendableChooser();
        SmartDashboard.putData("Autonomous Chooser", m_chooser);

        m_chooser.addDefault("Low Goal with Camera", new AutonomousLowGoalHot(chassis, driving, camera, collector, manipulator));
        m_chooser.addObject("Low Goal (no camera)", new AutonomousLowGoal(chassis, driving, camera, manipulator, collector));
        m_chooser.addObject("Mobility", new AutonomousMobility(chassis, driving));
        m_chooser.addObject("Nothing", new DoNothing(driving));
    }

    public void start() {
        m_autonomousCommand = (Command) m_chooser.getSelected();
        m_autonomousCommand.start();
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
