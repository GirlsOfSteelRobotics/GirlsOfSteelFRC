package com.gos.preseason2016.team_fbi.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.gos.preseason2016.team_fbi.robot.commands.ConveyorDown;
import com.gos.preseason2016.team_fbi.robot.commands.ConveyorUp;
import com.gos.preseason2016.team_fbi.robot.commands.ShiftDown;
import com.gos.preseason2016.team_fbi.robot.commands.ShiftUp;
import com.gos.preseason2016.team_fbi.robot.subsystems.Manipulator;
import com.gos.preseason2016.team_fbi.robot.subsystems.Shifters;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */


public class OI {

    // Joysticks
    private final Joystick m_operatorJoystick;
    private final Joystick m_chassisJoystick;

    //Driving
    private final JoystickButton m_shiftUp;
    private final JoystickButton m_shiftDown;

    //Collector
    private final JoystickButton m_conveyorUp;
    private final JoystickButton m_conveyorDown;

    public OI(Shifters shifters, Manipulator manipulator) {
        // Joysticks
        m_operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
        m_chassisJoystick = new Joystick(RobotMap.CHASSIS_JOYSTICK);

        m_conveyorUp = new JoystickButton(m_operatorJoystick, 5); //TODO: change
        m_conveyorUp.whileHeld(new ConveyorUp(manipulator));
        m_conveyorDown = new JoystickButton(m_operatorJoystick, 4); //TODO: change
        m_conveyorDown.whileHeld(new ConveyorDown(manipulator));

        m_shiftUp = new JoystickButton(m_chassisJoystick, 5); //TODO: change
        m_shiftUp.whenPressed(new ShiftUp(shifters));
        m_shiftDown = new JoystickButton(m_chassisJoystick, 3); //TODO: change
        m_shiftDown.whenPressed(new ShiftDown(shifters));
    }

    public Joystick getChassisJoystick() {
        return m_chassisJoystick;
    }

    public Joystick getOperatorJoystick() {
        return m_operatorJoystick;
    }
}
