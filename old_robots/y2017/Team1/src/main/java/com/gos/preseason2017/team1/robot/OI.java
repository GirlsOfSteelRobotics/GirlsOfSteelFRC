package com.gos.preseason2017.team1.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.gos.preseason2017.team1.robot.commands.JawIn;
import com.gos.preseason2017.team1.robot.commands.JawOut;
import com.gos.preseason2017.team1.robot.commands.ShiftDown;
import com.gos.preseason2017.team1.robot.commands.ShiftUp;
import com.gos.preseason2017.team1.robot.commands.ShooterIn;
import com.gos.preseason2017.team1.robot.commands.ShooterOut;
import com.gos.preseason2017.team1.robot.subsystems.JawPiston;
import com.gos.preseason2017.team1.robot.subsystems.Shifters;
import com.gos.preseason2017.team1.robot.subsystems.Shooter;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public Joystick m_driveStick;
    public Joystick m_gamePad;

    //shooting
    //private JoystickButton shootPrep;
    //private JoystickButton shoot;

    //collect
    //private JoystickButton collect;

    //arm
    //private JoystickButton armUp;
    //private JoystickButton armDown;

    //jaw
    private final JoystickButton m_jawIn;
    private final JoystickButton m_jawOut;

    //shooter piston
    private final JoystickButton m_shooterIn;
    private final JoystickButton m_shooterOut;

    //shifters
    private final JoystickButton m_shiftUp;
    private final JoystickButton m_shiftDown;

    public OI(Shifters shifters, JawPiston jaw, Shooter shooter) {

        m_driveStick = new Joystick(0);
        m_gamePad = new Joystick(1);

        /*    shootPrep = new JoystickButton(gamePad, 2);
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

        m_shiftUp = new JoystickButton(m_gamePad, 10);
        m_shiftUp.whenPressed(new ShiftUp(shifters));
        m_shiftDown = new JoystickButton(m_gamePad, 9);
        m_shiftDown.whenPressed(new ShiftDown(shifters));

        m_jawIn = new JoystickButton(m_gamePad, 5);
        m_jawIn.whenPressed(new JawIn(jaw));
        m_jawOut = new JoystickButton(m_gamePad, 6);
        m_jawOut.whenPressed(new JawOut(jaw));

        m_shooterIn = new JoystickButton(m_gamePad, 1);
        m_shooterIn.whenPressed(new ShooterIn(shooter));
        m_shooterOut = new JoystickButton(m_gamePad, 4);
        m_shooterOut.whenPressed(new ShooterOut(shooter));
    }

    public Joystick getDriveStick() {
        return m_driveStick;
    }
}
