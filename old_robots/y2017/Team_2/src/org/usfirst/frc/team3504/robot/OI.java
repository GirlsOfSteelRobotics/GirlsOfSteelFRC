package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team3504.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.

    private JoystickButton collectIn;
    private JoystickButton release;
    private JoystickButton shoot;
    private JoystickButton pivotUp;
    private JoystickButton pivotDown;
    private JoystickButton pusherOut;

    private Joystick stick;
    private Joystick gamePad;

    // Button button = new JoystickButton(stick, buttonNumber);

    public OI(){

    stick = new Joystick(0);
    gamePad = new Joystick(1);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    collectIn = new JoystickButton(gamePad, 5);
    collectIn.whileHeld(new CollectIn());

    release = new JoystickButton(gamePad, 6);
    release.whileHeld(new Release());

    shoot = new JoystickButton(gamePad, 3);
    shoot.whenPressed(new Shoot());

    pivotUp = new JoystickButton(gamePad, 4);
    pivotUp.whileHeld(new PivotUp());

    pivotDown = new JoystickButton(gamePad, 2);
    pivotDown.whileHeld(new PivotDown());

    pusherOut = new JoystickButton(gamePad, 9);
    pusherOut.whenPressed(new PusherOut());

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

    public Joystick getStick() {
        return stick;
    }
}
