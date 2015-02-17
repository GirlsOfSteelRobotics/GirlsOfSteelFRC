package org.usfirst.frc.team3504.robot.subsystems;

import org.usfirst.frc.team3504.robot.commands.camera.CameraOverlay;
import org.usfirst.frc.team3504.robot.commands.drive.DriveByJoystick;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;

/*
 * author Kriti
 */
public class Camera extends Subsystem {

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new CameraOverlay());

	}
}
