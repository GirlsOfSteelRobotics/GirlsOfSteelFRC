package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team3504.robot.commands.CollectIn;
import org.usfirst.frc.team3504.robot.commands.PivotDown;
import org.usfirst.frc.team3504.robot.commands.PivotUp;
import org.usfirst.frc.team3504.robot.commands.PusherOut;
import org.usfirst.frc.team3504.robot.commands.Release;
import org.usfirst.frc.team3504.robot.commands.Shoot;
import org.usfirst.frc.team3504.robot.subsystems.Manipulator;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.

    private final JoystickButton m_collectIn;
    private final JoystickButton m_release;
    private final JoystickButton m_shoot;
    private final JoystickButton m_pivotUp;
    private final JoystickButton m_pivotDown;
    private final JoystickButton m_pusherOut;

    private final Joystick m_stick;
    private final Joystick m_gamePad;

    // Button button = new JoystickButton(stick, buttonNumber);

    public OI(Manipulator manipulator) {

        m_stick = new Joystick(0);
        m_gamePad = new Joystick(1);

        // There are a few additional built in buttons you can use. Additionally,
        // by subclassing Button you can create custom triggers and bind those to
        // commands the same as any other Button.

        //// TRIGGERING COMMANDS WITH BUTTONS
        // Once you have a button, it's trivial to bind it to a button in one of
        // three ways:
        m_collectIn = new JoystickButton(m_gamePad, 5);
        m_collectIn.whileHeld(new CollectIn(manipulator));

        m_release = new JoystickButton(m_gamePad, 6);
        m_release.whileHeld(new Release(manipulator));

        m_shoot = new JoystickButton(m_gamePad, 3);
        m_shoot.whenPressed(new Shoot(manipulator));

        m_pivotUp = new JoystickButton(m_gamePad, 4);
        m_pivotUp.whileHeld(new PivotUp(manipulator));

        m_pivotDown = new JoystickButton(m_gamePad, 2);
        m_pivotDown.whileHeld(new PivotDown(manipulator));

        m_pusherOut = new JoystickButton(m_gamePad, 9);
        m_pusherOut.whenPressed(new PusherOut(manipulator));
    }

    public Joystick getStick() {
        return m_stick;
    }
}
