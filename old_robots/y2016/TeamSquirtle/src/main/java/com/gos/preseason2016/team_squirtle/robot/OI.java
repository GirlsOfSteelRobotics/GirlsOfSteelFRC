package com.gos.preseason2016.team_squirtle.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.gos.preseason2016.team_squirtle.robot.commands.RampDown;
import com.gos.preseason2016.team_squirtle.robot.commands.RampUp;
import com.gos.preseason2016.team_squirtle.robot.commands.ShiftHighGear;
import com.gos.preseason2016.team_squirtle.robot.commands.ShiftLowGear;
import com.gos.preseason2016.team_squirtle.robot.subsystems.Ramp;
import com.gos.preseason2016.team_squirtle.robot.subsystems.Shifters;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    private final Joystick m_stick;

    private final JoystickButton m_btnShiftUp;
    private final JoystickButton m_btnShiftDown;

    private final JoystickButton m_rampUp;
    private final JoystickButton m_rampDown;

    public OI(Shifters shifters, Ramp ramp) {

        m_stick = new Joystick(0);
        m_btnShiftUp = new JoystickButton(m_stick, 5);
        m_btnShiftUp.whenPressed(new ShiftHighGear(shifters));
        m_btnShiftDown = new JoystickButton(m_stick, 6);
        m_btnShiftDown.whenPressed(new ShiftLowGear(shifters));
        m_rampUp = new JoystickButton(m_stick, 3);
        m_rampUp.whenPressed(new RampUp(ramp));
        m_rampDown = new JoystickButton(m_stick, 4);
        m_rampDown.whenPressed(new RampDown(ramp));
    }

    public Joystick getJoystick() {
        return m_stick;
    }
}
