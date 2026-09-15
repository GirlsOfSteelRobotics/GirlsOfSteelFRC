package com.gos.lib.properties.feedforward;

import com.gos.lib.properties.BasePropertiesTest;
import edu.wpi.first.wpilibj.Preferences;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleMotorFeedForwardPropertyTest extends BasePropertiesTest {
    private static final String PROPERTY_NAME = "SimpleMotorFeedForwardTest";

    @Test
    public void testParameterSetting() {
        SimpleMotorFeedForwardProperty property = new SimpleMotorFeedForwardProperty(PROPERTY_NAME, false)
            .addKff(1)
            .addKs(2)
            .addKa(3);

        // Check initial setup
        assertEquals(1, property.getKFf());
        assertEquals(2, property.getKs());
        assertEquals(3, property.getKa());
        assertEquals(3 + 1, Preferences.getKeys().size());

        // Change the values, but do not update the properties. Should remain the same as the defaults
        Preferences.setDouble(PROPERTY_NAME + ".smff.kff", 9);
        Preferences.setDouble(PROPERTY_NAME + ".smff.ks", 8);
        Preferences.setDouble(PROPERTY_NAME + ".smff.ka", 7);
        assertEquals(1, property.getKFf());
        assertEquals(2, property.getKs());
        assertEquals(3, property.getKa());
        assertEquals(3 + 1, Preferences.getKeys().size());

        // Update the properties and assert they have changed
        property.updateIfChanged();
        assertEquals(9, property.getKFf());
        assertEquals(8, property.getKs());
        assertEquals(7, property.getKa());
        assertEquals(3 + 1, Preferences.getKeys().size());

    }

    @Test
    @SuppressWarnings("removal")
    public void testDeprecatedCalculate() {
        SimpleMotorFeedForwardProperty property = new SimpleMotorFeedForwardProperty(PROPERTY_NAME, true)
            .addKff(9)
            .addKs(8)
            .addKa(7);

        // Check calculations
        assertEquals(8 + 9 * 2 + 7 * 1.5, property.calculate(2, 1.5));
        assertEquals(-8 + 9 * -3 + 7 * 2, property.calculate(-3, 2));
    }
}
