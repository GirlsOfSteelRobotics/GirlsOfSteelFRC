/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {
    
    Relay[] spikes = new Relay[5];
    Button[] forwards = new Button[5];
    Button[] backwards = new Button[5];
    Button[] on = new Button[5];
    Button[] off = new Button[5];   
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        
        for (int i = 1; i < 5; i++) {
            if (i != 2) {
            spikes[i] = new Relay(i, Relay.Direction.kBoth);
            forwards[i] = new InternalButton();
            backwards[i] = new InternalButton();
            on[i] = new InternalButton();
            off[i] = new InternalButton();
            SmartDashboard.putData("Spike " + i + " Forward", forwards[i]);
            SmartDashboard.putData("Spike " + i + " Backward", backwards[i]);
            SmartDashboard.putData("Spike " + i + " On", on[i]);
            SmartDashboard.putData("Spike " + i + " Off", off[i]);
            
            }
        }
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
for (int i = 1; i < 5; i++) {
    if (i == 2) continue;
   spikes[i].set(Relay.Value.kOff);
    }
    }
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        for (int i = 1; i < 5; i++) {
            if (i == 2) continue;
            if (forwards[i].get()) {
                spikes[i].set(Relay.Value.kForward);
            } else if (backwards[i].get()) {
                spikes[i].set(Relay.Value.kReverse);
            }else if (on[i].get()) {
                spikes[i].set(Relay.Value.kOn);
            }else if (off[i].get()) {
                spikes[i].set(Relay.Value.kOff);
            } else {
                spikes[i].set(Relay.Value.kOff);
            }
        }
    }
    
}
