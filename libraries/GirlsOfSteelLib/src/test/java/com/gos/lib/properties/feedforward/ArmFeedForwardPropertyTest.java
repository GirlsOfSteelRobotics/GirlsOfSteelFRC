package com.gos.lib.properties.feedforward;

import com.gos.lib.properties.BasePropertiesTest;
import edu.wpi.first.wpilibj.Preferences;
import org.junit.jupiter.api.Test;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArmFeedForwardPropertyTest extends BasePropertiesTest {
    private static final String PROPERTY_NAME = "ArmFeedForwardTest";

    @Test
    public void testParameterSetting() {
        ArmFeedForwardProperty property = new ArmFeedForwardProperty(PROPERTY_NAME, false)
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
        Preferences.setDouble(PROPERTY_NAME + ".aff.kff", 1.5);
        Preferences.setDouble(PROPERTY_NAME + ".aff.ks", 0.5);
        Preferences.setDouble(PROPERTY_NAME + ".aff.ka", 2);
        Preferences.setDouble(PROPERTY_NAME + ".aff.kg", 1);
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
        ArmFeedForwardProperty property = new ArmFeedForwardProperty(PROPERTY_NAME, true)
            .addKff(1)
            .addKs(2)
            .addKg(4);

        assertEquals(
            property.getKs()
                + property.getKg() * Math.cos(Math.toRadians(45))
                + property.getKFf() * 2,
            property.calculate(Math.toRadians(45), 2));
        assertEquals(
            -property.getKs()
                + property.getKg() * Math.cos(Math.toRadians(45))
                + property.getKFf() * -2,
            property.calculate(Math.toRadians(45), -2));

        assertEquals(
            property.getKs()
                + property.getKg() * Math.cos(Math.toRadians(45))
                + property.getKFf() * 2,
            property.calculate(Math.toRadians(45), 2, 1.3));
        assertEquals(
            -property.getKs()
                + property.getKg() * Math.cos(Math.toRadians(45))
                + property.getKFf() * -2,
            property.calculate(Math.toRadians(45), -2, 1.3));
    }

    @Test
    @SuppressWarnings("removal")
    public void testDeprecatedCalculateWithKa() {
        ArmFeedForwardProperty property = new ArmFeedForwardProperty(PROPERTY_NAME, true)
            .addKff(1)
            .addKs(2)
            .addKg(4)
            .addKa(1.2);

        assertEquals(
            property.getKs()
                + property.getKg() * Math.cos(Math.toRadians(45))
                + property.getKFf() * 2,
            property.calculate(Math.toRadians(45), 2));
        assertEquals(
            -property.getKs()
                + property.getKg() * Math.cos(Math.toRadians(45))
                + property.getKFf() * -2,
            property.calculate(Math.toRadians(45), -2));

        assertEquals(
            property.getKs()
                + property.getKg() * Math.cos(Math.toRadians(45))
                + property.getKFf() * 2
                + property.getKa() * 1.3,
            property.calculate(Math.toRadians(45), 2, 1.3));
        assertEquals(
            -property.getKs()
                + property.getKg() * Math.cos(Math.toRadians(45))
                + property.getKFf() * -2
                + property.getKa() * 1.3,
            property.calculate(Math.toRadians(45), -2, 1.3));
    }

    @Test
    public void testCalculate() {
        ArmFeedForwardProperty property = new ArmFeedForwardProperty(PROPERTY_NAME, true)
            .addKff(1.5)
            .addKs(0.5)
            .addKg(1)
            .addKa(2);

        assertEquals(
            0.5, property.calculate(Radians.of(Math.PI / 3), RadiansPerSecond.of(0)).in(Volts), 0.002);
        assertEquals(
            2.5, property.calculate(Radians.of(Math.PI / 3), RadiansPerSecond.of(1)).in(Volts), 0.002);
    }
}
