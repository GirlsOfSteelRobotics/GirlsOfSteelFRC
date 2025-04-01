package com.gos.reefscape.led_patterns;

import com.gos.lib.led.LEDAngleToTargetOverAndUnder;
import com.gos.lib.led.mirrored.MirroredLEDBoolean;
import com.gos.lib.led.mirrored.MirroredLEDFlash;
import com.gos.lib.led.mirrored.MirroredLEDMovingPixel;
import com.gos.lib.led.mirrored.MirroredLEDRainbow;
import com.gos.reefscape.MaybeFlippedPose2d;
import com.gos.reefscape.enums.KeepOutZoneEnum;
import com.gos.reefscape.led_patterns.sub_patterns.KeepOutZoneStatePattern;
import com.gos.reefscape.subsystems.ChassisSubsystem;
import com.gos.reefscape.subsystems.CoralSubsystem;
import com.gos.reefscape.subsystems.LEDSubsystem;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

import java.util.Optional;

public class EnabledPatterns {

    private final CoralSubsystem m_coralSubsystem;


    private final MirroredLEDBoolean m_hasCoral;
    private final MirroredLEDFlash m_canDriveToPose;
    private final KeepOutZoneStatePattern m_keepOutPattern;
    private final ChassisSubsystem m_chassis;
    private final MirroredLEDRainbow m_relative;
    private final MirroredLEDMovingPixel m_driveToPose;
    private final LEDAngleToTargetOverAndUnder m_reefPositionCameraYaw;

    public EnabledPatterns(AddressableLEDBuffer buffer, CoralSubsystem coral, ChassisSubsystem driveToPose) {
        m_coralSubsystem = coral;
        m_reefPositionCameraYaw = new LEDAngleToTargetOverAndUnder(buffer, LEDSubsystem.SWIRL_START, LEDSubsystem.SWIRL_COUNT + LEDSubsystem.SWIRL_START, Color.kViolet, Color.kOrange, 6);


        //change this
        m_canDriveToPose = new MirroredLEDFlash(buffer, 0, LEDSubsystem.SWIRL_START / 2, 0.25, Color.kHotPink);

        m_hasCoral = new MirroredLEDBoolean(buffer, LEDSubsystem.SWIRL_START / 2, LEDSubsystem.SWIRL_START / 2, Color.kGreen, Color.kRed);
        m_keepOutPattern = new KeepOutZoneStatePattern(buffer, LEDSubsystem.SWIRL_START, LEDSubsystem.SWIRL_COUNT);

        m_relative = new MirroredLEDRainbow(buffer, 0, LEDSubsystem.SWIRL_START);
        m_driveToPose = new MirroredLEDMovingPixel(buffer, 0, LEDSubsystem.SWIRL_START, Color.kBlue);


        m_chassis = driveToPose;

    }

    public void ledUpdates() {
        if (m_chassis.isDrivingRobotRelative()) {
            m_relative.writeLeds();
        } else if (m_chassis.isDrivingToPose()) {
            m_driveToPose.writeLeds();
        }
        else {
            MaybeFlippedPose2d closestAlgae = m_chassis.findClosestAlgae().m_pose;
            double distance = closestAlgae.getPose().getTranslation().getDistance(m_chassis.getState().Pose.getTranslation());
            if (distance > 0.5) {
                m_canDriveToPose.writeLeds();
            }
            m_hasCoral.setStateAndWrite(m_coralSubsystem.hasCoral());
        }

        Optional<Double> maybeYaw = m_chassis.getReefCameraYaw();
        maybeYaw.ifPresent(m_reefPositionCameraYaw::setAngleAndWrite);
    }

    public void setKeepOutZoneState(KeepOutZoneEnum state) {
        m_keepOutPattern.setState(state);
    }
}


