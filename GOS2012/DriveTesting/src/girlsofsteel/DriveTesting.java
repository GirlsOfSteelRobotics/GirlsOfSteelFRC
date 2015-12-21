package girlsofsteel;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import girlsofsteel.commands.CommandBase;
import girlsofsteel.commands.ExampleCommand;

public class DriveTesting extends IterativeRobot {

    Command autonomousCommand;
    
    public void robotInit() {
        // instantiate commands
        autonomousCommand = new ExampleCommand();
        
        // Initialize all subsystems
        CommandBase.init();
    }

    public void autonomousInit() {
        autonomousCommand.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        autonomousCommand.cancel();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
}