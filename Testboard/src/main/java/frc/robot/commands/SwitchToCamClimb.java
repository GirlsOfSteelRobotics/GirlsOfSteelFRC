package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class SwitchToCamClimb extends Command {

    public SwitchToCamClimb() {
        requires(Robot.camera);
    }

    @Override
    public void initialize() {
        SmartDashboard.putString("Camera selection", "Climb");
        Robot.camera.switchToCamClimb();
    }

    @Override
    public void execute() {
    }

    @Override
    public void end() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
