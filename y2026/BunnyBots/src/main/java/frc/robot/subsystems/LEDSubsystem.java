package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.autos.AutoFactory;
import frc.robot.led_patterns.EnabledPatterns;
import org.wpilib.command2.SubsystemBase;
import org.wpilib.driverstation.DriverStation;
import org.wpilib.driverstation.internal.DriverStationBackend;
import org.wpilib.hardware.led.AddressableLED;
import org.wpilib.hardware.led.AddressableLEDBuffer;

public class LEDSubsystem extends SubsystemBase {


    private static final int MAX_INDEX_LED = 59;


    private final AddressableLEDBuffer m_addressMe;
    protected final AddressableLED m_led;

    private final EnabledPatterns m_enabledPatterns;

    private final ChassisSubsystem m_chassis;
    private final ElevatorSubsystem m_elevator;

    public LEDSubsystem(ChassisSubsystem chassis, ElevatorSubsystem elevator, AutoFactory factory) {
        m_chassis = chassis;
        m_elevator = elevator;
        m_addressMe = new AddressableLEDBuffer(MAX_INDEX_LED);
        m_led = new AddressableLED(Constants.LED_PORT);

        m_led.setLength(m_addressMe.getLength());
        m_led.setData((m_addressMe));

        m_enabledPatterns = new EnabledPatterns(m_addressMe, m_chassis, m_elevator);
    }

    @Override
    public void periodic() {
        clearLEDs();
        if (DriverStationBackend.isEnabled()) {
            m_enabledPatterns.writeLED();
        } /*else {
            m_disabledPatterns.update();

        }*/

        m_led.setData(m_addressMe);
        // m_driverStationPatterns.writeLeds();
    }


    public void clearLEDs() {
        for (int i = 0; i < MAX_INDEX_LED; i++) {
            m_addressMe.setRGB(i, 0, 0, 0);
        }

    }
}

