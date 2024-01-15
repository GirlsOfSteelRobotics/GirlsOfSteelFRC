package com.gos.lib.properties.pid;

import com.gos.lib.properties.BasePropertiesTest;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.Preferences;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WpiProfiledPidPropertyBuilderTest extends BasePropertiesTest {
    private static final String PROPERTY_NAME = "WpiProfiledPidPropertyBuilderTest";

    @Test
    public void testParameterSetting() {
        ProfiledPIDController controller = new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(0, 0));
        PidProperty property = new WpiProfiledPidPropertyBuilder(PROPERTY_NAME, false, controller)
            .addP(1)
            .addI(2)
            .addD(3)
            .addMaxVelocity(4)
            .addMaxAcceleration(5)
            .build();

        // Check initial setup
        assertEquals(1, controller.getP());
        assertEquals(2, controller.getI());
        assertEquals(3, controller.getD());
        assertEquals(5 + 1, Preferences.getKeys().size());

        property.updateIfChanged();
    }

    @Test
    public void testThrowOnKff() {
        ProfiledPIDController controller = new ProfiledPIDController(0, 0, 0, new TrapezoidProfile.Constraints(0, 0));
        WpiProfiledPidPropertyBuilder builder = new WpiProfiledPidPropertyBuilder(PROPERTY_NAME, false, controller);

        assertThrows(UnsupportedOperationException.class, () -> builder.addFF(0));
    }
}
