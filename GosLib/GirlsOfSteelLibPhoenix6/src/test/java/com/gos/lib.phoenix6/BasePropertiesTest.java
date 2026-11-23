package com.gos.lib.phoenix6;

import org.wpilib.networktables.NetworkTableInstance;
import org.wpilib.preferences.Preferences;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BasePropertiesTest {
    private NetworkTableInstance m_ntInstance;

    @BeforeEach
    public void setup() {
        m_ntInstance = NetworkTableInstance.create();
        Preferences.setNetworkTableInstance(m_ntInstance);
    }

    @AfterEach
    public void tearDown() {
        m_ntInstance.close();
        Preferences.setNetworkTableInstance(NetworkTableInstance.getDefault()); // NOPMD(CloseResource)
    }
}
