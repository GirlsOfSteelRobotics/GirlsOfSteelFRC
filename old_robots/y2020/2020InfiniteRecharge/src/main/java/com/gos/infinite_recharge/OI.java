package com.gos.infinite_recharge;

import com.gos.infinite_recharge.commands.AlignLeftRight;
import com.gos.infinite_recharge.commands.AutomatedConveyorIntake;
import com.gos.infinite_recharge.commands.ControlPanelRotationControl;
import com.gos.infinite_recharge.commands.ConveyorAdvanceOneUnit;
import com.gos.infinite_recharge.commands.ConveyorWhileHeld;
import com.gos.infinite_recharge.commands.DriveLessByJoystickWhenPressed;
import com.gos.infinite_recharge.commands.HangerLift;
import com.gos.infinite_recharge.commands.IntakeCells;
import com.gos.infinite_recharge.commands.MovePiston;
import com.gos.infinite_recharge.commands.RunShooterRPMWhileHeld;
import com.gos.infinite_recharge.commands.SpinControlPanel;
import com.gos.infinite_recharge.commands.SwitchToCamClimb;
import com.gos.infinite_recharge.commands.SwitchToCamIntake;
import com.gos.infinite_recharge.commands.VelocityControlDrivingTuning;
import com.gos.infinite_recharge.commands.WinchWind;
import com.gos.infinite_recharge.subsystems.Camera;
import com.gos.infinite_recharge.subsystems.Chassis;
import com.gos.infinite_recharge.subsystems.ControlPanel;
import com.gos.infinite_recharge.subsystems.Lift;
import com.gos.infinite_recharge.subsystems.Limelight;
import com.gos.infinite_recharge.subsystems.Shooter;
import com.gos.infinite_recharge.subsystems.ShooterConveyor;
import com.gos.infinite_recharge.subsystems.ShooterIntake;
import com.gos.infinite_recharge.subsystems.Winch;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import com.gos.infinite_recharge.commands.autonomous.SingleShoot;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public XboxController m_drivingPad;
    public XboxController m_operatingPad;

    public OI(Chassis chassis, ControlPanel controlPanel, Limelight limelight, Camera camera,
              Shooter shooter, ShooterIntake shooterIntake, ShooterConveyor shooterConveyor,
              Lift lift, Winch winch) {
        m_drivingPad = new XboxController(0);
        m_operatingPad = new XboxController(1);

        new JoystickButton(m_operatingPad, Button.kA.value).whileTrue(new AutomatedConveyorIntake(shooterIntake, shooterConveyor));
        new JoystickButton(m_operatingPad, Button.kB.value).whileTrue(new RunShooterRPMWhileHeld(shooter, Constants.DEFAULT_RPM));
        //new JoystickButton(m_operatingPad, Button.kLeftBumper.value).whileTrue(new RunShooterRPMWhileHeld(shooter, 2000));
        new JoystickButton(m_operatingPad, Button.kX.value).onTrue(new MovePiston(shooterIntake, true));
        new JoystickButton(m_operatingPad, Button.kLeftBumper.value).whileTrue(new IntakeCells(shooterIntake, true));
        new Trigger(() -> m_operatingPad.getLeftTriggerAxis() > .8).whileTrue(new IntakeCells(shooterIntake, false));
        new JoystickButton(m_operatingPad, Button.kRightBumper.value).whileTrue(new ConveyorWhileHeld(shooterConveyor, true));
        new Trigger(() -> m_operatingPad.getRightTriggerAxis() > .8).whileTrue(new ConveyorWhileHeld(shooterConveyor, false));
        //new JoystickButton(m_operatingPad, Button.kBack.value).whileTrue();
        new JoystickButton(m_operatingPad, Button.kX.value).onTrue(new MovePiston(shooterIntake, true));
        new JoystickButton(m_operatingPad, Button.kY.value).onTrue(new MovePiston(shooterIntake, false));
        //new POVButton(m_operatingPad, 90).onTrue(new TuneRPM(shooter));
        new POVButton(m_operatingPad, 0).onTrue(new ConveyorAdvanceOneUnit(shooterConveyor));
        new POVButton(m_operatingPad, 180).onTrue(new SingleShoot(shooter, shooterConveyor, Constants.DEFAULT_RPM));
        new JoystickButton(m_operatingPad, Button.kBack.value).whileTrue(new RunShooterRPMWhileHeld(shooter, Constants.LONG_RPM));
        new JoystickButton(m_operatingPad, Button.kStart.value).whileTrue(new VelocityControlDrivingTuning(chassis));

        new JoystickButton(m_drivingPad, Button.kA.value).whileTrue(new HangerLift(lift, true));
        new JoystickButton(m_drivingPad, Button.kB.value).whileTrue(new HangerLift(lift, false));
        //new JoystickButton(m_drivingPad, Button.kB.value).whileTrue(new WinchWind(winch, true));
        //new JoystickButton(m_drivingPad, Button.kA.value).whileTrue(new WinchWind(winch, false));
        new Trigger(() -> m_drivingPad.getLeftTriggerAxis() > .8).whileTrue(new WinchWind(winch, false));
        new Trigger(() -> m_drivingPad.getRightTriggerAxis() > .8).whileTrue(new WinchWind(winch, true));
        new JoystickButton(m_drivingPad, Button.kY.value).onTrue(new ControlPanelRotationControl(controlPanel));
        new POVButton(m_drivingPad, 0).onTrue(new SwitchToCamClimb(camera));
        new POVButton(m_drivingPad, 180).onTrue(new SwitchToCamIntake(camera));
        new POVButton(m_drivingPad, 90).whileTrue(new SpinControlPanel(controlPanel));
        new POVButton(m_drivingPad, 270).whileTrue(new AlignLeftRight(chassis, limelight));
        //new POVButton(m_drivingPad, 90).whileTrue(new AlignForwardBackward(chassis, limelight));

        new JoystickButton(m_drivingPad, Button.kLeftBumper.value).whileTrue(new DriveLessByJoystickWhenPressed(chassis, m_drivingPad));
        //new JoystickButton(m_drivingPad, Button.kB.value).onTrue(new DriveByJoystick(chassis, this));


    }

}
