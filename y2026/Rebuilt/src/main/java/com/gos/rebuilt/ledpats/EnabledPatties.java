package com.gos.rebuilt.ledpats;


import com.gos.lib.led.LEDBoolean;
import com.gos.lib.led.LEDFlash;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import com.gos.rebuilt.subsystems.PizzaSubsystem;
import com.gos.rebuilt.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class EnabledPatties {
    private final ShooterSubsystem m_shooter;
    private final ChassisSubsystem m_chassis;
    private final PizzaSubsystem m_pizza;
    private final LEDBoolean m_shooterReady;
    private final LEDBoolean m_chassisFacing;
    private final LEDBoolean m_everythingReady;
    private final LEDBoolean m_distanceGood;
    private final LEDFlash m_pizzaJammed;


    public EnabledPatties(AddressableLEDBuffer addressme, ShooterSubsystem shooter, ChassisSubsystem chassis, PizzaSubsystem pizza) {
        this.m_chassis = chassis;
        this.m_shooter = shooter;
        this.m_pizza = pizza;
        this.m_shooterReady = new LEDBoolean(addressme, 0,  10, Color.kLawnGreen, Color.kIndianRed);
        this.m_chassisFacing = new LEDBoolean(addressme, 11, 20, Color.kMediumSeaGreen, Color.kOrangeRed);
        this.m_distanceGood = new LEDBoolean(addressme, 21, 30, Color.kDarkGoldenrod, Color.kGhostWhite);
        this.m_everythingReady = new LEDBoolean(addressme, 31, 40, Color.kBrown, Color.kLimeGreen);
        this.m_pizzaJammed = new LEDFlash(addressme, 41, 50, .5, Color.kRed);
    }

    public void writeLED() {
        m_chassisFacing.setStateAndWrite(m_chassis.facingHub());
        m_shooterReady.setStateAndWrite(m_shooter.isAtGoalRPM());
        m_distanceGood.setStateAndWrite(m_chassis.getDistanceFromHub() > m_shooter.getMinDistance());
        m_everythingReady.setStateAndWrite(checkAll());
        if (m_pizza.checkJam()) {
            m_pizzaJammed.writeLeds();
        }


    }

    public boolean checkAll() {

        if (m_chassis.facingHub()) {
            return (m_shooter.isAtGoalRPM() && m_chassis.getDistanceFromHub() < m_shooter.getMinDistance());
        }
        return false;
    }
}
