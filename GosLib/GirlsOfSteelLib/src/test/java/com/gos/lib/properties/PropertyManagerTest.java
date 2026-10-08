package com.gos.lib.properties;

import edu.wpi.first.wpilibj.Preferences;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PropertyManagerTest extends BasePropertiesTest {
    private final ByteArrayOutputStream m_outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream m_errContent = new ByteArrayOutputStream();
    private final PrintStream m_originalOut = System.out;
    private final PrintStream m_originalErr = System.err;

    @BeforeEach
    @Override
    public void setup() {
        super.setup();
        System.setOut(new PrintStream(m_outContent));
        System.setErr(new PrintStream(m_errContent));
    }

    @AfterEach
    @Override
    public void tearDown() {
        super.tearDown();
        System.setOut(m_originalOut);
        System.setErr(m_originalErr);
    }

    @Test
    public void testPurgeConstantKeys() {
        Preferences.setDouble("A", 1);
        Preferences.setDouble("B", 2);
        assertEquals(2 + 1, Preferences.getKeys().size());

        PropertyManager.setPurgeConstantPreferenceKeys(true);

        new GosDoubleProperty(true, "A", -1);
        new GosDoubleProperty(false, "B", -1);
        new GosDoubleProperty(true, "C", -1);
        assertEquals(1 + 1, Preferences.getKeys().size());
    }

    @Test
    public void testPrintDynamicProperties() {
        Preferences.setDouble("A", 1);
        Preferences.setDouble("B", 2);
        assertEquals(2 + 1, Preferences.getKeys().size());

        new GosDoubleProperty(false, "A", 1);
        new GosDoubleProperty(false, "B", -1);

        PropertyManager.printDynamicProperties(false);

        assertTrue(m_outContent.toString().contains("Creating non-constant property 'A'. Default=1.0 vs Saved=1.0"));
        assertTrue(m_errContent.toString().contains("Creating non-constant property 'B'. Default=-1.0 vs Saved=2.0"));
    }

    @Test
    public void testPurgeExtraKeys() {
        Preferences.setDouble("A", 1);
        Preferences.setDouble("B", 2);
        Preferences.setDouble("C", 2);
        assertEquals(3 + 1, Preferences.getKeys().size());

        new GosDoubleProperty(false, "A", 1);
        new GosDoubleProperty(false, "C", -1);

        PropertyManager.purgeExtraKeys();
        assertEquals(2 + 1, Preferences.getKeys().size());
    }
}
