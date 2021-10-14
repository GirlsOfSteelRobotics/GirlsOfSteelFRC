package girlsofsteel.objects;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.commands.Autonomous;
import girlsofsteel.commands.CameraAuto;
import girlsofsteel.commands.DoNothing;

public class AutonomousChooser {

    SendableChooser chooser;
    Command autonomousCommand;

    public AutonomousChooser(){
        chooser = new SendableChooser();
        SmartDashboard.putData("Autonomous Chooser", chooser);

        chooser.addDefault("Shooting Back Right", new Autonomous(
                PositionInfo.BACK_RIGHT, 3, false));
        chooser.addObject("Shooting Back Left", new Autonomous(
                PositionInfo.BACK_LEFT, 3, false));
        chooser.addObject("Aim With Camera", new CameraAuto());
        chooser.addObject("Noithing", new DoNothing());
    }

    public void start(){
        autonomousCommand = (Command) chooser.getSelected();
        autonomousCommand.start();
    }

    public void end(){
        if(autonomousCommand != null){
            autonomousCommand.cancel();
        }
    }

    public void cancel(){
        end();
    }

}
