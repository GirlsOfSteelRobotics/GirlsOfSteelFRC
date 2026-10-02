package com.gos.codelabs.basic_simulator.subsystems;

import com.gos.codelabs.basic_simulator.BaseTestFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShooterSubsystemTest extends BaseTestFixture {

    @Test
    public void testSetSpeed() {
        try (ShooterSubsystem shooter = new ShooterSubsystem()) {
            runCycles(25, () -> shooter.setSpeed(1));
            assertTrue(shooter.getRpm() > 0);

            double fastestRpm = shooter.getRpm();
            runCycles(25, shooter::stop);
            assertTrue(shooter.getRpm() < fastestRpm);
        }
    }

    @Test
    public void testSpinAtRpm() {
        try (ShooterSubsystem shooter = new ShooterSubsystem()) {
            // When we start out, we should not be at speed
            assertFalse(shooter.isAtRpm(ShooterSubsystem.SHOOTING_RPM));

            // Give it two seconds to get up to speed
            runCycles(100, () -> shooter.spinAtRpm(ShooterSubsystem.SHOOTING_RPM));

            assertEquals(ShooterSubsystem.SHOOTING_RPM, shooter.getRpm(), ShooterSubsystem.ALLOWABLE_RPM_ERROR);
            assertTrue(shooter.isAtRpm(ShooterSubsystem.SHOOTING_RPM));
            assertFalse(shooter.isAtRpm(ShooterSubsystem.SHOOTING_RPM + 1000));
            assertFalse(shooter.isAtRpm(ShooterSubsystem.SHOOTING_RPM - 1000));
        }
    }
}
