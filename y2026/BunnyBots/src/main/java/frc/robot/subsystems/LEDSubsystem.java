package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.led_patterns.DisabledPatterns;
import frc.robot.led_patterns.EnabledPatterns;

public class LEDSubsystem extends SubsystemBase {


    private static final int MAX_INDEX_LED = 59;


    private final AddressableLEDBuffer m_addressMe;
    protected final AddressableLED m_led;

    private final EnabledPatterns m_enabledPatterns;
    private final DisabledPatterns m_disabledPatterns;

    private final ChassisSubsystem m_chassis;
    private final ElevatorSubsystem m_elevator;

    public LEDSubsystem( /* parameters go here */ ) {
        // write constructor stuff, initialize variables
    }
}

