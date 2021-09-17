package com.girlsofsteelrobotics.atlas.objects;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.girlsofsteelrobotics.atlas.commands.AutonomousLowGoal;
import com.girlsofsteelrobotics.atlas.commands.AutonomousLowGoalHot;
import com.girlsofsteelrobotics.atlas.commands.AutonomousMobility;
import com.girlsofsteelrobotics.atlas.commands.DoNothing;

public class AutonomousChooser {
    
    SendableChooser chooser;
    Command autonomousCommand;
    
    public AutonomousChooser(){
        chooser = new SendableChooser();
        SmartDashboard.putData("Autonomous Chooser", chooser);
        
        chooser.addDefault("Low Goal with Camera", new AutonomousLowGoalHot());
        chooser.addObject("Low Goal (no camera)", new AutonomousLowGoal());
        chooser.addObject("Mobility", new AutonomousMobility());
        chooser.addObject("Nothing", new DoNothing());
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
