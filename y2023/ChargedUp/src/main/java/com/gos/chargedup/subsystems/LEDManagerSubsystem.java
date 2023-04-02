package com.gos.chargedup.subsystems;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.Constants;
import com.gos.chargedup.autonomous.AutonomousFactory;
import com.gos.lib.led.LEDBoolean;
import com.gos.lib.led.LEDFlash;
import com.gos.lib.led.LEDMovingPixel;
import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.LEDPatternLookup;
import com.gos.lib.led.LEDPercentScale;
import com.gos.lib.led.LEDRainbow;
import com.gos.lib.led.LEDSolidColor;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.HashMap;
import java.util.Map;

public class LEDManagerSubsystem extends SubsystemBase {

    // Colors for auto nodes
    private static final Color NODE_0_COLOR = Color.kRed;
    private static final Color NODE_1_COLOR = Color.kOrange;
    private static final Color NODE_2_COLOR = Color.kYellow;
    private static final Color NODE_3_COLOR = Color.kGreen;
    private static final Color NODE_4_COLOR = Color.kBlack;
    private static final Color NODE_5_COLOR = Color.kIndigo;
    private static final Color NODE_6_COLOR = Color.kViolet;
    private static final Color NODE_7_COLOR = Color.kPapayaWhip;
    private static final Color NODE_8_COLOR = Color.kDarkCyan;

    private static final int MAX_INDEX_LED = 30;

    private static final int AUTO_HEIGHT_START = 0;
    private static final int AUTO_HEIGHT_COUNT = MAX_INDEX_LED / 4;

    private static final int AUTO_MODE_START = AUTO_HEIGHT_COUNT;

    private static final int AUTO_MODE_END = AUTO_MODE_START + MAX_INDEX_LED / 4;

    private static final int CLAW_HOLD_WAIT_TIME = 1;


    // subsystems
    //private final CommandXboxController m_joystick;
    private final ChassisSubsystem m_chassisSubsystem;
    private final ArmPivotSubsystem m_armSubsystem;

    private final ClawSubsystem m_claw;

    // Led core
    protected final AddressableLEDBuffer m_buffer;
    protected final AddressableLED m_led;

    // Testing LED
    //private final MirroredLEDPercentScale m_drivetrainSpeed;

    // Comp LED
    private final LEDFlash m_coneGamePieceSignal;
    private final LEDFlash m_cubeGamePieceSignal;

    private boolean m_optionConeLED;
    private boolean m_optionCubeLED;
    private boolean m_optionDockLED;
    private boolean m_clawIsAligned;

    private final LEDFlash m_readyToScore;
    private final LEDBoolean m_armAtAngle;

    private final LEDPercentScale m_dockAngle;
    private final LEDFlash m_isEngaged;
    private static final double ALLOWABLE_ERROR_ENGAGE = 2;

    private final LEDRainbow m_notInCommunityZone;

    private final AutonomousFactory m_autoModeFactory;

    private final LEDPatternLookup<AutonomousFactory.AutonMode> m_autoModeColor;
    private final LEDPatternLookup<AutoPivotHeight> m_heightColor;
    private final LEDFlash m_autoResetArmAtAngle;

    private final LEDFlash m_clawAlignedSignal;

    private final LEDFlash m_isInLoadingZoneSignal;

    private final LEDFlash m_isHoldingPieceClaw;

    private final Timer m_clawLEDsTimer = new Timer();

    private boolean m_clawWasTripped;


    public LEDManagerSubsystem(ChassisSubsystem chassisSubsystem, ArmPivotSubsystem armSubsystem, ClawSubsystem claw, AutonomousFactory autonomousFactory) {
        m_autoModeFactory = autonomousFactory;

        m_chassisSubsystem = chassisSubsystem;
        m_armSubsystem = armSubsystem;
        m_claw = claw;

        m_buffer = new AddressableLEDBuffer(MAX_INDEX_LED);
        m_led = new AddressableLED(Constants.LED_PORT);

        // Test LED
        //m_drivetrainSpeed = new MirroredLEDPercentScale(m_buffer, 0, MAX_INDEX_LED, Color.kGreen, 15);

        // Comp LED
        m_coneGamePieceSignal = new LEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kYellow);
        m_cubeGamePieceSignal = new LEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kMediumPurple);

        m_readyToScore = new LEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kGreen);
        //m_turretAngle = new MirroredLEDPercentScale(m_buffer, 20, 10, Color.kRed, 20);
        m_armAtAngle = new LEDBoolean(m_buffer, 10, 10, Color.kAntiqueWhite, Color.kRed);
        //m_goodDistance = new MirroredLEDBoolean(m_buffer, 0, 10, Color.kAntiqueWhite, Color.kRed);

        //m_goodDistToLoadingPiece = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kGreen);

        m_isInLoadingZoneSignal = new LEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kCornflowerBlue);

        m_dockAngle = new LEDPercentScale(m_buffer, 0, MAX_INDEX_LED, Color.kRed, 30);
        m_optionDockLED = false;
        m_isEngaged = new LEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kGreen);

        m_notInCommunityZone = new LEDRainbow(m_buffer, 0, MAX_INDEX_LED);

        // no piece - none
        // one piece - solid
        // one piece + grab second - flash
        // one piece + grab second + engage - flash faster
        // two piece - single light

        Map<AutonomousFactory.AutonMode, LEDPattern> autonColorMap = new HashMap<>();

        addOnePieceAndEngageAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_ENGAGE_3, NODE_3_COLOR);
        addOnePieceAndEngageAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_ENGAGE_4, NODE_4_COLOR);
        addOnePieceAndEngageAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_ENGAGE_5, NODE_5_COLOR);

        addOnePieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_0, NODE_0_COLOR);
        addOnePieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_1, NODE_1_COLOR);
        addOnePieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_2, NODE_2_COLOR);
        addOnePieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_3, NODE_3_COLOR);
        addOnePieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_5, NODE_5_COLOR);
        addOnePieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_6, NODE_6_COLOR);
        addOnePieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_7, NODE_7_COLOR);
        addOnePieceAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_NODE_AND_LEAVE_COMMUNITY_8, NODE_8_COLOR);

        addOnePieceGrabSecondAndEngageAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_PIECE_LEAVE_COMMUNITY_ENGAGE_WITH_SECOND_4, NODE_4_COLOR);
        addOnePieceGrabSecondAndEngageAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_PIECE_LEAVE_COMMUNITY_ENGAGE_WITH_SECOND_3, NODE_3_COLOR);
        addOnePieceGrabSecondAndEngageAuto(autonColorMap, AutonomousFactory.AutonMode.ONE_PIECE_LEAVE_COMMUNITY_ENGAGE_WITH_SECOND_5, NODE_5_COLOR);

        addTwoPieceAuto(autonColorMap, AutonomousFactory.AutonMode.TWO_PIECE_NODE_0_AND_1, NODE_0_COLOR);
        addTwoPieceAuto(autonColorMap, AutonomousFactory.AutonMode.TWO_PIECE_NODE_7_AND_8, NODE_7_COLOR);

        // no scoring -- solid red
        // low - red, middle - blue, high - purple
        Map<AutoPivotHeight, LEDPattern> autoHeightMap = new HashMap<>();
        addHeightPattern(autoHeightMap, AutoPivotHeight.LOW, Color.kRed);
        addHeightPattern(autoHeightMap, AutoPivotHeight.MEDIUM, Color.kBlue);
        addHeightPattern(autoHeightMap, AutoPivotHeight.HIGH, Color.kPurple);

        m_autoModeColor = new LEDPatternLookup<>(m_buffer, autonColorMap);
        m_heightColor = new LEDPatternLookup<>(m_buffer, autoHeightMap);

        m_autoResetArmAtAngle = new LEDFlash(m_buffer, AUTO_MODE_END, MAX_INDEX_LED, 0.5, Color.kGreen);

        m_led.setLength(m_buffer.getLength());

        //for Claw Aligned Check
        m_clawAlignedSignal = new LEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kOrange);

        //holding piece in claw
        m_isHoldingPieceClaw = new LEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.05, Color.kRed);
        m_clawWasTripped = false;

        // Set the data
        m_led.setData(m_buffer);
        m_led.start();
    }

    private void addOnePieceAuto(Map<AutonomousFactory.AutonMode, LEDPattern> autonColorMap, AutonomousFactory.AutonMode mode, Color color) {
        autonColorMap.put(mode, new LEDSolidColor(m_buffer, AUTO_MODE_START, AUTO_MODE_END, color));

    }

    private void addOnePieceGrabSecondAndEngageAuto(Map<AutonomousFactory.AutonMode, LEDPattern> autonColorMap, AutonomousFactory.AutonMode mode, Color color) {
        autonColorMap.put(mode, new LEDFlash(m_buffer, AUTO_MODE_START, AUTO_MODE_END, 0.5, color));
    }

    private void addOnePieceAndEngageAuto(Map<AutonomousFactory.AutonMode, LEDPattern> autonColorMap, AutonomousFactory.AutonMode mode, Color color) {
        autonColorMap.put(mode, new LEDSolidColor(m_buffer, AUTO_MODE_START, AUTO_MODE_END, color));

    }

    private void addTwoPieceAuto(Map<AutonomousFactory.AutonMode, LEDPattern> autonColorMap, AutonomousFactory.AutonMode mode, Color color) {
        autonColorMap.put(mode, new LEDMovingPixel(m_buffer, AUTO_MODE_START, AUTO_MODE_END, color));
    }

    private void addHeightPattern(Map<AutoPivotHeight, LEDPattern> autoHeightMap, AutoPivotHeight height, Color color) {
        autoHeightMap.put(height, new LEDFlash(m_buffer, AUTO_HEIGHT_START, AUTO_HEIGHT_COUNT, 0.5, color));
    }


    private void disabledPatterns() {
        AutoPivotHeight height = m_autoModeFactory.getSelectedHeight();
        m_heightColor.setKey(height);

        if (m_heightColor.hasKey(height)) {
            m_heightColor.writeLeds();
        }

        AutonomousFactory.AutonMode autoMode = m_autoModeFactory.getSelectedAuto();
        m_autoModeColor.setKey(autoMode);

        if (m_autoModeColor.hasKey(autoMode)) {
            m_autoModeColor.writeLeds();
        }

        if (m_armSubsystem.areEncodersSynced()) {
            m_autoResetArmAtAngle.writeLeds();
        }
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    private void enabledPatterns() {
        if (m_optionConeLED) {
            m_coneGamePieceSignal.writeLeds();
        }
        else if (m_optionCubeLED) {
            m_cubeGamePieceSignal.writeLeds();
        }
        else if (m_optionDockLED || m_chassisSubsystem.tryingToEngage()) {
            dockAndEngagePatterns();
        }
        else if (m_claw.hasGamePiece() && m_clawLEDsTimer.get() < CLAW_HOLD_WAIT_TIME) {
            m_isHoldingPieceClaw.writeLeds();
        }
        else if (m_chassisSubsystem.isInCommunityZone()) {
            communityZonePatterns();
        }
        else if (m_chassisSubsystem.isInLoadingZone()) {
            loadingZonePatterns();
        }

        else {
            m_notInCommunityZone.writeLeds();
        }

        if (m_clawIsAligned) {
            m_clawAlignedSignal.writeLeds();
        }


        /*
        else {
            communityZonePatterns();
        }
        */
    }

    public void shouldTrip() {
        if (!m_clawWasTripped && m_claw.hasGamePiece()) {
            m_clawLEDsTimer.reset();
            m_clawLEDsTimer.start();
        }

        m_clawWasTripped = m_claw.hasGamePiece();
    }

    @Override
    public void periodic() {
        clear();
        if (DriverStation.isDisabled()) {
            disabledPatterns();
        }
        else {
            enabledPatterns();
        }
        shouldTrip();


        // driverPracticePatterns();
        m_led.setData(m_buffer);
    }

    private void clear() {
        for (int i = 0; i < MAX_INDEX_LED; i++) {
            m_buffer.setRGB(i, 0, 0, 0);
        }
    }

    public void setDockOption() {
        m_optionDockLED = true;
    }

    private void dockAndEngagePatterns() {
        if (Math.abs(m_chassisSubsystem.getPitch()) > ALLOWABLE_ERROR_ENGAGE) {
            m_dockAngle.distanceToTarget(m_chassisSubsystem.getPitch());
            m_dockAngle.writeLeds();
        }
        else {
            m_isEngaged.writeLeds();
        }
    }

    public void stopDockAndEngagePatterns() {
        m_optionDockLED = false;
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void communityZonePatterns() {
        if (m_armSubsystem.isArmAtAngle()) {
            m_readyToScore.writeLeds();
        }
        else if (!m_armSubsystem.isArmAtAngle()) {
            //m_turretAngle.distanceToTarget(m_turretSubsystem.getTurretError());
            m_armAtAngle.setState(m_armSubsystem.isArmAtAngle());
        }
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void loadingZonePatterns() {
        if (m_chassisSubsystem.isInLoadingZone()) {
            m_isInLoadingZoneSignal.writeLeds();
        }

    }

    public void setClawIsAligned(boolean isAligned) {
        m_clawIsAligned = isAligned;
    }

    //////////////////////
    // Command Factories
    //////////////////////
    public CommandBase commandConeGamePieceSignal() {
        return this.runEnd(() -> m_optionConeLED = true, () -> m_optionConeLED = false);
    }

    public CommandBase commandCubeGamePieceSignal() {
        return this.runEnd(() -> m_optionCubeLED = true, () -> m_optionCubeLED = false);
    }
}
