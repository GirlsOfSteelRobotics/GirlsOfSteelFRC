package com.gos.aerial_assist;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.gos.aerial_assist.commands.CollectorDownAndWheelIn;
import com.gos.aerial_assist.commands.CollectorDownWheelOut;
import com.gos.aerial_assist.commands.CollectorWheelForward;
import com.gos.aerial_assist.commands.DisengageCollector;
import com.gos.aerial_assist.commands.HoldChassisInPlace;
import com.gos.aerial_assist.commands.KickerUsingLimitSwitch;
import com.gos.aerial_assist.commands.ManipulatorArmDownPID;
import com.gos.aerial_assist.commands.ManipulatorArmUpPID;
import com.gos.aerial_assist.commands.StopKicker;
import com.gos.aerial_assist.commands.TrussShot;
import com.gos.aerial_assist.subsystems.Chassis;
import com.gos.aerial_assist.subsystems.Collector;
import com.gos.aerial_assist.subsystems.Kicker;
import com.gos.aerial_assist.subsystems.Manipulator;
import com.gos.aerial_assist.tests.TestingDrivingStraight;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
@SuppressWarnings({"PMD.TooManyFields"})
public class OI {

    //PS3 button numbers
    private static final int SQUARE = 1;
    private static final int X = 2;
    private static final int CIRCLE = 3;
    private static final int TRIANGLE = 4;
    private static final int L2 = 7;
    private static final int SELECT = 9;
    private static final int START = 10;
    private static final int L1 = 5;
    private static final int R2 = 8;
    private static final int R1 = 6;

    //Joystick Ports
    private final Joystick m_operatorJoystick;
    private final Joystick m_chassisJoystick;
    // JoystickButton testLimitSwitchButton;
    // JoystickButton testing;
    // private JoystickButton testKickerJag;

    //Buttons for manipulator
    // private JoystickButton testManipulatorJag;
    private final JoystickButton m_manipulatorUp;
    private final JoystickButton m_manipulatorDown;
    // private JoystickButton manipulatorStop;

    //Buttons for driving
    private final JoystickButton m_testStraightDriveButton;
    // private JoystickButton tankDrive;
    private final JoystickButton m_holdChassis; //create button later wherever the drivers want it...
    private final JoystickButton m_holdChassis2;

    //Buttons for Operator Controller
    //Collector arm goes up/down
    private final JoystickButton m_collectorArmUp;
    private final JoystickButton m_collectorArmDownAndOut;
    private final JoystickButton m_collectorArmDownAndIn;

    //collector wheel goes forward and backward
    private final JoystickButton m_collectorWheelIn;

    private final JoystickButton m_loadKicker;
    private final JoystickButton m_kick;
    private final JoystickButton m_kickerSTOP;
    private final JoystickButton m_trussShot;

    //Buttons for testManipulatorJags
    /*JoystickButton testJagsForwardButton = new JoystickButton(rightJoystick, 4);
     JoystickButton testJagsBackwardButton = new JoystickButton(rightJoystick, 4);
     */
    public OI(Chassis chassis, Kicker kicker, Manipulator manipulator, Collector collector) {

        m_operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
        m_chassisJoystick = new Joystick(RobotMap.CHASSIS_JOYSTICK);

        //        leftKick = new JoystickButton(chassisJoystick, TRIANGLE);
        //        leftKick.whileTrue(new MoveKickerSide(1));
        //        rightKick = new JoystickButton(chassisJoystick, SQUARE);
        //        rightKick.whileTrue(new MoveKickerSide(0));
        //        bothKicks = new JoystickButton(chassisJoystick, X);
        //        bothKicks.whileTrue(new MoveKickerSide(2));
        m_manipulatorUp = new JoystickButton(m_operatorJoystick, R1);
        m_manipulatorUp.whileTrue(new ManipulatorArmUpPID(manipulator));
        //manipulatorUp.whileTrue(new ManipulatorManualUp());
        //manipulatorUp.onFalse(new StopManipulator());

        m_manipulatorDown = new JoystickButton(m_operatorJoystick, R2);
        m_manipulatorDown.whileTrue(new ManipulatorArmDownPID(manipulator));
        //manipulatorDown.whileTrue(new ManipulatorManualDown());
        //manipulatorDown.onFalse(new StopManipulator());

        //starting Operator's button testing
        m_collectorArmUp = new JoystickButton(m_operatorJoystick, TRIANGLE);
        m_collectorArmUp.whileTrue(new DisengageCollector(collector));

        m_collectorArmDownAndOut = new JoystickButton(m_operatorJoystick, SQUARE);
        m_collectorArmDownAndOut.whileTrue(new CollectorDownWheelOut(collector)); //This will move the collector arm down and turn the wheel
        //collectorArmDown.whileTrue(new EngageCollector());
        m_collectorArmDownAndIn = new JoystickButton(m_operatorJoystick, CIRCLE);
        m_collectorArmDownAndIn.whileTrue(new CollectorDownAndWheelIn(collector));

        /*
         Not actually down (its just in) as per driver request
         */
        //        collectorWheelOut = new JoystickButton(operatorJoystick, L1);
        //        collectorWheelOut.whileTrue(new CollectorWheelReverse());
        //collectorArmDownAndIn.whileTrue(new CollectorDownAndWheelIn());
        m_collectorWheelIn = new JoystickButton(m_operatorJoystick, X);
        m_collectorWheelIn.whileTrue(new CollectorWheelForward(collector));

        //   arcadeDrive = new JoystickButton(chassisJoystick, 5);
        //  arcadeDrive.onTrue(new ArcadeDrive());
        m_testStraightDriveButton = new JoystickButton(m_chassisJoystick, 5);
        m_testStraightDriveButton.whileTrue(new TestingDrivingStraight(chassis));

        m_holdChassis = new JoystickButton(m_chassisJoystick, CIRCLE); //3 on the driver joystick
        m_holdChassis.whileTrue(new HoldChassisInPlace(chassis));

        m_holdChassis2 = new JoystickButton(m_chassisJoystick, L2);
        m_holdChassis2.whileTrue(new HoldChassisInPlace(chassis));

        //  lowGoalShoot = new JoystickButton(operatorJoystick, HOME); //Should be changed for driver preference
        //  lowGoalShoot.onTrue(new ShootLowGoal());
        // scaledArcadeDrive = new JoystickButton(chassisJoystick, L2);
        // scaledArcadeDrive.onTrue(new ScaledArcadeDrive(slowpokescale));
        //  manipulatorUp = new JoystickButton(operatorJoystick, R1);
        // manipulatorUp.whileTrue(new ManipulatorManualUp());
        //  manipulatorDown = new JoystickButton(operatorJoystick, R2);
        //  manipulatorDown.whileTrue(new ManipulatorManualDown());
        //        kickingButton.onTrue(new Kick(0, 0)); //Angle and force would need to be changed
        //        retractKickerButton.onTrue(new RetractKicker());
        //        testLimitSwitchButton.onTrue(new TestLimitSwitch());
        //  testing.onTrue(new CollectBallWithWheel());
        //        testKickerJag = new JoystickButton(leftJoystick, CIRCLE);
        //        testKickerJag.onTrue(new TestKicker());
        // testLimitSwitchButton = new JoystickButton(operatorJoystick, 4); //TODO: Change
        // testing = new JoystickButton(operatorJoystick, 8);
        /*  collectorArmUp = new JoystickButton(operatorJoystick, 5);
         collectorArmUp.onTrue(new DisengageCollector());
         collectorArmDown = new JoystickButton(rightJoystick, 6);
         collectorArmDown.onTrue(new EngageCollector());
         collectorWheelForward = new JoystickButton(rightJoystick, 7);
         collectorWheelForward.onTrue(new CollectorWheelForward());
         collectorWheelReverse = new JoystickButton(rightJoystick, 8);
         collectorWheelReverse.onTrue(new CollectorWheelReverse());
         collectorWheelStop = new JoystickButton(rightJoystick, 9);
         collectorWheelStop.onTrue(new CollectorWheelStop());
         */
        //testManipulatorJag = new JoystickButton (rightJoystick,1);
        //  tankDrive = new JoystickButton(chassisJoystick, R1);
        //  tankDrive.onTrue(new TankDrive());
        m_loadKicker = new JoystickButton(m_operatorJoystick, SELECT);
        m_loadKicker.onTrue(new KickerUsingLimitSwitch(kicker, 0, false));

        m_kick = new JoystickButton(m_operatorJoystick, START); //Change to L2?
        m_kick.onTrue(new KickerUsingLimitSwitch(kicker, 1, false));

        m_trussShot = new JoystickButton(m_operatorJoystick, L1);
        m_trussShot.onTrue(new TrussShot(manipulator, collector, kicker));

        m_kickerSTOP = new JoystickButton(m_operatorJoystick, L2);
        m_kickerSTOP.onTrue(new StopKicker(kicker));
    }

    public Joystick getOperatorJoystick() {
        return m_operatorJoystick;
    }

    public Joystick getChassisJoystick() {
        return m_chassisJoystick;
    }

    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // JoystickButton button = new JoystickButton(stick, buttonNumber);
    // Another type of button you can create is a DigitalIOButton, which is
    // a button or switch hooked up to the cypress module. These are useful if
    // you want to build a customized operator interface.
    // Button button = new DigitalIOButton(1);
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.onTrue(new ExampleCommand());
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileTrue(new ExampleCommand());
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.onFalse(new ExampleCommand());
}
