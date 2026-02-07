package com.gos.rebuilt.ledpats;


import com.gos.lib.led.LEDBoolean;
import com.gos.rebuilt.subsystems.ChassisSubsystem;
import com.gos.rebuilt.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;

public class EnabledPatties {
    private final ShooterSubsystem m_shooter;
    private final ChassisSubsystem m_chassis;
    private final LEDBoolean m_shooterReady;
    private final LEDBoolean m_chassisFacing;
    private final LEDBoolean m_everythingReady;
    private final LEDBoolean m_distanceGood;


    public EnabledPatties(AddressableLEDBuffer addressme, ShooterSubsystem shooter, ChassisSubsystem chassis) {
        this.m_chassis = chassis;
        this.m_shooter = shooter;
        this.m_shooterReady = new LEDBoolean(addressme, 0,  10, Color.kLawnGreen, Color.kIndianRed);
        this.m_chassisFacing = new LEDBoolean(addressme, 10, 20, Color.kMediumSeaGreen, Color.kOrangeRed);
        this.m_distanceGood = new LEDBoolean(addressme, 20, 30, Color.kDarkGoldenrod, Color.kGhostWhite);
        this.m_everythingReady = new LEDBoolean(addressme, 30, 40, Color.kBrown, Color.kLimeGreen);

    }

    public void writeLED() {
        m_chassisFacing.setStateAndWrite(m_chassis.facingHub());
        m_shooterReady.setStateAndWrite(m_shooter.isAtGoalRPM());
        m_distanceGood.setStateAndWrite(m_chassis.getDistanceFromHub() < m_shooter.getMinDistance());
        m_everythingReady.setStateAndWrite(checkAll());


    }

    public boolean checkAll() {

        if (m_chassis.facingHub()) {
            return (m_shooter.isAtGoalRPM() && m_chassis.getDistanceFromHub() < m_shooter.getMinDistance());
        }
        return false;
    }
}
