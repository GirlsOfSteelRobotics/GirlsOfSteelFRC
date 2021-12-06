package org.usfirst.frc.team3504.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3504.robot.commands.Climb;
import org.usfirst.frc.team3504.robot.commands.CombinedShoot;
import org.usfirst.frc.team3504.robot.commands.CombinedShootGear;
import org.usfirst.frc.team3504.robot.commands.CombinedShootKey;
import org.usfirst.frc.team3504.robot.commands.DecrementHighShooter;
import org.usfirst.frc.team3504.robot.commands.Drive;
import org.usfirst.frc.team3504.robot.commands.DriveByDistance;
import org.usfirst.frc.team3504.robot.commands.IncrementHighShooter;
import org.usfirst.frc.team3504.robot.commands.ShiftDown;
import org.usfirst.frc.team3504.robot.commands.ShiftUp;
import org.usfirst.frc.team3504.robot.commands.SwitchBackward;
import org.usfirst.frc.team3504.robot.commands.SwitchForward;
import org.usfirst.frc.team3504.robot.commands.TurnToGear;
import org.usfirst.frc.team3504.robot.commands.UnClimb;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoBoilerGearAndShoot;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoCenterGear;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoDoNothing;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoGear;
import org.usfirst.frc.team3504.robot.commands.autonomous.AutoShooter;
import org.usfirst.frc.team3504.robot.subsystems.Agitator;
import org.usfirst.frc.team3504.robot.subsystems.Camera;
import org.usfirst.frc.team3504.robot.subsystems.Chassis;
import org.usfirst.frc.team3504.robot.subsystems.Climber;
import org.usfirst.frc.team3504.robot.subsystems.Loader;
import org.usfirst.frc.team3504.robot.subsystems.Shifters;
import org.usfirst.frc.team3504.robot.subsystems.Shooter;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
@SuppressWarnings({"PMD.GodClass", "PMD.TooManyFields", "PMD.ExcessiveMethodLength", "PMD.CyclomaticComplexity", "PMD.NcssCount"})
public class OI {

    public enum DriveDirection {
        kFWD, kREV
    }

    public enum JoystickScaling {
        linear, deadband, quadratic
    }


    //Drive Styles
    //gamepad: tank, split arcade
    //joystick: tank, one stick arcade
    public enum DriveStyle {
        oneStickArcade, gamePadArcade, twoStickTank, gamePadTank, droperation
    }

    private static final double DEADBAND = 0.3; //TODO: find a good value

    private static final DriveStyle m_driveStyle = DriveStyle.oneStickArcade;

    private Joystick m_drivingStickForward;
    private Joystick m_drivingStickBackward;
    private Joystick m_drivingStickRight;
    private Joystick m_drivingStickLeft;
    private Joystick m_drivingGamePad;
    private final Joystick m_operatingGamePad;
    private final Joystick m_autonSelector;

    private DriveDirection m_driveDirection = DriveDirection.kFWD;

    private JoystickScaling m_joystickScale = JoystickScaling.linear;

    private JoystickButton m_switchToForward;
    private JoystickButton m_switchToBackward;

    private JoystickButton m_shifterUp;
    private JoystickButton m_shifterDown;

    private JoystickButton m_shoot;
    private final JoystickButton m_shootGear;
    private final JoystickButton m_shootKey;

    private JoystickButton m_climb;
    private JoystickButton m_unClimb;

    private final JoystickButton m_incrementHighShooter;
    private final JoystickButton m_decrementHighShooter;

    private final Chassis m_chassis;
    private final Shifters m_shifters;
    private final Agitator m_agitator;
    private final Shooter m_shooter;
    private final Loader m_loader;
    private final Climber m_climber;
    private final Camera m_camera;

    public OI(Chassis chassis, Shifters shifters, Agitator agitator, Shooter shooter, Loader loader, Climber climber, Camera camera) {
        m_chassis = chassis;
        m_shifters = shifters;
        m_agitator = agitator;
        m_shooter = shooter;
        m_loader = loader;
        m_climber = climber;
        m_camera = camera;

        // Define the joysticks
        m_operatingGamePad = new Joystick(0);
        m_autonSelector = new Joystick(3);

        if (m_driveStyle == DriveStyle.oneStickArcade){
            m_drivingStickForward = new Joystick(1);
            m_drivingStickBackward = new Joystick(2);
        }
        else if (m_driveStyle == DriveStyle.gamePadArcade){
            m_drivingGamePad = new Joystick(1);
        }
        else if (m_driveStyle == DriveStyle.twoStickTank){
            m_drivingStickRight = new Joystick(1);
            m_drivingStickLeft = new Joystick(2);
        }
        else if (m_driveStyle == DriveStyle.gamePadTank){
            m_drivingGamePad = new Joystick(1);
        }
        else if (m_driveStyle == DriveStyle.droperation){
            m_drivingGamePad = new Joystick(1);
        }

        //BUTTON ASSIGNMENTS

        //OPERATOR BUTTONS
        // Shooter buttons
        m_shootKey = new JoystickButton(m_operatingGamePad, 2);
        m_shootGear = new JoystickButton(m_operatingGamePad, 3);
        m_shoot = new JoystickButton(m_operatingGamePad, 4);
        m_incrementHighShooter = new JoystickButton(m_operatingGamePad, 6);
        m_decrementHighShooter = new JoystickButton(m_operatingGamePad, 8);
        // Climber
        m_unClimb = new JoystickButton(m_operatingGamePad, 9);
        m_climb = new JoystickButton(m_operatingGamePad, 10);

        // Shooter buttons
        m_shootKey.whileHeld(new CombinedShootKey(m_agitator, m_shooter, m_loader));
        m_shootGear.whileHeld(new CombinedShootGear(m_agitator, m_shooter, m_loader));
        m_shoot.whileHeld(new CombinedShoot(m_agitator, m_shooter, m_loader));
        m_incrementHighShooter.whenPressed(new IncrementHighShooter(m_shooter));
        m_decrementHighShooter.whenPressed(new DecrementHighShooter(m_shooter));
        // Climber buttons
        m_unClimb.whileHeld(new UnClimb(m_climber));
        m_climb.whileHeld(new Climb(m_climber));

        if (m_driveStyle == DriveStyle.oneStickArcade){
            //DRIVER BUTTONS
            // Button to change between drive joysticks on trigger of both joysticks
            m_switchToForward = new JoystickButton(m_drivingStickForward, 1);
            m_switchToBackward = new JoystickButton(m_drivingStickBackward, 1);
            // Buttons for shifters copied to both joysticks
            m_shifterDown = new JoystickButton(m_drivingStickForward, 2);
            m_shifterUp = new JoystickButton(m_drivingStickForward, 3);

            // BACKWARDS BUTTONS
            if (!m_drivingStickBackward.getName().isEmpty()) {
                //DRIVER BUTTONS
                // Button to change between drive joysticks on trigger
                m_switchToBackward = new JoystickButton(m_drivingStickBackward, 1);
                // Buttons for shifters copied to both joysticks
                m_shifterDown = new JoystickButton(m_drivingStickBackward, 2);
                m_shifterUp = new JoystickButton(m_drivingStickBackward, 3);
            }

            // DRIVING BUTTONS
            // Button to change between drive joysticks on trigger of both joysticks
            m_switchToForward.whenPressed(new SwitchForward(this, m_chassis, m_camera));
            m_switchToBackward.whenPressed(new SwitchBackward(this, m_chassis, m_camera));
            // Buttons for shifters
            m_shifterDown.whenPressed(new ShiftDown(m_shifters));
            m_shifterUp.whenPressed(new ShiftUp(m_shifters));
        }
        else if (m_driveStyle == DriveStyle.gamePadArcade){
            //DRIVER BUTTONS
            // Buttons for shifters copied to both joysticks
            m_shifterDown = new JoystickButton(m_drivingGamePad, 1); //TODO: change button value
            m_shifterUp = new JoystickButton(m_drivingGamePad, 11); //TODO: change button value
        }
        else if (m_driveStyle == DriveStyle.twoStickTank){
            //DRIVER BUTTONS
            // Buttons for shifters copied to both joysticks
            m_shifterDown = new JoystickButton(m_drivingStickLeft, 2);
            m_shifterUp = new JoystickButton(m_drivingStickLeft, 3);
        }
        else if (m_driveStyle == DriveStyle.gamePadTank){
            //DRIVER BUTTONS
            // Buttons for shifters copied to both joysticks
            m_shifterDown = new JoystickButton(m_drivingGamePad, 1); //TODO: change button value
            m_shifterUp = new JoystickButton(m_drivingGamePad, 11); //TODO: change button value
        }
        else if (m_driveStyle == DriveStyle.droperation){
            m_shifterDown = new JoystickButton(m_operatingGamePad, 4); //Y
            m_shifterUp = new JoystickButton(m_operatingGamePad, 2); // B
            m_climb = new JoystickButton(m_operatingGamePad, 8);//START
            m_unClimb = new JoystickButton(m_operatingGamePad, 7);  // BACK
            m_shoot = new JoystickButton(m_operatingGamePad, 3); // X
        }

        /* Drive by vision (plus back up a few inches when done)
        driveByVision = new JoystickButton(gamePad, 1);
        driveByVision.whenPressed(new CreateMotionProfile("/home/lvuser/leftMP.dat", "/home/lvuser/rightMP.dat"));*/

        // Default commands
        m_chassis.setDefaultCommand(new Drive(this, m_chassis));
    }

    public DriveStyle getDriveStyle() {
        return m_driveStyle;
    }

    public Command getAutonCommand() {

        switch (getAutonSelector()) {
        case 0:
            return new DriveByDistance(m_chassis, m_shifters, 45, Shifters.Speed.kHigh);
        case 1:
            return new DriveByDistance(m_chassis, m_shifters, 112.0, Shifters.Speed.kHigh);
        case 2:
            return new AutoCenterGear(m_chassis, m_shifters, m_camera);
        case 3: // red boiler
            return new AutoGear(m_chassis, m_shifters, m_camera, 44.0, TurnToGear.Direction.kLeft); //updated 1:04p 4/47/17
        case 4: // red loader
            return new AutoGear(m_chassis, m_shifters, m_camera, 55.0, TurnToGear.Direction.kRight);
        case 5: // blue boiler
            return new AutoGear(m_chassis, m_shifters, m_camera, 44.0, TurnToGear.Direction.kRight); //updated 1:04p 4/47/17
        case 6: // blue loader
            return new AutoGear(m_chassis, m_shifters, m_camera, 50.0, TurnToGear.Direction.kLeft); //was previously 55.0
        case 7:
            return new AutoShooter(m_agitator, m_shooter, m_loader);
        //case 8:
            //return new DriveByDistance(-3, Shifters.Speed.kLow);
        case 9: //red boiler
            return new AutoBoilerGearAndShoot(m_chassis, m_shifters, m_agitator, m_shooter, m_loader, m_camera, 44.0, TurnToGear.Direction.kLeft); //updated 1:04p 4/47/17
        case 10: //blue boiler
            return new AutoBoilerGearAndShoot(m_chassis, m_shifters, m_agitator, m_shooter, m_loader, m_camera, 44.0, TurnToGear.Direction.kRight); //updated 1:04p 4/47/17
        /*case 11:
            return new TurnByDistance(-13.0, -3.0, Shifters.Speed.kLow);
        case 12:
            return new DriveByMotionProfile("/home/lvuser/leftCenterGear.dat", "/home/lvuser/rightCenterGear.dat", 1.0);
        case 13:
            return new AutoShooterAndCrossLine();*/
        case 15:
            return new AutoDoNothing(m_chassis);
        default:
            return new DriveByDistance(m_chassis, m_shifters, 75.5, Shifters.Speed.kLow);
        }
    }

    public double getDrivingJoystickY() {
        double unscaledValue;

        if (m_driveStyle == DriveStyle.droperation
            || m_driveStyle == DriveStyle.gamePadArcade){
            unscaledValue = m_drivingGamePad.getY();
        }
        else if (m_driveStyle == DriveStyle.oneStickArcade) {
            if (m_driveDirection == DriveDirection.kFWD) {
                unscaledValue = m_drivingStickForward.getY();
            }
            else {
                unscaledValue = -m_drivingStickBackward.getY();
            }
        }
        else {
            unscaledValue = 0.0;
        }
        return getScaledJoystickValue(unscaledValue);
    }

    public double getDrivingJoystickX() {
        double unscaledValue;

        if (m_driveStyle == DriveStyle.gamePadArcade) { // keep the redundancy, it breaks if
            unscaledValue = m_drivingGamePad.getZ(); //TODO: this should get the Z rotate value
        }
        else if (m_driveStyle == DriveStyle.droperation) { // removed
            unscaledValue = m_drivingGamePad.getX();
        }
        else if (m_driveStyle == DriveStyle.oneStickArcade){
            if (m_driveDirection == DriveDirection.kFWD) {
                unscaledValue = m_drivingStickForward.getX();
            }
            else {
                unscaledValue =  -m_drivingStickBackward.getX();
            }
        }
        else {
            unscaledValue = 0.0;
        }

        return getScaledJoystickValue(unscaledValue);
    }

    public double getDrivingJoystickLeft() {
        double unscaledValue;

        if (m_driveStyle == DriveStyle.gamePadTank) {
            unscaledValue = m_drivingGamePad.getY();
        }
        else if (m_driveStyle == DriveStyle.twoStickTank) {
            unscaledValue = m_drivingStickLeft.getY();
        }
        else {
            unscaledValue = 0.0; //TODO: may want to return something else
        }

        return getScaledJoystickValue(unscaledValue);
    }

    public double getDrivingJoystickRight() {
        double unscaledValue;

        if (m_driveStyle == DriveStyle.gamePadTank) {
            unscaledValue = m_drivingGamePad.getZ(); //TODO: this should get the Z vertical/rotate value
        }
        else if (m_driveStyle == DriveStyle.twoStickTank) {
            unscaledValue = m_drivingStickRight.getY();
        }
        else {
            unscaledValue = 0.0; //TODO: may want to return something else
        }

        return getScaledJoystickValue(unscaledValue);
    }

    public double getScaledJoystickValue(double input)
    {
        double output = 0;

        if (m_joystickScale == JoystickScaling.linear)
        {
            output = input;
        }
        else if (m_joystickScale == JoystickScaling.deadband)
        {
            if (Math.abs(input) < DEADBAND) {
                output = 0;
            }
            else
            {
                if (input > 0) {output = input - DEADBAND; }
                else {output = input + DEADBAND; }
            }
        }
        else if (m_joystickScale == JoystickScaling.quadratic)
        {
            if (input > 0) { output = Math.pow(input, 2); }
            else { output = -1 * Math.pow(input, 2); }
        }

        return output;
    }

    public void setDriveDirection(DriveDirection driveDirection) {
        m_driveDirection = driveDirection;
        System.out.println("Drive direction set to: " + driveDirection);
    }

    public void setJoystickScale(JoystickScaling joystickScale) {
        m_joystickScale = joystickScale;
        System.out.println("Joystick direction set to: " + joystickScale);
    }

    public boolean isJoystickReversed() {
        return (m_driveDirection == DriveDirection.kREV);
    }

    /**
     * Get Autonomous Mode Selector
     *
     * Read a physical pushbutton switch attached to a USB gamepad controller,
     * returning an integer that matches the current readout of the switch.
     *
     * @return int ranging 0-15
     */
    public int getAutonSelector() {
        // Each of the four "button" inputs corresponds to a bit of a binary
        // number encoding the current selection. To simplify wiring, buttons
        // 2-5 were used.
        int value = 1 * (m_autonSelector.getRawButton(2) ? 1 : 0)
                + 2 * (m_autonSelector.getRawButton(3) ? 1 : 0)
                + 4 * (m_autonSelector.getRawButton(4) ? 1 : 0)
                + 8 * (m_autonSelector.getRawButton(5) ? 1 : 0);
        System.out.println("Auto Selector Number: " + value);
        return value;
    }
}
