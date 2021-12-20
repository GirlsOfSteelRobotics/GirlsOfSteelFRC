/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package girlsofsteel;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.commands.ArcadeDrive;
import girlsofsteel.commands.DoNothing;
import girlsofsteel.commands.KickerUsingLimitSwitch;
import girlsofsteel.commands.ManualPositionPIDTuner;
import girlsofsteel.commands.TestKickerEncoder;
import girlsofsteel.commands.TuneManipulatorPID;
import girlsofsteel.objects.AutonomousChooser;
import girlsofsteel.objects.Camera;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Collector;
import girlsofsteel.subsystems.Driving;
import girlsofsteel.subsystems.Kicker;
import girlsofsteel.subsystems.Manipulator;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    private final Chassis m_chassis = new Chassis();
    private final Manipulator m_manipulator = new Manipulator();
    private final Kicker m_kicker = new Kicker();
    private final Collector m_collector = new Collector();
    private final Driving m_driving = new Driving();
    private final Camera m_camera = new Camera();
    private final OI m_oi;
    private final AutonomousChooser m_auto;
    private final Command m_autonomousCommand;

    public Robot() {
        // instantiate the command used for the autonomous period
        m_autonomousCommand = new DoNothing(m_driving);

        m_oi = new OI(m_chassis, m_kicker, m_manipulator, m_collector);

        m_auto = new AutonomousChooser(m_driving, m_chassis, m_camera, m_collector, m_manipulator);
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

        m_chassis.setRightEncoderReverseDirection(Configuration.rightEncoderReverse);
        m_chassis.setLeftEncoderReverseDirection(Configuration.leftEncoderReverse);
        m_chassis.setLeftPositionPIDValues(Configuration.leftPositionP, 0.0, 0.0);
        m_chassis.setRightPositionPIDValues(Configuration.rightPositionP, 0.0, 0.0);
        m_manipulator.setPID(Configuration.manipulatorPivotP, 0.0, 0.0);
        m_chassis.setLeftPositionPIDValues(Configuration.leftPositionP, 0.0, 0.0);
        m_chassis.setRightPositionPIDValues(Configuration.rightPositionP, 0.0, 0.0);
        m_manipulator.setPID(Configuration.manipulatorPivotP, 0.0, 0.0);

        SmartDashboard.putData(new KickerUsingLimitSwitch(m_kicker, -1, true));
        SmartDashboard.putData(new TestKickerEncoder(m_kicker));
        SmartDashboard.putData(new TuneManipulatorPID(m_manipulator));
        SmartDashboard.putData(new ManualPositionPIDTuner(m_chassis, m_driving));

   //     SmartDashboard.putData(new FullTester());
    }

    @Override
    public void autonomousInit() {
        // schedule the autonomous command (example)
        //new AutonomousMobility().start();
        // new AutonomousLowGoalHot().start();
        //auto.start();
        m_autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        SmartDashboard.putBoolean("Robot Is Hot", Camera.isGoalHot());
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.

        m_autonomousCommand.cancel();
        if(m_auto != null) {
            m_auto.cancel();
        }
        new ArcadeDrive(m_oi, m_driving, m_chassis).start(); //Starts arcade drive automatically
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        SmartDashboard.putBoolean("Robot Is Hot", Camera.isGoalHot());
        Scheduler.getInstance().run();
        //Configuration.configureForRobot((int) SmartDashboard.getNumber("Robot Configuration", 0));
//        SmartDashboard.putNumber("robotCameraAngle",(double)CommandBase.camera.getVerticalAngleOffset());
//        System.out.println("Camera Angle: " + (double)CommandBase.camera.getVerticalAngleOffset());
//        SmartDashboard.putNumber("robotDistance",(double)CommandBase.camera.getDistanceToTarget());
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic() {
    }
}
