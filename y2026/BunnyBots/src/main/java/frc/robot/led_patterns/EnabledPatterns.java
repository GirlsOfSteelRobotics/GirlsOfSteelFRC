package frc.robot.led_patterns;

import com.gos.lib.led.LEDBoolean;
import com.gos.lib.led.LEDPattern;
import com.gos.lib.led.LEDPatternLookup;
import com.gos.lib.led.LEDSolidColor;
import frc.robot.enums.ElevatorHeights;
import frc.robot.subsystems.ChassisSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import org.wpilib.hardware.led.AddressableLEDBuffer;
import org.wpilib.util.Color;

import java.util.HashMap;
import java.util.Map;

public class EnabledPatterns {
    private final ChassisSubsystem m_chassis;
    private final ElevatorSubsystem m_elevator;
    private final LEDBoolean m_canSeeWall;
    private final Map<Double, LEDPattern> m_elevatorMap = new HashMap<>();
    private final LEDPatternLookup<Double> m_elevatorGoal;

    //Section for if the can range sees the wall - LEDBoolean
    //Section for how many game pieces we have - Maybe 3 LEDBoolean?

    public EnabledPatterns(AddressableLEDBuffer addressMe, ChassisSubsystem chassis, ElevatorSubsystem elevator) {
        m_chassis = chassis;
        m_elevator = elevator;
        m_canSeeWall = new LEDBoolean(addressMe, 0, 10, Color.AZURE, Color.DARK_ORANGE);
        m_elevatorMap.put(ElevatorHeights.OVENHEIGHT.m_height, new LEDSolidColor(addressMe, 11, 20, Color.LEMON_CHIFFON));
        m_elevatorMap.put(ElevatorHeights.SHELF1.m_height, new LEDSolidColor(addressMe, 11, 20, Color.THISTLE));
        m_elevatorMap.put(ElevatorHeights.SHELF2.m_height, new LEDSolidColor(addressMe, 11, 20, Color.MEDIUM_SLATE_BLUE));
        m_elevatorMap.put(ElevatorHeights.SHELF3.m_height, new LEDSolidColor(addressMe, 11, 20, Color.PLUM));
        m_elevatorGoal = new LEDPatternLookup<>(addressMe, m_elevatorMap);

    }

    public void writeLED() {
        m_canSeeWall.setStateAndWrite(m_chassis.canSeeWall());
        m_elevatorGoal.setKey(m_elevator.getGoalHeight());
        m_elevatorGoal.writeLeds();
    }

}
