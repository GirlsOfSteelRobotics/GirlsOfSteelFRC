/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package girlsofsteel.testbot;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.testbot.commands.CommandBase;
import girlsofsteel.testbot.commands.GetEncoderDistance;
import girlsofsteel.testbot.commands.GetEncoderRate;
import girlsofsteel.testbot.commands.PositionControl;
import girlsofsteel.testbot.commands.RateControl;
import girlsofsteel.testbot.commands.RunRightMotor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class TestBot extends IterativeRobot {

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Initialize all subsystems
        CommandBase.init();

        // Initialize all subsystems
        CommandBase.init();

        // SmartDashboard stuff
        SmartDashboard.putData(new RunRightMotor());
        SmartDashboard.putData(new GetEncoderDistance());
        SmartDashboard.putData(new GetEncoderRate());
        SmartDashboard.putData(new PositionControl());
        SmartDashboard.putData(new RateControl());
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
}
