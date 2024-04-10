package com.gos.crescendo2024.led_patterns.subpatterns;

import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.lib.led.mirrored.MirroredLEDPercentScale;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class AprilTagPattern {
    private final MirroredLEDPercentScale m_aprilTagsDetected;
    private final ChassisSubsystem m_chassis;

    public AprilTagPattern(AddressableLEDBuffer buffer, int startIndex, int numLeds, ChassisSubsystem chassis) {
        m_aprilTagsDetected = new MirroredLEDPercentScale(buffer, startIndex, numLeds, Color.kDarkTurquoise, 4);
        m_chassis = chassis;
    }

    public void writeLED() {
        m_aprilTagsDetected.distanceToTarget(m_chassis.numAprilTagsSeen());
        m_aprilTagsDetected.writeLeds();
    }
}
