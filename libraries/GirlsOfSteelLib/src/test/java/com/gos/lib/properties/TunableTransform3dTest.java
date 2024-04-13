package com.gos.lib.properties;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Preferences;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TunableTransform3dTest extends BasePropertiesTest {
    private static final double EPSILON = 1e-6;

    private static final String PROPERTY_NAME = "SimpleMotorFeedForwardTest";

    @Test
    public void testParameterSetting() {
        TunableTransform3d tunableTransform = new TunableTransform3d(false, PROPERTY_NAME,
            new Transform3d(
                new Translation3d(1, 2, 3),
                new Rotation3d(Math.toRadians(4), Math.toRadians(5), Math.toRadians(6))
            ));
        Transform3d transform = tunableTransform.getTransform();

        // Test Default
        assertEquals(Units.metersToInches(1), Units.metersToInches(transform.getTranslation().getX()), EPSILON);
        assertEquals(Units.metersToInches(2), Units.metersToInches(transform.getTranslation().getY()), EPSILON);
        assertEquals(Units.metersToInches(3), Units.metersToInches(transform.getTranslation().getZ()), EPSILON);
        assertEquals(4, Math.toDegrees(transform.getRotation().getX()), EPSILON);
        assertEquals(5, Math.toDegrees(transform.getRotation().getY()), EPSILON);
        assertEquals(6, Math.toDegrees(transform.getRotation().getZ()), EPSILON);


        // Set Translation X
        Preferences.setDouble(PROPERTY_NAME + ".x(in)", 191);
        transform = tunableTransform.getTransform();

        assertEquals(191, Units.metersToInches(transform.getTranslation().getX()), EPSILON);
        assertEquals(Units.metersToInches(2), Units.metersToInches(transform.getTranslation().getY()), EPSILON);
        assertEquals(Units.metersToInches(3), Units.metersToInches(transform.getTranslation().getZ()), EPSILON);
        assertEquals(4, Math.toDegrees(transform.getRotation().getX()), EPSILON);
        assertEquals(5, Math.toDegrees(transform.getRotation().getY()), EPSILON);
        assertEquals(6, Math.toDegrees(transform.getRotation().getZ()), EPSILON);

        // Set Translation Y
        Preferences.setDouble(PROPERTY_NAME + ".y(in)", 229);
        transform = tunableTransform.getTransform();

        assertEquals(191, Units.metersToInches(transform.getTranslation().getX()), EPSILON);
        assertEquals(229, Units.metersToInches(transform.getTranslation().getY()), EPSILON);
        assertEquals(Units.metersToInches(3), Units.metersToInches(transform.getTranslation().getZ()), EPSILON);
        assertEquals(4, Math.toDegrees(transform.getRotation().getX()), EPSILON);
        assertEquals(5, Math.toDegrees(transform.getRotation().getY()), EPSILON);
        assertEquals(6, Math.toDegrees(transform.getRotation().getZ()), EPSILON);

        // Set Translation Z
        Preferences.setDouble(PROPERTY_NAME + ".z(in)", 3504);
        transform = tunableTransform.getTransform();

        assertEquals(191, Units.metersToInches(transform.getTranslation().getX()), EPSILON);
        assertEquals(229, Units.metersToInches(transform.getTranslation().getY()), EPSILON);
        assertEquals(3504, Units.metersToInches(transform.getTranslation().getZ()), EPSILON);
        assertEquals(4, Math.toDegrees(transform.getRotation().getX()), EPSILON);
        assertEquals(5, Math.toDegrees(transform.getRotation().getY()), EPSILON);
        assertEquals(6, Math.toDegrees(transform.getRotation().getZ()), EPSILON);

    }
}
