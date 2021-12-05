package org.usfirst.frc.team3504.robot;


import org.usfirst.frc.team3504.robot.commands.collector.CollectTote;
import org.usfirst.frc.team3504.robot.commands.collector.ReleaseTote;
import org.usfirst.frc.team3504.robot.commands.collector.AngleCollectorIn;
import org.usfirst.frc.team3504.robot.commands.collector.AngleCollectorOut;
import org.usfirst.frc.team3504.robot.commands.drive.ResetGyro;
import org.usfirst.frc.team3504.robot.commands.drive.GetGyro;
import org.usfirst.frc.team3504.robot.commands.shack.ShackIn;
import org.usfirst.frc.team3504.robot.commands.shack.ShackOut;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    // // CREATING BUTTONS
    // One type of button is a joystick button which is any button on a
    // joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    // // TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    // Joysticks
    private final Joystick operatorJoystick;
    private final Joystick chassisJoystick;

    // Collector
    private final LTButton collectTote;
    private final RTButton releaseTote;
    private final JoystickButton angleIn;
    private final JoystickButton angleOut;

    // Finger
    private JoystickButton fingersDown;
    private JoystickButton fingersUp;

    // Lifting
    private JoystickButton liftUp;
    private JoystickButton liftDown;
    private JoystickButton liftOneTote;

    // Auto Buttons
    private JoystickButton autoDriveForward;
    private JoystickButton autoDriveBackwards;
    private JoystickButton autoDriveLeft;
    private JoystickButton autoDriveRight;

    // Drive Buttons
    private JoystickButton driveForward;
    private JoystickButton driveBackward;
    private JoystickButton driveRight;
    private JoystickButton driveLeft;

    // Shack Buttons
    private final JoystickButton shackIn;
    private final JoystickButton shackOut;

    // Ultrasonic buttons
    private JoystickButton getDistance;

    // Gyro Button
    private final JoystickButton resetGyro;
    private final JoystickButton getGyro;

    // PID button
    private JoystickButton pidLifterTesting;

    public OI() {
        // Joysticks
        operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
        chassisJoystick = new Joystick(RobotMap.CHASSIS_JOYSTICK);

        //Collectors: collect/release tote on z-axis
        collectTote = new LTButton(operatorJoystick);
        collectTote.whileActive(new CollectTote());
        releaseTote = new RTButton(operatorJoystick);
        releaseTote.whileActive(new ReleaseTote());
        angleIn = new JoystickButton(operatorJoystick, 5); //hello sonia i am so interested in ur life please tell me everything
        angleIn.whenPressed(new AngleCollectorIn()); //omg sonia u dont even exist in real life am i just dreaming i think im loopy
        angleOut = new JoystickButton(operatorJoystick, 6); //what is going on i am so confused what does this even mean
        angleOut.whenPressed(new AngleCollectorOut()); //lolol lets rap

        // Shack
        shackIn = new JoystickButton(operatorJoystick, 1);
        shackIn.whenPressed(new ShackIn());
        shackOut = new JoystickButton(operatorJoystick, 2);
        shackOut.whenPressed(new ShackOut());

        // Lifting
        // liftUp = new JoystickButton(operatorJoystick, 7);
        // liftUp.whenPressed(new LiftUpWhileHeld());
        // liftUp.whileHeld(new LiftUpHeld());
        // liftDown = new JoystickButton(operatorJoystick, 8);
        // liftDown.whenPressed(new LiftDownWhileHeld());
        // liftDown.whileHeld(new LiftDownHeld());
        // liftOneTote = new JoystickButton(chassisJoystick, 9);

        // autoDriveRight = new JoystickButton(chassisJoystick, 5);
        // autoDriveRight.whenPressed(new AutoDriveRight(50));
        // autoDriveLeft = new JoystickButton(chassisJoystick, 6);
        // autoDriveLeft.whenPressed(new AutoDriveLeft(50));
        // autoDriveForward = new JoystickButton(chassisJoystick, 4);
        // autoDriveForward.whenPressed(new AutoDriveForward(50));
        // autoDriveBackwards = new JoystickButton(chassisJoystick, 3);
        // autoDriveBackwards.whenReleased(new AutoDriveBackwards(50));

        /*
         * //Drive buttons initialization driveForward = new
         * JoystickButton(chassisJoystick, 5); driveForward.whileHeld(new
         * DriveForward()); driveBackward = new JoystickButton(chassisJoystick,
         * 6); driveBackward.whileHeld(new DriveBackward()); driveRight = new
         * JoystickButton(chassisJoystick, 4); driveRight.whileHeld(new
         * DriveRight()); driveLeft = new JoystickButton(chassisJoystick, 3);
         * driveLeft.whileHeld(new DriveLeft());
         */

        // Gyro Buttons
        resetGyro = new JoystickButton(chassisJoystick, 17);
        resetGyro.whenPressed(new ResetGyro());
        getGyro = new JoystickButton(chassisJoystick, 18);
        getGyro.whenPressed(new GetGyro());

        // Pid TESTING
        // pidLifterTesting = new JoystickButton(chassisJoystick, 10);
        // pidLifterTesting.whenPressed(new PIDLifterTesting());
    }

    public Joystick getOperatorJoystick() {
        return operatorJoystick;
    }

    public Joystick getChassisJoystick() {
        return chassisJoystick;
    }
}
