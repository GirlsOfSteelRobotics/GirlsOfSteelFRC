package com.gos.recycle_rush.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.gos.recycle_rush.robot.commands.collector.AngleCollectorIn;
import com.gos.recycle_rush.robot.commands.collector.AngleCollectorOut;
import com.gos.recycle_rush.robot.commands.collector.CollectTote;
import com.gos.recycle_rush.robot.commands.collector.ReleaseTote;
import com.gos.recycle_rush.robot.commands.drive.GetGyro;
import com.gos.recycle_rush.robot.commands.drive.ResetGyro;
import com.gos.recycle_rush.robot.commands.shack.ShackIn;
import com.gos.recycle_rush.robot.commands.shack.ShackOut;
import com.gos.recycle_rush.robot.subsystems.Chassis;
import com.gos.recycle_rush.robot.subsystems.Collector;
import com.gos.recycle_rush.robot.subsystems.Shack;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    // Joysticks
    private final Joystick m_operatorJoystick;
    private final Joystick m_chassisJoystick;

    // Collector
    private final LTButton m_collectTote;
    private final RTButton m_releaseTote;
    private final JoystickButton m_angleIn;
    private final JoystickButton m_angleOut;

    // Shack Buttons
    private final JoystickButton m_shackIn;
    private final JoystickButton m_shackOut;

    // Gyro Button
    private final JoystickButton m_resetGyro;
    private final JoystickButton m_getGyro;

    public OI(Chassis chassis, Collector collector, Shack shack) {
        // Joysticks
        m_operatorJoystick = new Joystick(RobotMap.OPERATOR_JOYSTICK);
        m_chassisJoystick = new Joystick(RobotMap.CHASSIS_JOYSTICK);

        //Collectors: collect/release tote on z-axis
        m_collectTote = new LTButton(m_operatorJoystick);
        m_collectTote.whileActive(new CollectTote(collector));
        m_releaseTote = new RTButton(m_operatorJoystick);
        m_releaseTote.whileActive(new ReleaseTote(collector));
        m_angleIn = new JoystickButton(m_operatorJoystick, 5); //hello sonia i am so interested in ur life please tell me everything
        m_angleIn.whenPressed(new AngleCollectorIn(collector)); //omg sonia u dont even exist in real life am i just dreaming i think im loopy
        m_angleOut = new JoystickButton(m_operatorJoystick, 6); //what is going on i am so confused what does this even mean
        m_angleOut.whenPressed(new AngleCollectorOut(collector)); //lolol lets rap

        // Shack
        m_shackIn = new JoystickButton(m_operatorJoystick, 1);
        m_shackIn.whenPressed(new ShackIn(shack));
        m_shackOut = new JoystickButton(m_operatorJoystick, 2);
        m_shackOut.whenPressed(new ShackOut(shack));

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
        m_resetGyro = new JoystickButton(m_chassisJoystick, 17);
        m_resetGyro.whenPressed(new ResetGyro(chassis));
        m_getGyro = new JoystickButton(m_chassisJoystick, 18);
        m_getGyro.whenPressed(new GetGyro(chassis));

        // Pid TESTING
        // pidLifterTesting = new JoystickButton(chassisJoystick, 10);
        // pidLifterTesting.whenPressed(new PIDLifterTesting());
    }

    public Joystick getOperatorJoystick() {
        return m_operatorJoystick;
    }

    public Joystick getChassisJoystick() {
        return m_chassisJoystick;
    }
}
