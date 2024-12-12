package com.gos.lib.rev.swerve;

import com.gos.lib.rev.swerve.RevSwerveModuleConstants.DriveMotor;
import com.gos.lib.rev.swerve.RevSwerveModuleConstants.SwerveGearingKit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RevSwerveModuleConstantsTest {


    @Test
    public void testLowNeo() {
        SwerveGearingKit kit = SwerveGearingKit.LOW;
        DriveMotor motorType = DriveMotor.NEO;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorType, kit.m_pinionTeeth, kit.m_spurTeeth);
        assertEquals(5.50, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(4.12, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testLowVortex() {
        SwerveGearingKit kit = SwerveGearingKit.LOW;
        DriveMotor motorType = DriveMotor.VORTEX;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorType, kit.m_pinionTeeth, kit.m_spurTeeth);
        assertEquals(5.50, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(4.92, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testMediumNeo() {
        SwerveGearingKit kit = SwerveGearingKit.MEDIUM;
        DriveMotor motorType = DriveMotor.NEO;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorType, kit.m_pinionTeeth, kit.m_spurTeeth);
        assertEquals(5.08, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(4.46, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testMediumVortex() {
        SwerveGearingKit kit = SwerveGearingKit.MEDIUM;
        DriveMotor motorType = DriveMotor.VORTEX;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorType, kit.m_pinionTeeth, kit.m_spurTeeth);
        assertEquals(5.08, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(5.33, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }


    @Test
    public void testHighNeo() {
        SwerveGearingKit kit = SwerveGearingKit.HIGH;
        DriveMotor motorType = DriveMotor.NEO;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorType, kit.m_pinionTeeth, kit.m_spurTeeth);
        assertEquals(4.71, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(4.80, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testHighVortex() {
        SwerveGearingKit kit = SwerveGearingKit.HIGH;
        DriveMotor motorType = DriveMotor.VORTEX;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorType, kit.m_pinionTeeth, kit.m_spurTeeth);
        assertEquals(4.71, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(5.74, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }


    @Test
    public void testExtraHigh1Neo() {
        SwerveGearingKit kit = SwerveGearingKit.EXTRA_HIGH_1;
        DriveMotor motorType = DriveMotor.NEO;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorType, kit.m_pinionTeeth, kit.m_spurTeeth);
        assertEquals(4.50, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(5.03, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testExtraHigh1Vortex() {
        SwerveGearingKit kit = SwerveGearingKit.EXTRA_HIGH_1;
        DriveMotor motorType = DriveMotor.VORTEX;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorType, kit.m_pinionTeeth, kit.m_spurTeeth);
        assertEquals(4.50, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(6.01, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }


    @Test
    public void testExtraHigh2Neo() {
        SwerveGearingKit kit = SwerveGearingKit.EXTRA_HIGH_2;
        DriveMotor motorType = DriveMotor.NEO;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorType, kit.m_pinionTeeth, kit.m_spurTeeth);
        assertEquals(4.29, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(5.28, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testExtraHigh2Vortex() {
        SwerveGearingKit kit = SwerveGearingKit.EXTRA_HIGH_2;
        DriveMotor motorType = DriveMotor.VORTEX;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorType, kit.m_pinionTeeth, kit.m_spurTeeth);
        assertEquals(4.29, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(6.32, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }


    @Test
    public void testExtraHigh3Neo() {
        SwerveGearingKit kit = SwerveGearingKit.EXTRA_HIGH_3;
        DriveMotor motorType = DriveMotor.NEO;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorType, kit.m_pinionTeeth, kit.m_spurTeeth);
        assertEquals(4.0, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(5.66, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testExtraHigh3Vortex() {
        SwerveGearingKit kit = SwerveGearingKit.EXTRA_HIGH_3;
        DriveMotor motorType = DriveMotor.VORTEX;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorType, kit.m_pinionTeeth, kit.m_spurTeeth);
        assertEquals(4.0, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(6.77, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }


    @Test
    public void testExtraHigh4Neo() {
        SwerveGearingKit kit = SwerveGearingKit.EXTRA_HIGH_4;
        DriveMotor motorType = DriveMotor.NEO;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorType, kit.m_pinionTeeth, kit.m_spurTeeth);
        assertEquals(3.75, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(6.04, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testExtraHigh4Vortex() {
        SwerveGearingKit kit = SwerveGearingKit.EXTRA_HIGH_4;
        DriveMotor motorType = DriveMotor.VORTEX;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorType, kit.m_pinionTeeth, kit.m_spurTeeth);
        assertEquals(3.75, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(7.22, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }


    @Test
    public void testExtraHigh5Neo() {
        SwerveGearingKit kit = SwerveGearingKit.EXTRA_HIGH_5;
        DriveMotor motorType = DriveMotor.NEO;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorType, kit.m_pinionTeeth, kit.m_spurTeeth);
        assertEquals(3.56, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(6.36, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testExtraHigh5Vortex() {
        SwerveGearingKit kit = SwerveGearingKit.EXTRA_HIGH_5;
        DriveMotor motorType = DriveMotor.VORTEX;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorType, kit.m_pinionTeeth, kit.m_spurTeeth);
        assertEquals(3.56, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(7.60, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }
}
