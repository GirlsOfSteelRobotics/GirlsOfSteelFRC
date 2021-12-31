package girlsofsteel;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import girlsofsteel.commands.CollectorDownAndWheelIn;
import girlsofsteel.commands.CollectorDownWheelOut;
import girlsofsteel.commands.CollectorWheelForward;
import girlsofsteel.commands.DisengageCollector;
import girlsofsteel.commands.HoldChassisInPlace;
import girlsofsteel.commands.KickerUsingLimitSwitch;
import girlsofsteel.commands.ManipulatorArmDownPID;
import girlsofsteel.commands.ManipulatorArmUpPID;
import girlsofsteel.commands.StopKicker;
import girlsofsteel.commands.TrussShot;
import girlsofsteel.subsystems.Chassis;
import girlsofsteel.subsystems.Collector;
import girlsofsteel.subsystems.Kicker;
import girlsofsteel.subsystems.Manipulator;
import girlsofsteel.tests.TestingDrivingStraight;

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
//        leftKick.whileHeld(new MoveKickerSide(1));
//        rightKick = new JoystickButton(chassisJoystick, SQUARE);
//        rightKick.whileHeld(new MoveKickerSide(0));
//        bothKicks = new JoystickButton(chassisJoystick, X);
//        bothKicks.whileHeld(new MoveKickerSide(2));
        m_manipulatorUp = new JoystickButton(m_operatorJoystick, R1);
        m_manipulatorUp.whileHeld(new ManipulatorArmUpPID(manipulator));
        //manipulatorUp.whileHeld(new ManipulatorManualUp());
        //manipulatorUp.whenReleased(new StopManipulator());

        m_manipulatorDown = new JoystickButton(m_operatorJoystick, R2);
        m_manipulatorDown.whileHeld(new ManipulatorArmDownPID(manipulator));
        //manipulatorDown.whileHeld(new ManipulatorManualDown());
        //manipulatorDown.whenReleased(new StopManipulator());

        //starting Operator's button testing
        m_collectorArmUp = new JoystickButton(m_operatorJoystick, TRIANGLE);
        m_collectorArmUp.whileHeld(new DisengageCollector(collector));

        m_collectorArmDownAndOut = new JoystickButton(m_operatorJoystick, SQUARE);
        m_collectorArmDownAndOut.whileHeld(new CollectorDownWheelOut(collector)); //This will move the collector arm down and turn the wheel
        //collectorArmDown.whileHeld(new EngageCollector());
        m_collectorArmDownAndIn = new JoystickButton(m_operatorJoystick, CIRCLE);
        m_collectorArmDownAndIn.whileHeld(new CollectorDownAndWheelIn(collector));

        /*
         Not actually down (its just in) as per driver request
         */
//        collectorWheelOut = new JoystickButton(operatorJoystick, L1);
//        collectorWheelOut.whileHeld(new CollectorWheelReverse());
        //collectorArmDownAndIn.whileHeld(new CollectorDownAndWheelIn());
        m_collectorWheelIn = new JoystickButton(m_operatorJoystick, X);
        m_collectorWheelIn.whileHeld(new CollectorWheelForward(collector));

        //   arcadeDrive = new JoystickButton(chassisJoystick, 5);
        //  arcadeDrive.whenPressed(new ArcadeDrive());
        m_testStraightDriveButton = new JoystickButton(m_chassisJoystick, 5);
        m_testStraightDriveButton.whileHeld(new TestingDrivingStraight(chassis));

        m_holdChassis = new JoystickButton(m_chassisJoystick, CIRCLE); //3 on the driver joystick
        m_holdChassis.whileHeld(new HoldChassisInPlace(chassis));

        m_holdChassis2 = new JoystickButton(m_chassisJoystick, L2);
        m_holdChassis2.whileHeld(new HoldChassisInPlace(chassis));

        //  lowGoalShoot = new JoystickButton(operatorJoystick, HOME);//Should be changed for driver preference
        //  lowGoalShoot.whenPressed(new ShootLowGoal());
        // scaledArcadeDrive = new JoystickButton(chassisJoystick, L2);
        // scaledArcadeDrive.whenPressed(new ScaledArcadeDrive(slowpokescale));
        //  manipulatorUp = new JoystickButton(operatorJoystick, R1);
        // manipulatorUp.whileHeld(new ManipulatorManualUp());
        //  manipulatorDown = new JoystickButton(operatorJoystick, R2);
        //  manipulatorDown.whileHeld(new ManipulatorManualDown());
        //        kickingButton.whenPressed(new Kick(0, 0)); //Angle and force would need to be changed
//        retractKickerButton.whenPressed(new RetractKicker());
//        testLimitSwitchButton.whenPressed(new TestLimitSwitch());
        //  testing.whenPressed(new CollectBallWithWheel());
//        testKickerJag = new JoystickButton(leftJoystick, CIRCLE);
//        testKickerJag.whenPressed(new TestKicker());
        // testLimitSwitchButton = new JoystickButton(operatorJoystick, 4); //TODO: Change
        // testing = new JoystickButton(operatorJoystick, 8);
        /*  collectorArmUp = new JoystickButton(operatorJoystick, 5);
         collectorArmUp.whenPressed(new DisengageCollector());
         collectorArmDown = new JoystickButton(rightJoystick, 6);
         collectorArmDown.whenPressed(new EngageCollector());
         collectorWheelForward = new JoystickButton(rightJoystick, 7);
         collectorWheelForward.whenPressed(new CollectorWheelForward());
         collectorWheelReverse = new JoystickButton(rightJoystick, 8);
         collectorWheelReverse.whenPressed(new CollectorWheelReverse());
         collectorWheelStop = new JoystickButton(rightJoystick, 9);
         collectorWheelStop.whenPressed(new CollectorWheelStop());
         */
        //testManipulatorJag = new JoystickButton (rightJoystick,1);
        //  tankDrive = new JoystickButton(chassisJoystick, R1);
        //  tankDrive.whenPressed(new TankDrive());
        m_loadKicker = new JoystickButton(m_operatorJoystick, SELECT);
        m_loadKicker.whenPressed(new KickerUsingLimitSwitch(kicker, 0, false));

        m_kick = new JoystickButton(m_operatorJoystick, START); //Change to L2?
        m_kick.whenPressed(new KickerUsingLimitSwitch(kicker, 1, false));

        m_trussShot = new JoystickButton(m_operatorJoystick, L1);
        m_trussShot.whenPressed(new TrussShot(manipulator, collector, kicker));

        m_kickerSTOP = new JoystickButton(m_operatorJoystick, L2);
        m_kickerSTOP.whenPressed(new StopKicker(kicker));
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
    // Button button = new JoystickButton(stick, buttonNumber);
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
    // button.whenPressed(new ExampleCommand());
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}
