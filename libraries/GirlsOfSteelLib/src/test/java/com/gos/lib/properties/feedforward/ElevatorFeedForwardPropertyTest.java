package com.gos.lib.properties.feedforward;

import com.gos.lib.properties.BasePropertiesTest;
import edu.wpi.first.wpilibj.Preferences;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevatorFeedForwardPropertyTest extends BasePropertiesTest {
    private static final String PROPERTY_NAME = "ElevatorFeedForwardTest";

    @Test
    public void testParameterSetting() {
        ElevatorFeedForwardProperty property = new ElevatorFeedForwardProperty(PROPERTY_NAME, false)
            .addKff(1)
            .addKs(2)
            .addKa(3)
            .addKg(4);

        // Check initial setup
        assertEquals(1, property.getKFf());
        assertEquals(2, property.getKs());
        assertEquals(3, property.getKa());
        assertEquals(4, property.getKg());
        assertEquals(4 + 1, Preferences.getKeys().size());

        // Change the values, but do not update the properties. Should remain the same as the defaults
        Preferences.setDouble(PROPERTY_NAME + ".eff.kff", 1.5);
        Preferences.setDouble(PROPERTY_NAME + ".eff.ks", 0.5);
        Preferences.setDouble(PROPERTY_NAME + ".eff.ka", 2);
        Preferences.setDouble(PROPERTY_NAME + ".eff.kg", 1);
        assertEquals(1, property.getKFf());
        assertEquals(2, property.getKs());
        assertEquals(3, property.getKa());
        assertEquals(4, property.getKg());
        assertEquals(4 + 1, Preferences.getKeys().size());

        // Update the properties and assert they have changed
        property.updateIfChanged();
        assertEquals(1.5, property.getKFf());
        assertEquals(0.5, property.getKs());
        assertEquals(2, property.getKa());
        assertEquals(1, property.getKg());
        assertEquals(4 + 1, Preferences.getKeys().size());
    }

    @Test
    @SuppressWarnings("removal")
    public void testDeprecatedCalculateNoKa() {
        ElevatorFeedForwardProperty property = new ElevatorFeedForwardProperty(PROPERTY_NAME, true)
            .addKff(1)
            .addKs(2)
            .addKg(4);

        assertEquals(property.getKs() + property.getKg() + property.getKFf() * 2, property.calculate(2));
        assertEquals(-property.getKs() + property.getKg() + property.getKFf() * -2, property.calculate(-2));

        assertEquals(property.getKs() + property.getKg() + property.getKFf() * 2, property.calculate(2, 1.3));
        assertEquals(-property.getKs() + property.getKg() + property.getKFf() * -2, property.calculate(-2, 1.3));
    }

    @Test
    @SuppressWarnings("removal")
    public void testDeprecatedCalculateWithKa() {
        ElevatorFeedForwardProperty property = new ElevatorFeedForwardProperty(PROPERTY_NAME, true)
            .addKff(1)
            .addKs(2)
            .addKg(4)
            .addKa(1.2);

        assertEquals(property.getKs() + property.getKg() + property.getKFf() * 2, property.calculate(2));
        assertEquals(-property.getKs() + property.getKg() + property.getKFf() * -2, property.calculate(-2));

        assertEquals(property.getKs() + property.getKg() + property.getKFf() * 2 + 1.3 * property.getKa(), property.calculate(2, 1.3));
        assertEquals(-property.getKs() + property.getKg() + property.getKFf() * -2 + 1.3 * property.getKa(), property.calculate(-2, 1.3));
    }
}
