package com.gos.lib.rev.swerve;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RevSwerveModuleConstants19SpurVortexTest {
    private final RevSwerveModuleConstants.DriveMotor m_driveMotor = RevSwerveModuleConstants.DriveMotor.VORTEX;
    private final RevSwerveModuleConstants.DriveMotorSpurTeeth m_spurTeeth = RevSwerveModuleConstants.DriveMotorSpurTeeth.T19;

    @Test
    public void test16Teeth() {
        RevSwerveModuleConstants.DriveMotorPinionTeeth teeth = RevSwerveModuleConstants.DriveMotorPinionTeeth.T16;

        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(m_driveMotor, teeth, m_spurTeeth);
        assertEquals(3.56, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(7.60, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }
}
