package com.gos.rebuilt.subsystems;


import com.gos.rebuilt.Constants;
import com.gos.rebuilt.autos.AutoFactory;
import com.gos.rebuilt.ledpats.DIsabledPatties;
import com.gos.rebuilt.ledpats.EnabledPatties;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class LEDSubsystem extends SubsystemBase {


    private static final int MAX_INDEX_LED = 59;


    private final AddressableLEDBuffer m_addressMe;
    protected final AddressableLED m_led;

    private final EnabledPatties m_enabledPatterns;
    private final DIsabledPatties m_disabledPatterns;


    private final ShooterSubsystem m_shooter;
    private final ChassisSubsystem m_chassis;
    private final PizzaSubsystem m_pizza;


    public LEDSubsystem(ShooterSubsystem shooter, ChassisSubsystem chassis, PizzaSubsystem pizza, AutoFactory factory) {
        this.m_shooter = shooter;
        this.m_chassis = chassis;
        this.m_pizza = pizza;

        this.m_addressMe = new AddressableLEDBuffer(MAX_INDEX_LED);
        this.m_led = new AddressableLED(Constants.LED_PORT);
        m_led.setLength(m_addressMe.getLength());
        m_led.setData((m_addressMe));
        m_led.start();
        this.m_enabledPatterns = new EnabledPatties(m_addressMe, m_shooter, m_chassis, m_pizza);
        this.m_disabledPatterns = new DIsabledPatties(m_addressMe, factory);


    }

    @Override
    public void periodic() {
        clearLEDs();
        if (DriverStation.isEnabled()) {
            m_enabledPatterns.writeLED();
        } else {
            m_disabledPatterns.update();

        }

        m_led.setData(m_addressMe);
    }


    public void clearLEDs() {
        for (int i = 0; i < MAX_INDEX_LED; i++) {
            m_addressMe.setRGB(i, 0, 0, 0);
        }

    }
}

