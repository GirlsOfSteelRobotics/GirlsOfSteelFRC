package com.gos.lib.rev.swerve;

import com.gos.lib.rev.RevMotorControllerModel;
import com.gos.lib.rev.RevMotorModel;
import com.gos.lib.rev.swerve.config.RevSwerveModuleConstants;
import com.gos.lib.rev.swerve.config.SwerveGearingKit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RevSwerveModuleConstantsTest {


    @Test
    public void testLowNeo() {
        RevMotorControllerModel motorControllerModel = RevMotorControllerModel.SPARK_MAX;
        RevMotorModel motorType = RevMotorModel.NEO;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorControllerModel, motorType, SwerveGearingKit.LOW);
        assertEquals(5.50, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(4.12, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testLowVortex() {
        RevMotorControllerModel motorControllerModel = RevMotorControllerModel.SPARK_MAX;
        RevMotorModel motorType = RevMotorModel.VORTEX;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorControllerModel, motorType, SwerveGearingKit.LOW);
        assertEquals(5.50, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(4.92, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testMediumNeo() {
        RevMotorControllerModel motorControllerModel = RevMotorControllerModel.SPARK_MAX;
        RevMotorModel motorType = RevMotorModel.NEO;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorControllerModel, motorType, SwerveGearingKit.MEDIUM);
        assertEquals(5.08, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(4.46, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testMediumVortex() {
        RevMotorControllerModel motorControllerModel = RevMotorControllerModel.SPARK_MAX;
        RevMotorModel motorType = RevMotorModel.VORTEX;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorControllerModel, motorType, SwerveGearingKit.MEDIUM);
        assertEquals(5.08, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(5.33, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }


    @Test
    public void testHighNeo() {
        RevMotorControllerModel motorControllerModel = RevMotorControllerModel.SPARK_MAX;
        RevMotorModel motorType = RevMotorModel.NEO;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorControllerModel, motorType, SwerveGearingKit.HIGH);
        assertEquals(4.71, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(4.80, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testHighVortex() {
        RevMotorControllerModel motorControllerModel = RevMotorControllerModel.SPARK_MAX;
        RevMotorModel motorType = RevMotorModel.VORTEX;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorControllerModel, motorType, SwerveGearingKit.HIGH);
        assertEquals(4.71, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(5.74, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }


    @Test
    public void testExtraHigh1Neo() {
        RevMotorControllerModel motorControllerModel = RevMotorControllerModel.SPARK_MAX;
        RevMotorModel motorType = RevMotorModel.NEO;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorControllerModel, motorType, SwerveGearingKit.EXTRA_HIGH_1);
        assertEquals(4.50, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(5.03, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testExtraHigh1Vortex() {
        RevMotorControllerModel motorControllerModel = RevMotorControllerModel.SPARK_MAX;
        RevMotorModel motorType = RevMotorModel.VORTEX;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorControllerModel, motorType, SwerveGearingKit.EXTRA_HIGH_1);
        assertEquals(4.50, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(6.01, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }


    @Test
    public void testExtraHigh2Neo() {
        RevMotorControllerModel motorControllerModel = RevMotorControllerModel.SPARK_MAX;
        RevMotorModel motorType = RevMotorModel.NEO;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorControllerModel, motorType, SwerveGearingKit.EXTRA_HIGH_2);
        assertEquals(4.29, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(5.28, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testExtraHigh2Vortex() {
        RevMotorControllerModel motorControllerModel = RevMotorControllerModel.SPARK_MAX;
        RevMotorModel motorType = RevMotorModel.VORTEX;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorControllerModel, motorType, SwerveGearingKit.EXTRA_HIGH_2);
        assertEquals(4.29, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(6.32, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }


    @Test
    public void testExtraHigh3Neo() {
        RevMotorControllerModel motorControllerModel = RevMotorControllerModel.SPARK_MAX;
        RevMotorModel motorType = RevMotorModel.NEO;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorControllerModel, motorType, SwerveGearingKit.EXTRA_HIGH_3);
        assertEquals(4.0, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(5.66, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testExtraHigh3Vortex() {
        RevMotorControllerModel motorControllerModel = RevMotorControllerModel.SPARK_MAX;
        RevMotorModel motorType = RevMotorModel.VORTEX;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorControllerModel, motorType, SwerveGearingKit.EXTRA_HIGH_3);
        assertEquals(4.0, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(6.77, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }


    @Test
    public void testExtraHigh4Neo() {
        RevMotorControllerModel motorControllerModel = RevMotorControllerModel.SPARK_MAX;
        RevMotorModel motorType = RevMotorModel.NEO;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorControllerModel, motorType, SwerveGearingKit.EXTRA_HIGH_4);
        assertEquals(3.75, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(6.04, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testExtraHigh4Vortex() {
        RevMotorControllerModel motorControllerModel = RevMotorControllerModel.SPARK_MAX;
        RevMotorModel motorType = RevMotorModel.VORTEX;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorControllerModel, motorType, SwerveGearingKit.EXTRA_HIGH_4);
        assertEquals(3.75, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(7.22, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }


    @Test
    public void testExtraHigh5Neo() {
        RevMotorControllerModel motorControllerModel = RevMotorControllerModel.SPARK_MAX;
        RevMotorModel motorType = RevMotorModel.NEO;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorControllerModel, motorType, SwerveGearingKit.EXTRA_HIGH_5);
        assertEquals(3.56, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(6.36, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }

    @Test
    public void testExtraHigh5Vortex() {
        RevMotorControllerModel motorControllerModel = RevMotorControllerModel.SPARK_MAX;
        RevMotorModel motorType = RevMotorModel.VORTEX;
        RevSwerveModuleConstants constants = new RevSwerveModuleConstants(motorControllerModel, motorType, SwerveGearingKit.EXTRA_HIGH_5);
        assertEquals(3.56, constants.m_drivingMotorReduction, 1e-2);
        assertEquals(7.60, constants.m_driveWheelFreeSpeedRps, 1e-2);
    }
}
