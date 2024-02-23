package com.gos.crescendo2024.led_patterns.subpatterns;

import com.gos.crescendo2024.subsystems.ChassisSubsystem;
import com.gos.lib.led.LEDPercentScale;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class AprilTagPattern {
    private final LEDPercentScale m_AprilTagsDetected;
    private final ChassisSubsystem m_chassis;
    public AprilTagPattern(AddressableLEDBuffer buffer, int startIndex, int maxIndex, ChassisSubsystem chassis){
        m_AprilTagsDetected = new LEDPercentScale(buffer, startIndex, maxIndex, Color.kDarkTurquoise, 4);
        m_chassis = chassis;
    }
    public void writeLED(){
        m_AprilTagsDetected.distanceToTarget(m_chassis.numAprilTagsSeen());
        m_AprilTagsDetected.writeLeds();
    }
}
