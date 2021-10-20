/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package girlsofsteel.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import girlsofsteel.tests.TestCollector;

/**
 *
 * @author Sylvie Lee
 *
 * NOTE: KICKER STUFF HAS NOT BEEN ADDED IN HERE YET
 *
 * The point of this is to test all the functions of the robot quickly in the pit.
 *
 * 1.) Arcade Drive
 *  - moving forwards, backwards, turning left and right
 * 2.) Pivot Arm
 *  - Movement w/ the PS3 controller (R1 + R2)
 *  - Moving with the PID (check to see if the down is negative and up is positive)
 *      - Should also print out encoder values with this to verify it works
 * 3.) Collector Arm
 *  - Movement w/ PS3 (triangle and x)
 *  - Check limit switches
 * 4.) Collector Wheel
 *  - Movement w/ PS3 (circle and square)
 * 5.) Kicker
 *  - Trigger w/ the servo
 *  - Position PID, print out encoder values
 *  - Go through whole kicking motion
 * 6.) Chassis position PID
 *  - encoders working? (check to see if the signs need to be flipped)
 *  - check if the setpoint should be negative or positive (probably different for right and left sides)
 *  - Re-Tune p, i, and d IF this can be done on the competition carpet (otherwise keep the values we have currently)
 * 7.) Chassis velocity PID
 *  - Assuming the encoders are working (from step 6)
 *  - Tune p, i, and d (once again only if we have a chance to tune on a real carpet)
 * 8.) Autonomous
 *  - Test first just mobility (this might just be tied in with position chassis PID testing)
 *  - Low goal autonomous
 *  - High goal autonomous (when we get one)
 *  - High goal w/ hot goal (when we get one)
 *
 */
public class FullTester extends CommandBase {

    public FullTester() {
        SmartDashboard.putData(new ArcadeDrive()); //to test arcade drive
        //SmartDashboard.putData(new setArmAnglePID(90)); //to test pivot arm PID
        //SmartDashboard.putData(new TestPivotArmPID()); //Tests the pivot arm w/ smartdashboard inputs for setpoint
        SmartDashboard.putData(new TuneManipulatorPID()); //To tune the pivot arm PID if necessary
        SmartDashboard.putData(new TestCollector()); //to test collector arm + wheel
//        SmartDashboard.putData(new CollectorWheelForward()); //probably uneccessary since we have testcollector()
//        SmartDashboard.putData(new CollectorWheelReverse()); //^
//        SmartDashboard.putData(new CollectorWheelStop()); //^
        SmartDashboard.putData(new ManualPositionPIDTuner()); //To tune the position chassis PID
        SmartDashboard.putData(new ChassisLSPBPlanner());
        SmartDashboard.putData(new MoveToPositionLSPB(0.0));
        //SmartDashboard.putData(new VelocityPIDTuner()); //To tune the velocity chassis PID
        SmartDashboard.putData(new AutonomousLowGoal()); //The low goal autonomous
        SmartDashboard.putData(new AutonomousMobility()); //To test the mobility autonomous (go forwards)
        //SmartDashboard.putData(new TestKicker());

    }

    protected void initialize() {
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        chassis.stopJags();
        chassis.disablePositionPID();
        chassis.stopEncoders();
        manipulator.stopJag();
        manipulator.stopEncoder();
        manipulator.disablePID();
        collector.stopCollector();
        collector.stopCollectorWheel();
    }

    protected void interrupted() {
        end();
    }

}
