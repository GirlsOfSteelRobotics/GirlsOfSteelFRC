package com.gos.lib.properties.pid;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WpiPidPropertyBuilderTest {

    private static final String PROPERTY_NAME = "WpiPidPropertyBuilderTest";

    @Test
    public void testParameterSetting() {
        try (PIDController controller = new PIDController(0, 0, 0)) {
            PidProperty property = new WpiPidPropertyBuilder(PROPERTY_NAME, false, controller)
                .addP(1)
                .addI(2)
                .addD(3)
                .build();

            // Check initial setup
            assertEquals(1, controller.getP());
            assertEquals(2, controller.getI());
            assertEquals(3, controller.getD());
            assertEquals(3 + 1, Preferences.getKeys().size());

            property.updateIfChanged();
        }
    }

    @Test
    public void testThrowOnKff() {
        try (PIDController controller = new PIDController(0, 0, 0)) {
            WpiPidPropertyBuilder builder = new WpiPidPropertyBuilder(PROPERTY_NAME, false, controller);

            assertThrows(UnsupportedOperationException.class, () -> builder.addFF(0));
        }
    }

    @Test
    public void testThrowOnMaxVelocity() {
        try (PIDController controller = new PIDController(0, 0, 0)) {
            WpiPidPropertyBuilder builder = new WpiPidPropertyBuilder(PROPERTY_NAME, false, controller);

            assertThrows(UnsupportedOperationException.class, () -> builder.addMaxVelocity(0));
        }
    }

    @Test
    public void testThrowOnMaxAccel() {
        try (PIDController controller = new PIDController(0, 0, 0)) {
            WpiPidPropertyBuilder builder = new WpiPidPropertyBuilder(PROPERTY_NAME, false, controller);

            assertThrows(UnsupportedOperationException.class, () -> builder.addMaxAcceleration(0));
        }
    }
}
