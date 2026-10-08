package com.gos.lib.phoenix6;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.Slot1Configs;
import com.ctre.phoenix6.configs.Slot2Configs;
import com.ctre.phoenix6.configs.SlotConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.gos.lib.phoenix6.properties.pid.Phoenix6TalonPidPropertyBuilder;
import edu.wpi.first.wpilibj.Preferences;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Phoenix6TalonPidPropertyBuilderTest extends BasePropertiesTest {

    private static final String PROPERTY_NAME = "TalonFXPidPropertyBuilderTest";

    @Test
    public void testTraditionalSetup() {
        try (TalonFX talon = new TalonFX(6)) {
            talon.getConfigurator().apply(new TalonFXConfiguration());

            // Verify default settings
            Slot0Configs config = new Slot0Configs();
            talon.getConfigurator().refresh(config);
            assertEquals(0, config.kP);
            assertEquals(0, config.kI);
            assertEquals(0, config.kD);
            assertEquals(0, config.kV);
            assertEquals(0, config.kS);
            assertEquals(0, config.kG);
            assertEquals(GravityTypeValue.Elevator_Static, config.GravityType);
            assertEquals(0, config.kA);

            new Phoenix6TalonPidPropertyBuilder(PROPERTY_NAME, false, talon, 0)
                .addP(1)
                .addI(2)
                .addD(3)
                .addKV(4)
                .addKS(5)
                .addKG(6, GravityTypeValue.Arm_Cosine)
                .addKA(7)
                .build();

            // Verify the initial setup
            talon.getConfigurator().refresh(config);
            assertEquals(1, config.kP);
            assertEquals(2, config.kI);
            assertEquals(3, config.kD);
            assertEquals(4, config.kV);
            assertEquals(5, config.kS);
            assertEquals(6, config.kG);
            assertEquals(GravityTypeValue.Arm_Cosine, config.GravityType);
            assertEquals(7, config.kA);

            assertEquals(1, Preferences.getDouble(PROPERTY_NAME + "kp", -1));
            assertEquals(2, Preferences.getDouble(PROPERTY_NAME + "ki", -1));
            assertEquals(3, Preferences.getDouble(PROPERTY_NAME + "kD", -1));
            assertEquals(4, Preferences.getDouble(PROPERTY_NAME + "kV", -1));
            assertEquals(5, Preferences.getDouble(PROPERTY_NAME + "kS", -1));
            assertEquals(6, Preferences.getDouble(PROPERTY_NAME + "kG", -1));
            assertEquals(7, Preferences.getDouble(PROPERTY_NAME + "kA", -1));
        }
    }

    @Test
    public void testSetupWithSlot() {
        try (TalonFX talon = new TalonFX(6)) {
            talon.getConfigurator().apply(new TalonFXConfiguration());

            // Verify default settings
            Slot1Configs config = new Slot1Configs();
            talon.getConfigurator().refresh(config);
            assertEquals(0, config.kP);
            assertEquals(0, config.kI);
            assertEquals(0, config.kD);
            assertEquals(0, config.kV);
            assertEquals(0, config.kS);
            assertEquals(0, config.kG);
            assertEquals(GravityTypeValue.Elevator_Static, config.GravityType);
            assertEquals(0, config.kA);

            config = new Slot1Configs()
                .withKP(100).withKI(0.1).withKD(0.5)
                .withKS(0.2).withKV(2.33).withKA(1)
                .withKG(35.04).withGravityType(GravityTypeValue.Arm_Cosine);

            new Phoenix6TalonPidPropertyBuilder(PROPERTY_NAME, false, talon, 1)
                .fromDefaults(SlotConfigs.from(config))
                .build();

            // Verify the initial setup
            talon.getConfigurator().refresh(config);
            assertEquals(100, config.kP, 1e-3);
            assertEquals(0.1, config.kI, 1e-3);
            assertEquals(0.5, config.kD, 1e-3);
            assertEquals(2.33, config.kV, 1e-3);
            assertEquals(0.2, config.kS, 1e-3);
            assertEquals(35.04, config.kG, 1e-3);
            assertEquals(GravityTypeValue.Arm_Cosine, config.GravityType);
            assertEquals(1, config.kA, 1e-3);

            assertEquals(100, Preferences.getDouble(PROPERTY_NAME + "kp", -1));
            assertEquals(0.1, Preferences.getDouble(PROPERTY_NAME + "ki", -1));
            assertEquals(0.5, Preferences.getDouble(PROPERTY_NAME + "kD", -1));
            assertEquals(2.33, Preferences.getDouble(PROPERTY_NAME + "kV", -1));
            assertEquals(0.2, Preferences.getDouble(PROPERTY_NAME + "kS", -1));
            assertEquals(35.04, Preferences.getDouble(PROPERTY_NAME + "kG", -1));
            assertEquals(1, Preferences.getDouble(PROPERTY_NAME + "kA", -1));
        }
    }

    @Test
    public void testEmptySlotConfig() {
        try (TalonFX talon = new TalonFX(6)) {
            talon.getConfigurator().apply(new TalonFXConfiguration());

            Slot2Configs config = new Slot2Configs();
            new Phoenix6TalonPidPropertyBuilder(PROPERTY_NAME, false, talon, 2)
                .fromDefaults(SlotConfigs.from(config))
                .build();

            assertEquals(0 + 1, Preferences.getKeys().size());
        }
    }
}
