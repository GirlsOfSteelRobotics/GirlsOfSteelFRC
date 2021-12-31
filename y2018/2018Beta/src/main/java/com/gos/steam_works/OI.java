package com.gos.steam_works;

import com.gos.steam_works.commands.Drive;
import com.gos.steam_works.commands.DriveByDistance;
import com.gos.steam_works.commands.ShiftDown;
import com.gos.steam_works.commands.ShiftUp;
import com.gos.steam_works.commands.TurnToGear;
import com.gos.steam_works.commands.autonomous.AutoBoilerGearAndShoot;
import com.gos.steam_works.commands.autonomous.AutoCenterGear;
import com.gos.steam_works.commands.autonomous.AutoDoNothing;
import com.gos.steam_works.commands.autonomous.AutoGear;
import com.gos.steam_works.commands.autonomous.AutoShooter;
import com.gos.steam_works.subsystems.Agitator;
import com.gos.steam_works.subsystems.Chassis;
import com.gos.steam_works.subsystems.Loader;
import com.gos.steam_works.subsystems.Shifters;
import com.gos.steam_works.subsystems.Shooter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
@SuppressWarnings("PMD.TooManyFields")
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
        joystickArcade, gamePadArcade, joystickTank, gamePadTank
    }

    private DriveStyle m_driveStyle;

    private final Joystick m_drivingGamePad = new Joystick(1);
    private final Joystick m_drivingJoystickOne = new Joystick(1);
    private final Joystick m_drivingJoystickTwo = new Joystick(2);
    private final Joystick m_autonSelector = new Joystick(5);

    private final DigitalInput m_dio0 = new DigitalInput(0);
    private final DigitalInput m_dio1 = new DigitalInput(1);
    private final DigitalInput m_dio2 = new DigitalInput(2);
    private final DigitalInput m_dio3 = new DigitalInput(3);
    private final DigitalInput m_dio4 = new DigitalInput(4);

    private DriveDirection m_driveDirection = DriveDirection.kFWD;

    private JoystickScaling m_joystickScale = JoystickScaling.linear;
    private static final double DEADBAND = 0.3; //TODO: find a good value

    private final Chassis m_chassis;
    private final Shifters m_shifters;
    private final Shooter m_shooter;
    private final Loader m_loader;
    private final Agitator m_agitator;
    private final GripPipelineListener m_pipelineListener;

    public OI(Chassis chassis, Shifters shifters, Shooter shooter, Loader loader, Agitator agitator, GripPipelineListener pipelineListener) {

        m_chassis = chassis;
        m_shifters = shifters;
        m_shooter = shooter;
        m_agitator = agitator;
        m_loader = loader;
        m_pipelineListener = pipelineListener;

        //BUTTON ASSIGNMENTS
        JoystickButton shifterDown = new JoystickButton(m_drivingJoystickOne, 2);
        JoystickButton shifterUp = new JoystickButton(m_drivingJoystickOne, 3);
        JoystickButton driveByDistanceLow = new JoystickButton(m_drivingJoystickOne, 9);
        JoystickButton driveByDistanceHigh = new JoystickButton(m_drivingJoystickOne, 10);

        //        JoystickButton liftUp = new JoystickButton(operatorGamePad, 1); //TODO: random buttom assignment
        //        JoystickButton liftDown = new JoystickButton(operatorGamePad, 2);
        //
        //        JoystickButton collect = new JoystickButton(operatorGamePad, 3);
        //        JoystickButton release = new JoystickButton(operatorGamePad, 4);

        shifterDown.whenPressed(new ShiftDown(m_shifters));
        shifterUp.whenPressed(new ShiftUp(m_shifters));
        driveByDistanceLow.whenPressed(new DriveByDistance(m_chassis, m_shifters, 12, Shifters.Speed.kLow));
        driveByDistanceHigh.whenPressed(new DriveByDistance(m_chassis, m_shifters, 12, Shifters.Speed.kHigh));

        //liftUp.whileHeld(new LiftUp());
        //liftDown.whileHeld(new LiftDown());

        //collect.whileHeld(new Collect());
        //release.whileHeld(new Release());

        m_drivingGamePad.setTwistChannel(3);
        /* Drive by vision (plus back up a few inches when done)
        driveByVision = new JoystickButton(gamePad, 1);
        driveByVision.whenPressed(new CreateMotionProfile("/home/lvuser/leftMP.dat", "/home/lvuser/rightMP.dat"));*/


        m_chassis.setDefaultCommand(new Drive(this, m_chassis));
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    public Command getAutonCommand() {
        switch (getAutonSelector()) {
        case 0:
            return new DriveByDistance(m_chassis, m_shifters, 45, Shifters.Speed.kHigh);
        case 1:
            return new DriveByDistance(m_chassis, m_shifters, 112.0, Shifters.Speed.kHigh);
        case 2:
            return new AutoCenterGear(m_chassis, m_shifters, m_pipelineListener);
        case 3: // red boiler
            return new AutoGear(m_chassis, m_shifters, m_pipelineListener, 44.0, TurnToGear.Direction.kLeft); //updated 1:04p 4/47/17
        case 4: // red loader
            return new AutoGear(m_chassis, m_shifters, m_pipelineListener, 55.0, TurnToGear.Direction.kRight);
        case 5: // blue boiler
            return new AutoGear(m_chassis, m_shifters, m_pipelineListener, 44.0, TurnToGear.Direction.kRight); //updated 1:04p 4/47/17
        case 6: // blue loader
            return new AutoGear(m_chassis, m_shifters, m_pipelineListener, 50.0, TurnToGear.Direction.kLeft); //was previously 55.0
        case 7:
            return new AutoShooter(m_shooter, m_loader, m_agitator);
        //case 8:
        //return new DriveByDistance(-3, Shifters.Speed.kLow);
        case 9: //red boiler
            return new AutoBoilerGearAndShoot(m_chassis, m_shifters, m_shooter, m_loader, m_agitator, m_pipelineListener, 44.0, TurnToGear.Direction.kLeft); //updated 1:04p 4/47/17
        case 10: //blue boiler
            return new AutoBoilerGearAndShoot(m_chassis, m_shifters, m_shooter, m_loader, m_agitator, m_pipelineListener, 44.0, TurnToGear.Direction.kRight); //updated 1:04p 4/47/17
        /*case 11:
            return new TurnByDistance(-13.0, -3.0, Shifters.Speed.kLow);
        case 12:
            return new DriveByMotionProfile("/home/lvuser/leftCenterGear.dat", "/home/lvuser/rightCenterGear.dat", 1.0);
        case 13:
            return new AutoShooterAndCrossLine();*/
        case 15:
            return new AutoDoNothing();
        default:
            return new DriveByDistance(m_chassis, m_shifters, 75.5, Shifters.Speed.kLow);
        }
    }

    public double getDrivingJoystickY() {
        if (m_driveStyle == DriveStyle.gamePadArcade) {
            return m_drivingGamePad.getY();
        } else if (m_driveStyle == DriveStyle.joystickArcade) {
            return m_drivingJoystickOne.getY();
        } else if (m_driveStyle == DriveStyle.gamePadTank) {
            return m_drivingGamePad.getY(); //TODO: this should get the Z vertical/rotate value
        } else if (m_driveStyle == DriveStyle.joystickTank) {
            return m_drivingJoystickOne.getY();
        } else {
            return 0.0;
        }
    }

    public double getDrivingJoystickX() {
        if (m_driveStyle == DriveStyle.gamePadArcade) {
            return -m_drivingGamePad.getZ();
        } else if (m_driveStyle == DriveStyle.joystickArcade) {
            return m_drivingJoystickOne.getX();
        } else if (m_driveStyle == DriveStyle.gamePadTank) {
            return m_drivingGamePad.getTwist();
        } else if (m_driveStyle == DriveStyle.joystickTank) {
            return m_drivingJoystickTwo.getY();
        } else {
            return 0.0;
        }
    }


    public double getScaledJoystickValue(double input) {
        double output = 0;

        if (m_joystickScale == JoystickScaling.linear) {
            output = input;
        } else if (m_joystickScale == JoystickScaling.deadband) {
            if (Math.abs(input) < DEADBAND) {
                output = 0;
            } else {
                if (input > 0) {
                    output = input - DEADBAND;
                } else {
                    output = input + DEADBAND;
                }
            }
        } else if (m_joystickScale == JoystickScaling.quadratic) {
            if (input > 0) {
                output = Math.pow(input, 2);
            } else {
                output = -1 * Math.pow(input, 2);
            }
        }

        return output;
    }

    public void setDriveDirection(DriveDirection driveDirection) {
        this.m_driveDirection = driveDirection;
        System.out.println("Drive direction set to: " + driveDirection);
    }

    public void setJoystickScale(JoystickScaling joystickScale) {
        this.m_joystickScale = joystickScale;
        System.out.println("Joystick direction set to: " + joystickScale);
    }

    public boolean isJoystickReversed() {
        return m_driveDirection == DriveDirection.kREV;
    }

    public void setDriveStyle() {
        if (!m_dio1.get()) {
            m_driveStyle = DriveStyle.joystickArcade;
        } else if (!m_dio2.get()) {
            m_driveStyle = DriveStyle.gamePadArcade;
        } else if (!m_dio3.get()) {
            m_driveStyle = DriveStyle.joystickTank;
        } else if (!m_dio4.get()) {
            m_driveStyle = DriveStyle.gamePadTank;
        } else {
            System.out.println("NO DRIVE MODE SELECTED. \nDefaulting to Joystick Arcade...");
            m_driveStyle = DriveStyle.joystickArcade;
        }
        System.out.println("Drive Mode: " + m_driveStyle);
    }

    public DriveStyle getDriveStyle() {
        return m_driveStyle;
    }

    public boolean isSquared() {
        return !m_dio0.get();
    }

    /**
     * Get Autonomous Mode Selector
     * <p>
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
