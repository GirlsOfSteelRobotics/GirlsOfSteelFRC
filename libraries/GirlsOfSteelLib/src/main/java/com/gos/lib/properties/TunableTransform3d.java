package com.gos.lib.properties;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;

public class TunableTransform3d {
    private final HeavyDoubleProperty m_xProperty;
    private final HeavyDoubleProperty m_yProperty;
    private final HeavyDoubleProperty m_zProperty;

    private final HeavyDoubleProperty m_yawProperty;
    private final HeavyDoubleProperty m_pitchProperty;
    private final HeavyDoubleProperty m_rollProperty;

    private Transform3d m_transform;

    public TunableTransform3d(boolean isConstant, String baseName, Transform3d originalTransform) {
        m_transform = originalTransform;

        Translation3d translation = originalTransform.getTranslation();
        m_xProperty = HeavyDoubleProperty.create(baseName + ".x(in)", isConstant, Units.metersToInches(translation.getX()), this::updateX);
        m_yProperty = HeavyDoubleProperty.create(baseName + ".y(in)", isConstant, Units.metersToInches(translation.getY()), this::updateY);
        m_zProperty = HeavyDoubleProperty.create(baseName + ".z(in)", isConstant, Units.metersToInches(translation.getZ()), this::updateZ);

        Rotation3d rotation = originalTransform.getRotation();
        m_yawProperty = HeavyDoubleProperty.create(baseName + ".yaw(deg)", isConstant, Math.toDegrees(rotation.getZ()), this::updateYaw);
        m_pitchProperty = HeavyDoubleProperty.create(baseName + ".pitch(deg)", isConstant, Math.toDegrees(rotation.getY()), this::updatePitch);
        m_rollProperty = HeavyDoubleProperty.create(baseName + ".roll(deg)", isConstant, Math.toDegrees(rotation.getX()), this::updateRoll);
    }

    public Transform3d getTransform() {
        m_xProperty.updateIfChanged();
        m_yProperty.updateIfChanged();
        m_zProperty.updateIfChanged();

        m_yawProperty.updateIfChanged();
        m_pitchProperty.updateIfChanged();
        m_rollProperty.updateIfChanged();

        return m_transform;
    }

    private void updateX(double inches) {
        Translation3d oldTranslation = m_transform.getTranslation();
        Translation3d newTranslation = new Translation3d(Units.inchesToMeters(inches), oldTranslation.getY(), oldTranslation.getZ());
        m_transform = new Transform3d(newTranslation, m_transform.getRotation());
    }

    private void updateY(double inches) {
        Translation3d oldTranslation = m_transform.getTranslation();
        Translation3d newTranslation = new Translation3d(oldTranslation.getX(), Units.inchesToMeters(inches), oldTranslation.getZ());
        m_transform = new Transform3d(newTranslation, m_transform.getRotation());
    }

    private void updateZ(double inches) {
        Translation3d oldTranslation = m_transform.getTranslation();
        Translation3d newTranslation = new Translation3d(oldTranslation.getX(), oldTranslation.getY(), Units.inchesToMeters(inches));
        m_transform = new Transform3d(newTranslation, m_transform.getRotation());
    }

    private void updateYaw(double degrees) {
        Rotation3d oldRotation = m_transform.getRotation();
        Rotation3d newRotation = new Rotation3d(oldRotation.getX(), oldRotation.getY(), Math.toRadians(degrees));
        m_transform = new Transform3d(m_transform.getTranslation(), newRotation);
    }

    private void updatePitch(double degrees) {
        Rotation3d oldRotation = m_transform.getRotation();
        Rotation3d newRotation = new Rotation3d(oldRotation.getX(), Math.toRadians(degrees), oldRotation.getZ());
        m_transform = new Transform3d(m_transform.getTranslation(), newRotation);
    }

    private void updateRoll(double degrees) {
        Rotation3d oldRotation = m_transform.getRotation();
        Rotation3d newRotation = new Rotation3d(Math.toRadians(degrees), oldRotation.getY(), oldRotation.getZ());
        m_transform = new Transform3d(m_transform.getTranslation(), newRotation);
    }

}
