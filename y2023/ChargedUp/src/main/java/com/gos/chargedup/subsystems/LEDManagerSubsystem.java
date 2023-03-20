package com.gos.chargedup.subsystems;

import com.gos.chargedup.AutoPivotHeight;
import com.gos.chargedup.Constants;
import com.gos.chargedup.autonomous.AutonomousFactory;
import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.mirrored.MirroredLEDBoolean;
import com.gos.lib.led.mirrored.MirroredLEDFlash;
import com.gos.lib.led.mirrored.MirroredLEDPatternLookup;
import com.gos.lib.led.mirrored.MirroredLEDPercentScale;
import com.gos.lib.led.mirrored.MirroredLEDRainbow;
import com.gos.lib.led.mirrored.MirroredLEDSolidColor;
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

    private static final int MAX_INDEX_LED = 30;

    private static final int AUTO_HEIGHT_START = 0;
    private static final int AUTO_HEIGHT_COUNT = MAX_INDEX_LED / 4;

    private static final int AUTO_MODE_START = AUTO_HEIGHT_COUNT;

    private static final int AUTO_MODE_COUNT = MAX_INDEX_LED / 4;

    private static final int CLAW_HOLD_WAIT_TIME = 1;


    // subsystems
    //private final CommandXboxController m_joystick;
    private final ChassisSubsystem m_chassisSubsystem;
    private final ArmPivotSubsystem m_armSubsystem;
    private final TurretSubsystem m_turretSubsystem;

    private final ClawSubsystem m_claw;

    // Led core
    protected final AddressableLEDBuffer m_buffer;
    protected final AddressableLED m_led;

    // Testing LED
    //private final MirroredLEDPercentScale m_drivetrainSpeed;

    // Comp LED
    private final MirroredLEDFlash m_coneGamePieceSignal;
    private final MirroredLEDFlash m_cubeGamePieceSignal;

    private boolean m_optionConeLED;
    private boolean m_optionCubeLED;
    private boolean m_optionDockLED;
    private boolean m_clawIsAligned;

    private final MirroredLEDFlash m_readyToScore;
    private final MirroredLEDPercentScale m_turretAngle;
    private final MirroredLEDBoolean m_armAtAngle;

    private final MirroredLEDPercentScale m_dockAngle;
    private final MirroredLEDFlash m_isEngaged;
    private static final double ALLOWABLE_ERROR_ENGAGE = 2;

    private final MirroredLEDRainbow m_notInCommunityZone;

    private final AutonomousFactory m_autoModeFactory;

    private final MirroredLEDPatternLookup<AutonomousFactory.AutonMode> m_autoModeColor;
    private final MirroredLEDPatternLookup<AutoPivotHeight> m_heightColor;

    private final MirroredLEDFlash m_clawAlignedSignal;

    private final MirroredLEDFlash m_isInLoadingZoneSignal;

    private final MirroredLEDFlash m_isHoldingPieceClaw;

    private final Timer m_clawLEDsTimer = new Timer();

    private boolean m_clawWasTripped;


    public LEDManagerSubsystem(ChassisSubsystem chassisSubsystem, ArmPivotSubsystem armSubsystem, TurretSubsystem turretSubsystem, ClawSubsystem claw, AutonomousFactory autonomousFactory) {
        m_autoModeFactory = autonomousFactory;

        m_chassisSubsystem = chassisSubsystem;
        m_armSubsystem = armSubsystem;
        m_turretSubsystem = turretSubsystem;
        m_claw = claw;

        m_buffer = new AddressableLEDBuffer(MAX_INDEX_LED);
        m_led = new AddressableLED(Constants.LED_PORT);

        // Test LED
        //m_drivetrainSpeed = new MirroredLEDPercentScale(m_buffer, 0, MAX_INDEX_LED, Color.kGreen, 15);

        // Comp LED
        m_coneGamePieceSignal = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kYellow);
        m_cubeGamePieceSignal = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kMediumPurple);

        m_readyToScore = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kGreen);
        m_turretAngle = new MirroredLEDPercentScale(m_buffer, 20, 10, Color.kRed, 20);
        m_armAtAngle = new MirroredLEDBoolean(m_buffer, 10, 10, Color.kAntiqueWhite, Color.kRed);
        // m_goodDistance = new MirroredLEDBoolean(m_buffer, 0, 10, Color.kAntiqueWhite, Color.kRed);

        //m_goodDistToLoadingPiece = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kGreen);

        m_isInLoadingZoneSignal = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kCornflowerBlue);

        m_dockAngle = new MirroredLEDPercentScale(m_buffer, 0, MAX_INDEX_LED, Color.kRed, 30);
        m_optionDockLED = false;
        m_isEngaged = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kGreen);

        m_notInCommunityZone = new MirroredLEDRainbow(m_buffer, 0, MAX_INDEX_LED);

        // no piece - base color
        // one piece - flash
        // two piece - single light

        // starting position -- color (1 - pink, 2 - red, 3 - orange, 4 - yellow, 5 - green, 6 - blue, 7 - purple)
        Map<AutonomousFactory.AutonMode, LEDPattern> autonColorMap = new HashMap<>();
        autonColorMap.put(AutonomousFactory.AutonMode.ONLY_LEAVE_COMMUNITY_END, new MirroredLEDSolidColor(m_buffer, AUTO_MODE_START, AUTO_MODE_COUNT, Color.kPink));
        autonColorMap.put(AutonomousFactory.AutonMode.ONLY_LEAVE_COMMUNITY_PLAYER_STATION, new MirroredLEDSolidColor(m_buffer, AUTO_MODE_START, AUTO_MODE_COUNT, Color.kPurple));
        autonColorMap.put(AutonomousFactory.AutonMode.ONLY_DOCK_AND_ENGAGE, new MirroredLEDSolidColor(m_buffer, AUTO_MODE_START, AUTO_MODE_COUNT, Color.kPapayaWhip));

        autonColorMap.put(AutonomousFactory.AutonMode.SCORE_CUBE_AT_CURRENT_POS, new MirroredLEDFlash(m_buffer, AUTO_MODE_START, AUTO_MODE_COUNT, 0.5, Color.kPapayaWhip));
        //        autonColorMap.put(AutonomousFactory.AutonMode.SCORE_CONE_AT_CURRENT_POS, new MirroredLEDFlash(m_buffer, AUTO_MODE_START, AUTO_MODE_COUNT, 0.5, Color.kAliceBlue));
        //        autonColorMap.put(AutonomousFactory.AutonMode.ONE_NODE_AND_ENGAGE_3, new MirroredLEDFlash(m_buffer, AUTO_MODE_START, AUTO_MODE_COUNT, 0.5, Color.kDarkOrange));
        autonColorMap.put(AutonomousFactory.AutonMode.ONE_NODE_AND_ENGAGE_4, new MirroredLEDFlash(m_buffer, AUTO_MODE_START, AUTO_MODE_COUNT, 0.5, Color.kYellow));
        //        autonColorMap.put(AutonomousFactory.AutonMode.ONE_NODE_AND_ENGAGE_5, new MirroredLEDFlash(m_buffer, AUTO_MODE_START, AUTO_MODE_COUNT, 0.5, Color.kGreen));

        //        autonColorMap.put(AutonomousFactory.AutonMode.TWO_PIECE_NODE_0_AND_1, new MirroredLEDMovingPixel(m_buffer, AUTO_MODE_START, AUTO_MODE_COUNT, Color.kPink));
        //        autonColorMap.put(AutonomousFactory.AutonMode.TWO_PIECE_NODE_7_AND_8, new MirroredLEDMovingPixel(m_buffer, AUTO_MODE_START, AUTO_MODE_COUNT, Color.kPurple));
        //        autonColorMap.put(AutonomousFactory.AutonMode.TWO_PIECE_ENGAGE, new MirroredLEDMovingPixel(m_buffer, AUTO_MODE_START, AUTO_MODE_COUNT, Color.kDarkOrchid));

        // no scoring -- solid red
        // low - red, middle - blue, high - purple
        Map<AutoPivotHeight, LEDPattern> autoHeightMap = new HashMap<>();
        autoHeightMap.put(AutoPivotHeight.LOW, new MirroredLEDFlash(m_buffer, AUTO_HEIGHT_START, AUTO_HEIGHT_COUNT, 0.5, Color.kRed));
        autoHeightMap.put(AutoPivotHeight.MEDIUM, new MirroredLEDFlash(m_buffer, AUTO_HEIGHT_START, AUTO_HEIGHT_COUNT, 0.5, Color.kBlue));
        autoHeightMap.put(AutoPivotHeight.HIGH, new MirroredLEDFlash(m_buffer, AUTO_HEIGHT_START, AUTO_HEIGHT_COUNT, 0.5, Color.kPurple));

        m_autoModeColor = new MirroredLEDPatternLookup<>(m_buffer, autonColorMap);
        m_heightColor = new MirroredLEDPatternLookup<>(m_buffer, autoHeightMap);

        m_led.setLength(m_buffer.getLength());

        //for Claw Aligned Check
        m_clawAlignedSignal = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.5, Color.kOrange);

        //holding piece in claw
        m_isHoldingPieceClaw = new MirroredLEDFlash(m_buffer, 0, MAX_INDEX_LED, 0.05, Color.kRed);
        m_clawWasTripped = false;

        // Set the data
        m_led.setData(m_buffer);
        m_led.start();
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
        if (m_turretSubsystem.atTurretAngle() && m_armSubsystem.isArmAtAngle()) {
            m_readyToScore.writeLeds();
        }
        else if (!m_turretSubsystem.atTurretAngle() || !m_armSubsystem.isArmAtAngle()) {
            m_turretAngle.distanceToTarget(m_turretSubsystem.getTurretError());
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

