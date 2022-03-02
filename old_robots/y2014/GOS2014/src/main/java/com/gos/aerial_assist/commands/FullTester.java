/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gos.aerial_assist.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.aerial_assist.OI;
import com.gos.aerial_assist.objects.Camera;
import com.gos.aerial_assist.subsystems.Chassis;
import com.gos.aerial_assist.subsystems.Collector;
import com.gos.aerial_assist.subsystems.Driving;
import com.gos.aerial_assist.subsystems.Manipulator;
import com.gos.aerial_assist.tests.TestCollector;

/**
 * @author Sylvie Lee
 * <p>
 * NOTE: KICKER STUFF HAS NOT BEEN ADDED IN HERE YET
 * <p>
 * The point of this is to test all the functions of the robot quickly in the pit.
 * <p>
 * 1.) Arcade Drive
 * - moving forwards, backwards, turning left and right
 * 2.) Pivot Arm
 * - Movement w/ the PS3 controller (R1 + R2)
 * - Moving with the PID (check to see if the down is negative and up is positive)
 * - Should also print out encoder values with this to verify it works
 * 3.) Collector Arm
 * - Movement w/ PS3 (triangle and x)
 * - Check limit switches
 * 4.) Collector Wheel
 * - Movement w/ PS3 (circle and square)
 * 5.) Kicker
 * - Trigger w/ the servo
 * - Position PID, print out encoder values
 * - Go through whole kicking motion
 * 6.) Chassis position PID
 * - encoders working? (check to see if the signs need to be flipped)
 * - check if the setpoint should be negative or positive (probably different for right and left sides)
 * - Re-Tune p, i, and d IF this can be done on the competition carpet (otherwise keep the values we have currently)
 * 7.) Chassis velocity PID
 * - Assuming the encoders are working (from step 6)
 * - Tune p, i, and d (once again only if we have a chance to tune on a real carpet)
 * 8.) Autonomous
 * - Test first just mobility (this might just be tied in with position chassis PID testing)
 * - Low goal autonomous
 * - High goal autonomous (when we get one)
 * - High goal w/ hot goal (when we get one)
 */
public class FullTester extends CommandBase {

    private final Chassis m_chassis;
    private final Manipulator m_manipulator;
    private final Collector m_collector;

    public FullTester(OI oi, Chassis chassis, Driving driving, Camera camera, Manipulator manipulator, Collector collector) {
        m_chassis = chassis;
        m_manipulator = manipulator;
        m_collector = collector;

        SmartDashboard.putData(new ArcadeDrive(oi, driving, m_chassis)); //to test arcade drive
        //SmartDashboard.putData(new setArmAnglePID(90)); //to test pivot arm PID
        //SmartDashboard.putData(new TestPivotArmPID()); //Tests the pivot arm w/ smartdashboard inputs for setpoint
        SmartDashboard.putData(new TuneManipulatorPID(manipulator)); //To tune the pivot arm PID if necessary
        SmartDashboard.putData(new TestCollector(collector)); //to test collector arm + wheel
        //        SmartDashboard.putData(new CollectorWheelForward()); //probably uneccessary since we have testcollector()
        //        SmartDashboard.putData(new CollectorWheelReverse()); //^
        //        SmartDashboard.putData(new CollectorWheelStop()); //^
        SmartDashboard.putData(new ManualPositionPIDTuner(chassis, driving)); //To tune the position chassis PID
        SmartDashboard.putData(new ChassisLspbplanner(chassis, driving));
        SmartDashboard.putData(new MoveToPositionLSPB(chassis, driving, 0.0));
        //SmartDashboard.putData(new VelocityPIDTuner()); //To tune the velocity chassis PID
        SmartDashboard.putData(new AutonomousLowGoal(chassis, driving, camera, manipulator, collector)); //The low goal autonomous
        SmartDashboard.putData(new AutonomousMobility(chassis, driving)); //To test the mobility autonomous (go forwards)
        //SmartDashboard.putData(new TestKicker());

    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_chassis.stopJags();
        m_chassis.disablePositionPID();
        m_manipulator.stopJag();
        m_manipulator.disablePID();
        m_collector.stopCollector();
        m_collector.stopCollectorWheel();
    }



}
