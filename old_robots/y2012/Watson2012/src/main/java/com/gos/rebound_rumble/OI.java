package com.gos.rebound_rumble;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import com.gos.rebound_rumble.commands.BridgeDown;
import com.gos.rebound_rumble.commands.BridgeUp;
import com.gos.rebound_rumble.commands.Collect;
import com.gos.rebound_rumble.commands.DisableChassis;
import com.gos.rebound_rumble.commands.DisableShooter;
import com.gos.rebound_rumble.commands.DisableTurret;
import com.gos.rebound_rumble.commands.DriveSlowTurning;
import com.gos.rebound_rumble.commands.DriveSlowVelocity;
import com.gos.rebound_rumble.commands.HoldPosition;
import com.gos.rebound_rumble.commands.PS3ManualTurret;
import com.gos.rebound_rumble.commands.ReverseCollectors;
import com.gos.rebound_rumble.commands.ReverseTopMiddleRollers;
import com.gos.rebound_rumble.commands.Shoot;
import com.gos.rebound_rumble.commands.ShootUsingTable;
import com.gos.rebound_rumble.commands.StopCollectors;
import com.gos.rebound_rumble.commands.TurretTrackTarget;
import com.gos.rebound_rumble.subsystems.Bridge;
import com.gos.rebound_rumble.subsystems.Chassis;
import com.gos.rebound_rumble.subsystems.Collector;
import com.gos.rebound_rumble.subsystems.Shooter;
import com.gos.rebound_rumble.subsystems.Turret;

@SuppressWarnings({"PMD.GodClass", "PMD.TooManyFields", "PMD.NcssCount", "PMD.CognitiveComplexity", "PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
public class OI {

    //driving scales (percentage of max power)
    private static final double NORMAL_DRIVE = 1.0;
    private static final double MEDIUM_DRIVE = 0.75;
    private static final double SLOW_DRIVE = 0.55;

    private static final double HALF_TURNING = 0.5;
    private static final double MEDIUM_TURNING = 0.75;

    private static final int DRIVER_JOYSTICK_PORT = 1;
    private static final int DISABLE_CHASSIS_BUTTON_LEFT = 5; //L1 button on driverJoystick
    private static final int DISABLE_CHASSIS_BUTTON_RIGHT = 6; //R1 button on driverJoystick
    private static final int NORMAL_DRIVE_JAGS_BUTTON = 7; //L2 button on driverJoystick
    private static final int MEDIUM_DRIVE_JAGS_BUTTON = 8; //R2
    private static final int SLOW_DRIVE_JAGS_BUTTON = 3; //circle
    private static final int DRIVE_SLOW_VELOCITY_BUTTON = 10; //start
    private static final int BRIDGE_ARM_UP_BUTTON = 4; //triangle button on driverJoystick
    private static final int BRIDGE_ARM_DOWN_BUTTON = 2; //x
    private static final int HOLD_POSITION_BUTTON = 1; //square

    //TODO find out what the driver buttons should be
    //driver joystick (PS3)
    private final Joystick m_driverJoystick;
    private final JoystickButton m_disableChassisLeft;
    private final JoystickButton m_disableChassisRight;
    private final JoystickButton m_normalDriveJags;
    private final JoystickButton m_mediumDriveJags;
    private final JoystickButton m_slowDriveJags;
    private final JoystickButton m_driveSlowVelocity;
    private final JoystickButton m_bridgeArmUp;
    private final JoystickButton m_bridgeArmDown;
    private final JoystickButton m_holdPosition;

    //TODO find out the buttons desired for the operator
    //operator controller (PS3)
    private static final int OPERATOR_JOYSTICK_PORT = 2;
    private static final int DISABLE_SHOOTER_BUTTON = 8; //R2 on operatorJoystick
    private static final int AUTO_SHOOT_BUTTON = 6; //R1 on operatorJoystick
    private static final int TURRET_OVERRIDE_BUTTON = 5; //L1 on operatorJoystick
    private static final int DISABLE_TURRET_BUTTON = 7; //L2
    private static final int RESTART_TURRET_TRACKING_BUTTON = 10; //start on operatorJoystick
    private static final int COLLECT_BALLS_BUTTON = 2; //x button on operatorJoystick
    private static final int STOP_COLLECTOR_BUTTON = 3; //circle on operatorJoystick
    private static final int COLLECTORS_REVERSE_BUTTON = 4; //triangle
    private static final int SHOOT_FROM_KEY_BUTTON = 1; //square
    private static final int REVERSE_TOP_MIDDLE_ROLLERS_BUTTON = 9; //select

    private final Joystick m_operatorJoystick;
    private final JoystickButton m_disableShooter;
    private final JoystickButton m_autoShoot;
    private final JoystickButton m_turretOverride;
    private final JoystickButton m_disableTurret;
    private final JoystickButton m_restartTurretTracking;
    private final JoystickButton m_collectBalls;
    private final JoystickButton m_stopCollector;
    private final JoystickButton m_collectorsReverse;
    private final JoystickButton m_shootFromKey;
    private final JoystickButton m_reverseTopMiddleRollers;

    //buttons
    private static final int AUTO_SHOOT_PHYSICAL_BUTTON = 9;
    private static final int STOP_SHOOTER_PHYSICAL_BUTTON = 16;
    //switches
    private static final int TURRET_OVERRIDE_SWITCH_A = 11;
    private static final int TURRET_OVERRIDE_SWITCH_B = 15;
    private static final int SHOOTER_OVERRIDE_SWITCH_A = 10;
    private static final int SHOOTER_OVERRIDE_SWITCH_B = 12;
    private static final int BRUSH_SWITCH_A = 6;
    private static final int BRUSH_SWITCH_B = 8;
    private static final int MIDDLE_COLLECTOR_SWITCH_A = 2;
    private static final int MIDDLE_COLLECTOR_SWITCH_B = 4;
    private static final int TOP_ROLLERS_SWITCH_A = 1;
    private static final int TOP_ROLLERS_SWITCH_B = 3;
    //slider
    private static final int SHOOTER_SLIDER = 6;
    //TODO can we read from the knob without the flicking?
    //counter
    private static final int AUTONOMOUS_COUNTER_ONE = 1;
    private static final int AUTONOMOUS_COUNTER_TWO = 2;
    private static final int AUTONOMOUS_COUNTER_THREE = 3;
    private static final int AUTONOMOUS_COUNTER_FIVE = 5;
    private static final int AUTONOMOUS_COUNTER_SEVEN = 7;
    //lights -> TODO figure out lights (digital input numbers & how to use)

    //buttons
    //shooter
    private boolean m_autoShootRunning;
    private boolean m_stopShooterRunning;
    private int m_currValue;
    private int m_preValue;

    private static final int ENHANCED_IO_JOYSTICK_PORT = 3;
    private final Joystick m_enhancedIoJoystick;

    //  Default Constructor
    public OI(Chassis chassis, Shooter shooter, Collector collector, Turret turret, Bridge bridge) {
        m_enhancedIoJoystick = new Joystick(ENHANCED_IO_JOYSTICK_PORT);

        m_driverJoystick = new Joystick(DRIVER_JOYSTICK_PORT);

        m_disableChassisLeft = new JoystickButton(m_driverJoystick, DISABLE_CHASSIS_BUTTON_LEFT);
        m_disableChassisRight = new JoystickButton(m_driverJoystick, DISABLE_CHASSIS_BUTTON_RIGHT);
        m_normalDriveJags = new JoystickButton(m_driverJoystick, NORMAL_DRIVE_JAGS_BUTTON);
        m_slowDriveJags = new JoystickButton(m_driverJoystick, SLOW_DRIVE_JAGS_BUTTON);
        m_mediumDriveJags = new JoystickButton(m_driverJoystick, MEDIUM_DRIVE_JAGS_BUTTON);
        m_driveSlowVelocity = new JoystickButton((m_driverJoystick), DRIVE_SLOW_VELOCITY_BUTTON);
        m_bridgeArmDown = new JoystickButton(m_driverJoystick, BRIDGE_ARM_DOWN_BUTTON);
        m_bridgeArmUp = new JoystickButton(m_driverJoystick, BRIDGE_ARM_UP_BUTTON);
        m_holdPosition = new JoystickButton(m_driverJoystick, HOLD_POSITION_BUTTON);

        m_disableChassisLeft.whenPressed(new DisableChassis(chassis));
        m_disableChassisRight.whenPressed(new DisableChassis(chassis));
        m_normalDriveJags.whenPressed(new DriveSlowTurning(chassis, m_driverJoystick, NORMAL_DRIVE, HALF_TURNING));
        m_slowDriveJags.whenPressed(new DriveSlowTurning(chassis, m_driverJoystick, SLOW_DRIVE, HALF_TURNING));
        m_mediumDriveJags.whenPressed((new DriveSlowTurning(chassis, m_driverJoystick, MEDIUM_DRIVE, MEDIUM_TURNING)));
        m_driveSlowVelocity.whenPressed(new DriveSlowVelocity(chassis, m_driverJoystick)); //at the momement
        //doesn't work -> not really necessary (not used for bridge really)
        m_bridgeArmDown.whenPressed(new BridgeDown(bridge));
        m_bridgeArmUp.whenPressed(new BridgeUp(bridge));
        m_holdPosition.whileHeld(new HoldPosition(chassis));
        m_holdPosition.whenReleased(new DriveSlowTurning(chassis, m_driverJoystick, SLOW_DRIVE, HALF_TURNING));

        m_operatorJoystick = new Joystick(OPERATOR_JOYSTICK_PORT);

        m_collectBalls = new JoystickButton(m_operatorJoystick, COLLECT_BALLS_BUTTON);
        m_collectorsReverse = new JoystickButton(m_operatorJoystick, COLLECTORS_REVERSE_BUTTON);
        m_stopCollector = new JoystickButton(m_operatorJoystick, STOP_COLLECTOR_BUTTON);
        m_turretOverride = new JoystickButton(m_operatorJoystick, TURRET_OVERRIDE_BUTTON);
        m_restartTurretTracking = new JoystickButton(m_operatorJoystick, RESTART_TURRET_TRACKING_BUTTON);
        m_autoShoot = new JoystickButton(m_operatorJoystick, AUTO_SHOOT_BUTTON);
        m_disableShooter = new JoystickButton(m_operatorJoystick, DISABLE_SHOOTER_BUTTON);
        m_disableTurret = new JoystickButton(m_operatorJoystick, DISABLE_TURRET_BUTTON);
        m_shootFromKey = new JoystickButton(m_operatorJoystick, SHOOT_FROM_KEY_BUTTON);
        m_reverseTopMiddleRollers = new JoystickButton(m_operatorJoystick, REVERSE_TOP_MIDDLE_ROLLERS_BUTTON);

        //TODO ask drive team if they want to switch some of these to "while held" functions
        //maybe shooting?
        m_collectBalls.whenPressed(new Collect(collector));
        m_collectorsReverse.whenPressed(new ReverseCollectors(collector));
        m_stopCollector.whenPressed(new StopCollectors(collector));
        m_turretOverride.whenPressed(new PS3ManualTurret(turret, m_operatorJoystick));
        m_restartTurretTracking.whenPressed(new TurretTrackTarget(turret, m_operatorJoystick));
        m_autoShoot.whileHeld(new ShootUsingTable(shooter, this, true));
        m_disableShooter.whenPressed(new DisableShooter(shooter));
        m_disableTurret.whenPressed(new DisableTurret(turret));
        m_shootFromKey.whileHeld(new Shoot(shooter, this, 24.0)); //dead-reckoning from key
        m_reverseTopMiddleRollers.whileHeld(new ReverseTopMiddleRollers(collector, shooter));
    }

    public Joystick getDriverJoystick() {
        return m_driverJoystick;
    }

    public Joystick getOperatorJoystick() {
        return m_operatorJoystick;
    }

    public boolean isCollectCameraDataPressed() {
        //        if(operatorJoystick.getRawButton(COLLECT_CAMERA_DATA)){
        return true;
    }

    public boolean isShootRunning() {
        if (m_enhancedIoJoystick.getRawButton(AUTO_SHOOT_PHYSICAL_BUTTON)) {
            m_stopShooterRunning = false;
            m_autoShootRunning = true;
            System.out.println("Stop Shoot:" + m_stopShooterRunning);
            System.out.println("Shoot:" + m_autoShootRunning);
        }
        return m_autoShootRunning;
    }

    public boolean isStopShooterRunning() {
        if (m_enhancedIoJoystick.getRawButton(STOP_SHOOTER_PHYSICAL_BUTTON)) {
            m_stopShooterRunning = true;
            m_autoShootRunning = false;
            System.out.println("Stop Shoot:" + m_stopShooterRunning);
            System.out.println("Shoot:" + m_autoShootRunning);
        }
        return m_stopShooterRunning;
    }

    //This button is an analog(because we ran out of digital) so I changed the following function.
    public boolean areTopRollersOverriden() {
        //        double switchValue = 0.0;
        //        try {
        //            switchValue = m_enhancedIoJoystick.getRawAxis(TOP_ROLLERS_OVERRIDE_SWITCH);
        //        } catch (EnhancedIOException ex) {
        //            ex.printStackTrace();
        //        }
        //        //System.out.println("Top Rollers switch:" + switchValue);
        //        if (switchValue < 0) {
        //            return true;
        //        } else {
        //            return false;
        //        }
        return false;
    }

    public boolean isButtonPressed(int buttonNumber) {
        return m_enhancedIoJoystick.getRawButton(buttonNumber);
    }

    //switches
    public int getTurretOverrideSwitchValue() {
        //1 -> auto
        //2 -> set position
        //3 -> manual
        return getSwitchValue(TURRET_OVERRIDE_SWITCH_A, TURRET_OVERRIDE_SWITCH_B);
    }

    public int getShooterOverrideSwitchValue() {
        //1 -> auto
        //2 -> increment (increase/decrease speed)
        //3 -> manual
        return getSwitchValue(SHOOTER_OVERRIDE_SWITCH_A, SHOOTER_OVERRIDE_SWITCH_B);
    }

    public int getBrushSwitchValue() {
        //1 -> forward
        //2 -> off
        //3 -> reverse
        return getSwitchValue(BRUSH_SWITCH_A, BRUSH_SWITCH_B);
    }

    public int getMiddleCollectorSwitchValue() {
        //1 -> forward
        //2 -> off
        //3 -> reverse
        return getSwitchValue(MIDDLE_COLLECTOR_SWITCH_A, MIDDLE_COLLECTOR_SWITCH_B);
    }

    public int getTopRollersSwitchValue() { //only after override
        //1 -> forward
        //2 -> off
        //3 -> reverse
        return getSwitchValue(TOP_ROLLERS_SWITCH_A, TOP_ROLLERS_SWITCH_B);
    }

    private int getSwitchValue(int channelA, int channelB) {
        boolean digitalInputA = m_enhancedIoJoystick.getRawButton(channelA);
        boolean digitalInputB = m_enhancedIoJoystick.getRawButton(channelB);

        int switchValue;
        if (digitalInputA && !digitalInputB) {
            switchValue = 3;
        } else if (!digitalInputA && !digitalInputB) {
            switchValue = 2;
        } else if (!digitalInputA && digitalInputB) {
            switchValue = 1;
        } else { //THIS SHOULD NEVER HAPPEN.
            switchValue = 4;
            System.out.println("ERROR WITH THE SWITCH VALUE");
        }
        return switchValue;
    }

    //switch -- turret
    public boolean isTurretAutoOn() {
        return getTurretOverrideSwitchValue() == 2;
    }

    public boolean isTurretSetPositionOn() {
        return getTurretOverrideSwitchValue() == 1;
    }

    public boolean isTurretManualOn() {
        return getTurretOverrideSwitchValue() == 3;
    }

    //switch -- shooter
    public boolean isShooterAutoOn() {
        return getShooterOverrideSwitchValue() == 2;
    }

    public boolean isShooterIncrementOn() {
        return getShooterOverrideSwitchValue() == 1;
    }

    public boolean isShooterManualOn() {
        return getShooterOverrideSwitchValue() == 3;
    }

    //rollers
    public boolean isBrushForward() {
        return getBrushSwitchValue() == 1;
    }

    public boolean isBrushOff() {
        return getBrushSwitchValue() == 2;
    }

    public boolean isBrushReverse() {
        return getBrushSwitchValue() == 3;
    }

    public boolean isMiddleCollectorForward() {
        return getMiddleCollectorSwitchValue() == 1;
    }

    public boolean isMiddleCollectorOff() {
        return getMiddleCollectorSwitchValue() == 2;
    }

    public boolean isMiddleCollectorReverse() {
        return getMiddleCollectorSwitchValue() == 3;
    }

    public boolean areTopRollersForward() {
        return getTopRollersSwitchValue() == 1;
    }

    public boolean areTopRollersOff() {
        return getTopRollersSwitchValue() == 2;
    }

    public boolean areTopRollersReverse() {
        return getTopRollersSwitchValue() == 3;
    }

    //slider
    public double getShooterSliderValue() {
        return m_enhancedIoJoystick.getRawAxis(SHOOTER_SLIDER);
    }

    //knob
    public double getTurretKnobValue(double deadzone) {
        m_currValue = (int) (100 * m_enhancedIoJoystick.getRawAxis(1));
        double returnVal;

        if (m_currValue - m_preValue > deadzone) {
            returnVal = -5.0;
        } else if (m_currValue - m_preValue < -deadzone) {
            returnVal = 5.0;
        } else {
            returnVal = 0.0;
        }
        m_preValue = m_currValue;
        return returnVal;
    }

    /*
     * Autonomous Counter Analog Values:
     *
     * 1: A1
     * 2: A3
     * 3: A1 & A3
     * 4: A5
     * 5: A1 & A5
     * 6: A3 & A5
     * 7: A1 & A3 & A5
     * 8: A7
     * 9: A1 & A7
     * 10: A2
     * 11: A1 & A2
     * 12: A2 & A3
     * 13: A1 & A2 & A3
     * 14: A2 & A5
     * 15: A1 & A2 & A5
     * NOTE: A = analog input
     */
    public int getAutonomousCounterValue() {
        boolean auto1 = changeAnalogs(m_enhancedIoJoystick.getRawAxis(AUTONOMOUS_COUNTER_ONE));
        boolean auto2 = changeAnalogs(m_enhancedIoJoystick.getRawAxis(AUTONOMOUS_COUNTER_TWO));
        boolean auto3 = changeAnalogs(m_enhancedIoJoystick.getRawAxis(AUTONOMOUS_COUNTER_THREE));
        boolean auto5 = changeAnalogs(m_enhancedIoJoystick.getRawAxis(AUTONOMOUS_COUNTER_FIVE));
        boolean auto7 = changeAnalogs(m_enhancedIoJoystick.getRawAxis(AUTONOMOUS_COUNTER_SEVEN));

        int autoNumber = 0;

        if (auto1 && !auto2 && !auto3 && !auto5 && !auto7) {
            autoNumber = 1;
        }
        if (!auto1 && !auto2 && auto3 && !auto5 && !auto7) {
            autoNumber = 2;
        } else if (auto1 && !auto2 && auto3 && !auto5 && !auto7) {
            autoNumber = 3;
        } else if (!auto1 && !auto2 && !auto3 && auto5 && !auto7) {
            autoNumber = 4;
        } else if (auto1 && !auto2 && !auto3 && auto5 && !auto7) {
            autoNumber = 5;
        } else if (!auto1 && !auto2 && auto3 && auto5 && !auto7) {
            autoNumber = 6;
        } else if (auto1 && !auto2 && auto3 && auto5 && !auto7) {
            autoNumber = 7;
        } else if (!auto1 && !auto2 && !auto3 && !auto5 && auto7) {
            autoNumber = 8;
        } else if (auto1 && !auto2 && !auto3 && !auto5 && auto7) {
            autoNumber = 9;
        } else if (!auto1 && auto2 && !auto3 && !auto5 && !auto7) {
            autoNumber = 10;
        } else if (auto1 && auto2 && !auto3 && !auto5 && !auto7) {
            autoNumber = 11;
        } else if (!auto1 && auto2 && auto3 && !auto5 && !auto7) {
            autoNumber = 12;
        } else if (auto1 && auto2 && auto3 && !auto5 && !auto7) {
            autoNumber = 13;
        } else if (!auto1 && auto2 && !auto3 && auto5 && !auto7) {
            autoNumber = 14;
        } else if (auto1 && auto2 && !auto3 && auto5 && !auto7) {
            autoNumber = 15;
        }

        System.out.println("auto1: " + auto1 + "auto2: " + auto2 + "auto3: " + auto3 + "auto5: " + auto5 + "auto7: " + auto7);
        return autoNumber;

    }

    private boolean changeAnalogs(double analogValue) {
        return analogValue > 0.5;
    }

    //If ever used, change line numbers in the driver station prints
    public void printAnalogAutonomous() {
        System.out.println("Counter 1:" + m_enhancedIoJoystick.getRawAxis(AUTONOMOUS_COUNTER_ONE));
        System.out.println("Counter 2:" + m_enhancedIoJoystick.getRawAxis(AUTONOMOUS_COUNTER_TWO));
        System.out.println("Counter 3:" + m_enhancedIoJoystick.getRawAxis(AUTONOMOUS_COUNTER_THREE));
        System.out.println("Counter 5:" + m_enhancedIoJoystick.getRawAxis(AUTONOMOUS_COUNTER_FIVE));
        System.out.println("Counter 7:" + m_enhancedIoJoystick.getRawAxis(AUTONOMOUS_COUNTER_SEVEN));
    }
}
