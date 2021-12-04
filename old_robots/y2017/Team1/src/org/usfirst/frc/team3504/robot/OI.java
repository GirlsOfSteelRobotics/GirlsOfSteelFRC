package org.usfirst.frc.team3504.robot;

import org.usfirst.frc.team3504.robot.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

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
    public Joystick driveStick;
    public Joystick gamePad;

    //shooting
    //private JoystickButton shootPrep;
    //private JoystickButton shoot;

    //collect
    //private JoystickButton collect;

    //arm
    //private JoystickButton armUp;
    //private JoystickButton armDown;

    //jaw
    private final JoystickButton jawIn;
    private final JoystickButton jawOut;

    //shooter piston
    private final JoystickButton shooterIn;
    private final JoystickButton shooterOut;

    //shifters
    private final JoystickButton shiftUp;
    private final JoystickButton shiftDown;

    public OI() {

        driveStick = new Joystick(0);
        gamePad = new Joystick(1);

    /*	shootPrep = new JoystickButton(gamePad, 2);
        shootPrep.whenPressed(new ShootPrep());
        shoot = new JoystickButton(gamePad, 3);
        shoot.whenPressed(new Shoot());

        //collect = new JoystickButton(gamePad, 7);
        //collect.whileHeld(new Collect());

        /*
        armUp = new JoystickButton(gamePad, 10);
        armUp.whenPressed(new ArmUp());
        armDown = new JoystickButton(gamePad, 9);
        armDown.whenPressed(new ArmDown());
        */

        shiftUp = new JoystickButton(gamePad, 10);
        shiftUp.whenPressed(new ShiftUp());
        shiftDown = new JoystickButton(gamePad, 9);
        shiftDown.whenPressed(new ShiftDown());

        jawIn = new JoystickButton(gamePad, 5);
        jawIn.whenPressed(new JawIn());
        jawOut = new JoystickButton(gamePad , 6);
        jawOut.whenPressed(new JawOut());

        shooterIn = new JoystickButton(gamePad, 1);
        shooterIn.whenPressed(new ShooterIn());
        shooterOut = new JoystickButton(gamePad, 4);
        shooterOut.whenPressed(new ShooterOut());
    }

    public Joystick getDriveStick() {
        return driveStick;
    }
}
