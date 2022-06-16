package com.gos.steam_works;

import com.gos.steam_works.commands.DriveJoystickArcadeGamepad;
import com.gos.steam_works.commands.DriveJoystickArcadeOneStick;
import com.gos.steam_works.commands.DriveJoystickTankGamepad;
import com.gos.steam_works.commands.DriveJoystickTankTwoStick;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.gos.steam_works.commands.Climb;
import com.gos.steam_works.commands.CombinedShoot;
import com.gos.steam_works.commands.CombinedShootGear;
import com.gos.steam_works.commands.CombinedShootKey;
import com.gos.steam_works.commands.DecrementHighShooter;
import com.gos.steam_works.commands.DriveByDistance;
import com.gos.steam_works.commands.IncrementHighShooter;
import com.gos.steam_works.commands.ShiftDown;
import com.gos.steam_works.commands.ShiftUp;
import com.gos.steam_works.commands.SwitchBackward;
import com.gos.steam_works.commands.SwitchForward;
import com.gos.steam_works.commands.TurnToGear;
import com.gos.steam_works.commands.UnClimb;
import com.gos.steam_works.commands.autonomous.AutoBoilerGearAndShoot;
import com.gos.steam_works.commands.autonomous.AutoCenterGear;
import com.gos.steam_works.commands.autonomous.AutoDoNothing;
import com.gos.steam_works.commands.autonomous.AutoGear;
import com.gos.steam_works.commands.autonomous.AutoShooter;
import com.gos.steam_works.subsystems.Agitator;
import com.gos.steam_works.subsystems.Camera;
import com.gos.steam_works.subsystems.Chassis;
import com.gos.steam_works.subsystems.Climber;
import com.gos.steam_works.subsystems.Loader;
import com.gos.steam_works.subsystems.Shifters;
import com.gos.steam_works.subsystems.Shooter;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
@SuppressWarnings({"PMD.GodClass", "PMD.TooManyFields", "PMD.ExcessiveMethodLength", "PMD.CyclomaticComplexity", "PMD.NcssCount", "PMD.NPathComplexity"})
public class OI {

    //Drive Styles
    //gamepad: tank, split arcade
    //joystick: tank, one stick arcade
    public enum DriveStyle {
        ONE_STICK_ARCADE, GAME_PAD_ARCADE, TWO_STICK_TANK, GAME_PAD_TANK, DROPERATION
    }

    private static final DriveStyle DRIVE_STYLE = DriveStyle.ONE_STICK_ARCADE;

    private Joystick m_drivingStickForward;
    private Joystick m_drivingStickBackward;
    private Joystick m_drivingStickRight;
    private Joystick m_drivingStickLeft;
    private XboxController m_drivingGamePad;
    private final Joystick m_operatingGamePad;
    private final Joystick m_autonSelector;

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

        if (DRIVE_STYLE == DriveStyle.ONE_STICK_ARCADE) {
            m_drivingStickForward = new Joystick(1);
            m_drivingStickBackward = new Joystick(2);
        } else if (DRIVE_STYLE == DriveStyle.GAME_PAD_ARCADE) {
            m_drivingGamePad = new XboxController(1);
        } else if (DRIVE_STYLE == DriveStyle.TWO_STICK_TANK) {
            m_drivingStickRight = new Joystick(1);
            m_drivingStickLeft = new Joystick(2);
        } else if (DRIVE_STYLE == DriveStyle.GAME_PAD_TANK) {
            m_drivingGamePad = new XboxController(1);
        } else if (DRIVE_STYLE == DriveStyle.DROPERATION) {
            m_drivingGamePad = new XboxController(1);
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

        if (DRIVE_STYLE == DriveStyle.ONE_STICK_ARCADE) {
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
            m_switchToForward.whenPressed(new SwitchForward(m_chassis, m_camera));
            m_switchToBackward.whenPressed(new SwitchBackward(m_chassis, m_camera));
            // Buttons for shifters
            m_shifterDown.whenPressed(new ShiftDown(m_shifters));
            m_shifterUp.whenPressed(new ShiftUp(m_shifters));
        } else if (DRIVE_STYLE == DriveStyle.GAME_PAD_ARCADE) {
            //DRIVER BUTTONS
            // Buttons for shifters copied to both joysticks
            m_shifterDown = new JoystickButton(m_drivingGamePad, 1); //TODO: change button value
            m_shifterUp = new JoystickButton(m_drivingGamePad, 11); //TODO: change button value
        } else if (DRIVE_STYLE == DriveStyle.TWO_STICK_TANK) {
            //DRIVER BUTTONS
            // Buttons for shifters copied to both joysticks
            m_shifterDown = new JoystickButton(m_drivingStickLeft, 2);
            m_shifterUp = new JoystickButton(m_drivingStickLeft, 3);
        } else if (DRIVE_STYLE == DriveStyle.GAME_PAD_TANK) {
            //DRIVER BUTTONS
            // Buttons for shifters copied to both joysticks
            m_shifterDown = new JoystickButton(m_drivingGamePad, 1); //TODO: change button value
            m_shifterUp = new JoystickButton(m_drivingGamePad, 11); //TODO: change button value
        } else if (DRIVE_STYLE == DriveStyle.DROPERATION) {
            m_shifterDown = new JoystickButton(m_operatingGamePad, 4); //Y
            m_shifterUp = new JoystickButton(m_operatingGamePad, 2); // B
            m_climb = new JoystickButton(m_operatingGamePad, 8); //START
            m_unClimb = new JoystickButton(m_operatingGamePad, 7);  // BACK
            m_shoot = new JoystickButton(m_operatingGamePad, 3); // X
        }

        /* Drive by vision (plus back up a few inches when done)
        driveByVision = new JoystickButton(gamePad, 1);
        driveByVision.whenPressed(new CreateMotionProfile("/home/lvuser/leftMP.dat", "/home/lvuser/rightMP.dat"));*/

        // Default commands
        switch (DRIVE_STYLE) {
        case ONE_STICK_ARCADE:
            m_chassis.setDefaultCommand(new DriveJoystickArcadeOneStick(m_drivingStickRight, m_chassis));
            break;
        case GAME_PAD_ARCADE:
            m_chassis.setDefaultCommand(new DriveJoystickArcadeGamepad(m_drivingGamePad, m_chassis));
            break;
        case TWO_STICK_TANK:
            m_chassis.setDefaultCommand(new DriveJoystickTankTwoStick(m_drivingStickRight, m_drivingStickLeft, m_chassis));
            break;
        case GAME_PAD_TANK:
            m_chassis.setDefaultCommand(new DriveJoystickTankGamepad(m_drivingGamePad, m_chassis));
            break;
        case DROPERATION:
        default:
            throw new IllegalArgumentException();
        }
    }

    public DriveStyle getDriveStyle() {
        return DRIVE_STYLE;
    }

    public Command getAutonCommand() {

        switch (getAutonSelector()) {
        case 0:
            return new DriveByDistance(m_chassis, m_shifters, 45, Shifters.Speed.HIGH);
        case 1:
            return new DriveByDistance(m_chassis, m_shifters, 112.0, Shifters.Speed.HIGH);
        case 2:
            return new AutoCenterGear(m_chassis, m_shifters, m_camera);
        case 3: // red boiler
            return new AutoGear(m_chassis, m_shifters, m_camera, 44.0, TurnToGear.Direction.LEFT); //updated 1:04p 4/47/17
        case 4: // red loader
            return new AutoGear(m_chassis, m_shifters, m_camera, 55.0, TurnToGear.Direction.RIGHT);
        case 5: // blue boiler
            return new AutoGear(m_chassis, m_shifters, m_camera, 44.0, TurnToGear.Direction.RIGHT); //updated 1:04p 4/47/17
        case 6: // blue loader
            return new AutoGear(m_chassis, m_shifters, m_camera, 50.0, TurnToGear.Direction.LEFT); //was previously 55.0
        case 7:
            return new AutoShooter(m_agitator, m_shooter, m_loader);
        //case 8:
        //return new DriveByDistance(-3, Shifters.Speed.kLow);
        case 9: //red boiler
            return new AutoBoilerGearAndShoot(m_chassis, m_shifters, m_agitator, m_shooter, m_loader, m_camera, 44.0, TurnToGear.Direction.LEFT); //updated 1:04p 4/47/17
        case 10: //blue boiler
            return new AutoBoilerGearAndShoot(m_chassis, m_shifters, m_agitator, m_shooter, m_loader, m_camera, 44.0, TurnToGear.Direction.RIGHT); //updated 1:04p 4/47/17
        /*case 11:
            return new TurnByDistance(-13.0, -3.0, Shifters.Speed.kLow);
        case 12:
            return new DriveByMotionProfile("/home/lvuser/leftCenterGear.dat", "/home/lvuser/rightCenterGear.dat", 1.0);
        case 13:
            return new AutoShooterAndCrossLine();*/
        case 15:
            return new AutoDoNothing(m_chassis);
        default:
            return new DriveByDistance(m_chassis, m_shifters, 75.5, Shifters.Speed.LOW);
        }
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
