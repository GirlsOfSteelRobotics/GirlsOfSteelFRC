package com.gos.rebuilt.ledpats;

import com.gos.rebuilt.autos.AutoFactory;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class DIsabledPatties {

    private final AutoFactory m_autoModeFactory;
    private final AutoModePattern m_autoModePattern;


    public DIsabledPatties(AddressableLEDBuffer buffer, AutoFactory autoModeFactory) {
        m_autoModePattern = new AutoModePattern(buffer, 51, 8); //change nums
        m_autoModeFactory = autoModeFactory;


    }

    public void update() {
        m_autoModePattern.writeAutoModePattern(m_autoModeFactory.getSelectedAuto());
    }


}
