package com.gos.outreach2016.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.gos.outreach2016.robot.commands.AccessoryLeftFwd;
import com.gos.outreach2016.robot.commands.AccessoryLeftRev;
import com.gos.outreach2016.robot.commands.AccessoryRightFwd;
import com.gos.outreach2016.robot.commands.AccessoryRightRev;
import com.gos.outreach2016.robot.commands.AutonomousCommand;
import com.gos.outreach2016.robot.commands.CloseArm;
import com.gos.outreach2016.robot.commands.CollectBall;
import com.gos.outreach2016.robot.commands.DriveByJoystick;
import com.gos.outreach2016.robot.commands.OpenArm;
import com.gos.outreach2016.robot.commands.PivotDown;
import com.gos.outreach2016.robot.commands.PivotUp;
import com.gos.outreach2016.robot.commands.ReleaseBall;
import com.gos.outreach2016.robot.commands.ShiftDown;
import com.gos.outreach2016.robot.commands.ShiftUp;
import com.gos.outreach2016.robot.commands.ShootBall;
import com.gos.outreach2016.robot.commands.ShooterIn;
import com.gos.outreach2016.robot.subsystems.AccessoryMotors;
import com.gos.outreach2016.robot.subsystems.DriveSystem;
import com.gos.outreach2016.robot.subsystems.Manipulator;
import com.gos.outreach2016.robot.subsystems.Shifters;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
@SuppressWarnings("PMD.DataClass")
public class OI {
    public final JoystickButton m_joystickButton3;
    public final JoystickButton m_joystickButton2;

    public final JoystickButton m_closeArm;
    public final JoystickButton m_openArm;
    public final JoystickButton m_collectBall;
    public final JoystickButton m_releaseBall;
    public final JoystickButton m_shootBall;
    public final JoystickButton m_shooterIn;
    public final JoystickButton m_pivotUp;
    public final JoystickButton m_pivotDown;

    public final Joystick m_driveStick;
    public final Joystick m_operatorGamePad;

    public OI(Shifters shifters, DriveSystem driveSystem, Manipulator manipulator, AccessoryMotors accessoryMotors) {
        m_driveStick = new Joystick(0);
        m_operatorGamePad = new Joystick(1);

        m_joystickButton2 = new JoystickButton(m_driveStick, 2);
        m_joystickButton2.whenPressed(new ShiftDown(shifters));
        m_joystickButton3 = new JoystickButton(m_driveStick, 3);
        m_joystickButton3.whenPressed(new ShiftUp(shifters));

        m_closeArm = new JoystickButton(m_operatorGamePad, 6); //good
        m_closeArm.whenPressed(new CloseArm(manipulator));
        m_openArm = new JoystickButton(m_operatorGamePad, 5); //good
        m_openArm.whenPressed(new OpenArm(manipulator));
        m_collectBall = new JoystickButton(m_operatorGamePad, 3); //good
        m_collectBall.whileHeld(new CollectBall(manipulator));
        m_releaseBall = new JoystickButton(m_operatorGamePad, 4); //good
        m_releaseBall.whileHeld(new ReleaseBall(manipulator));
        m_shootBall = new JoystickButton(m_operatorGamePad, 2);
        m_shootBall.whenPressed(new ShootBall(manipulator));
        m_shooterIn = new JoystickButton(m_operatorGamePad, 1);
        m_shooterIn.whenPressed(new ShooterIn(manipulator));
        m_pivotUp = new JoystickButton(m_operatorGamePad, 7); //good
        m_pivotUp.whileHeld(new PivotUp(manipulator));
        m_pivotDown = new JoystickButton(m_operatorGamePad, 8); //good
        m_pivotDown.whileHeld(new PivotDown(manipulator));

        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand(driveSystem, accessoryMotors));
        SmartDashboard.putData("DriveByJoystick", new DriveByJoystick(this, driveSystem));
        SmartDashboard.putData("Shift Up", new ShiftUp(shifters));
        SmartDashboard.putData("Shift Down", new ShiftDown(shifters));
        SmartDashboard.putData("Accessory Left Fwd", new AccessoryLeftFwd(accessoryMotors));
        SmartDashboard.putData("Accessory Left Rev", new AccessoryLeftRev(accessoryMotors));
        SmartDashboard.putData("Accessory Right Fwd", new AccessoryRightFwd(accessoryMotors));
        SmartDashboard.putData("Accessory Right Rev", new AccessoryRightRev(accessoryMotors));
    }

    public Joystick getDriveStick() {
        return m_driveStick;
    }
}
