package com.gos.lib.rev.swerve;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RevSwerveModuleConstantsTest {

    @Test
    public void test12Teeth() {
        RevSwerveModuleConstants.DriveMotorTeeth teeth = RevSwerveModuleConstants.DriveMotorTeeth.T12;

        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(teeth);
        assertEquals(5.50, constants.m_drivingMotorReduction, 1e-2);
    }

    @Test
    public void test13Teeth() {
        RevSwerveModuleConstants.DriveMotorTeeth teeth = RevSwerveModuleConstants.DriveMotorTeeth.T13;

        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(teeth);
        assertEquals(5.08, constants.m_drivingMotorReduction, 1e-2);
    }

    @Test
    public void test14Teeth() {
        RevSwerveModuleConstants.DriveMotorTeeth teeth = RevSwerveModuleConstants.DriveMotorTeeth.T14;

        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(teeth);
        assertEquals(4.71, constants.m_drivingMotorReduction, 1e-2);
    }
}
