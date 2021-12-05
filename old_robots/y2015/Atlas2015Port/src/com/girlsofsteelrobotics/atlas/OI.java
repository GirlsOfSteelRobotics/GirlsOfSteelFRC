package com.girlsofsteelrobotics.atlas;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import com.girlsofsteelrobotics.atlas.commands.CollectorDownAndWheelIn;
import com.girlsofsteelrobotics.atlas.commands.CollectorDownWheelOut;
import com.girlsofsteelrobotics.atlas.commands.CollectorWheelForward;
import com.girlsofsteelrobotics.atlas.commands.DisengageCollector;
import com.girlsofsteelrobotics.atlas.commands.HoldChassisInPlace;
import com.girlsofsteelrobotics.atlas.commands.KickerUsingLimitSwitch;
import com.girlsofsteelrobotics.atlas.commands.ManipulatorArmDownPID;
import com.girlsofsteelrobotics.atlas.commands.ManipulatorArmUpPID;
import com.girlsofsteelrobotics.atlas.commands.STOPKICKER;
import com.girlsofsteelrobotics.atlas.commands.TrussShot;
import com.girlsofsteelrobotics.atlas.tests.TestingDrivingStraight;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    //PS3 button numbers
    private static final int SQUARE = 1;
    private static final int X = 2;
    private static final int CIRCLE = 3;
    private static final int TRIANGLE = 4;
    private static final int L2 = 7;
    private static final int SELECT = 9;
    private static final int HOME = 13;
    private static final int START = 10;
    private static final int L1 = 5;
    private static final int R2 = 8;
    private static final int R1 = 6;

    //Joystick Ports
    private final Joystick operatorJoystick;
    private final Joystick chassisJoystick;

    //Buttons for kicker
    private JoystickButton releaseKickerButton;
    private JoystickButton windupKickerButton;
    private JoystickButton lowGoalShoot;
   // JoystickButton testLimitSwitchButton;
    // JoystickButton testing;
    // private JoystickButton testKickerJag;

    //Buttons for manipulator
    // private JoystickButton testManipulatorJag;
    private final JoystickButton manipulatorUp;
    private final JoystickButton manipulatorDown;
    // private JoystickButton manipulatorStop;

    //Buttons for driving
    private final JoystickButton testStraightDriveButton;
    // private JoystickButton tankDrive;
    private JoystickButton arcadeDrive;
    private final JoystickButton holdChassis; //create button later wherever the drivers want it...
    private final JoystickButton holdChassis2;

    //Buttons for Operator Controller
    //Collector arm goes up/down
    private final JoystickButton collectorArmUp;
    private final JoystickButton collectorArmDownAndOut;
    private final JoystickButton collectorArmDownAndIn;

    //collector wheel goes forward and backward
    private final JoystickButton collectorWheelIn;

    private final JoystickButton loadKicker;
    private final JoystickButton kick;
    private final JoystickButton kickerSTOP;
    private final JoystickButton trussShot;

    //Buttons for testManipulatorJags
    /*JoystickButton testJagsForwardButton = new JoystickButton(rightJoystick, 4);
     JoystickButton testJagsBackwardButton = new JoystickButton(rightJoystick, 4);
     */
    public OI() {

        operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
        chassisJoystick = new Joystick(RobotMap.CHASSIS_JOYSTICK);

//        leftKick = new JoystickButton(chassisJoystick, TRIANGLE);
//        leftKick.whileHeld(new MoveKickerSide(1));
//        rightKick = new JoystickButton(chassisJoystick, SQUARE);
//        rightKick.whileHeld(new MoveKickerSide(0));
//        bothKicks = new JoystickButton(chassisJoystick, X);
//        bothKicks.whileHeld(new MoveKickerSide(2));
        manipulatorUp = new JoystickButton(operatorJoystick, R1);
        manipulatorUp.whileHeld(new ManipulatorArmUpPID());
          //manipulatorUp.whileHeld(new ManipulatorManualUp());
        //manipulatorUp.whenReleased(new StopManipulator());

        manipulatorDown = new JoystickButton(operatorJoystick, R2);
        manipulatorDown.whileHeld(new ManipulatorArmDownPID());
        //manipulatorDown.whileHeld(new ManipulatorManualDown());
        //manipulatorDown.whenReleased(new StopManipulator());

        //starting Operator's button testing
        collectorArmUp = new JoystickButton(operatorJoystick, TRIANGLE);
        collectorArmUp.whileHeld(new DisengageCollector());

        collectorArmDownAndOut = new JoystickButton(operatorJoystick, SQUARE);
        collectorArmDownAndOut.whileHeld(new CollectorDownWheelOut()); //This will move the collector arm down and turn the wheel
        //collectorArmDown.whileHeld(new EngageCollector());
        collectorArmDownAndIn = new JoystickButton(operatorJoystick, CIRCLE);
        collectorArmDownAndIn.whileHeld(new CollectorDownAndWheelIn());

        /*
         Not actually down (its just in) as per driver request
         */
//        collectorWheelOut = new JoystickButton(operatorJoystick, L1);
//        collectorWheelOut.whileHeld(new CollectorWheelReverse());
        //collectorArmDownAndIn.whileHeld(new CollectorDownAndWheelIn());
        collectorWheelIn = new JoystickButton(operatorJoystick, X);
        collectorWheelIn.whileHeld(new CollectorWheelForward());

     //   arcadeDrive = new JoystickButton(chassisJoystick, 5);
        //  arcadeDrive.whenPressed(new ArcadeDrive());
        testStraightDriveButton = new JoystickButton(chassisJoystick, 5);
        testStraightDriveButton.whileHeld(new TestingDrivingStraight());

        holdChassis = new JoystickButton(chassisJoystick, CIRCLE); //3 on the driver joystick
        holdChassis.whileHeld(new HoldChassisInPlace());

        holdChassis2 = new JoystickButton(chassisJoystick, L2);
        holdChassis2.whileHeld(new HoldChassisInPlace());

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
        loadKicker = new JoystickButton(operatorJoystick, SELECT);
        loadKicker.whenPressed(new KickerUsingLimitSwitch(0, false));

        kick = new JoystickButton(operatorJoystick, START); //Change to L2?
        kick.whenPressed(new KickerUsingLimitSwitch(1, false));

        trussShot = new JoystickButton(operatorJoystick, L1);
        trussShot.whenPressed(new TrussShot());

        kickerSTOP = new JoystickButton(operatorJoystick, L2);
        kickerSTOP.whenPressed(new STOPKICKER());
    }

    public Joystick getOperatorJoystick() {
        return operatorJoystick;
    }

    public Joystick getChassisJoystick() {
        return chassisJoystick;
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
